package biocampo.demo.Domain.Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import biocampo.demo.Domain.Model.Employee;
import biocampo.demo.Domain.Model.Harvest;
import biocampo.demo.Domain.Repository.HarvestRepository;
import biocampo.demo.Persistance.CRUD.RepoCosecha;
import biocampo.demo.Persistance.CRUD.RepoCultivo;
import biocampo.demo.Persistance.CRUD.RepoEmpleado;
import biocampo.demo.Persistance.Entity.Cosecha;
import biocampo.demo.Persistance.Entity.Cultivo;
import biocampo.demo.Persistance.Entity.Empleado;
import biocampo.demo.Persistance.Mappings.EmployeeMapper;
import biocampo.demo.Persistance.Mappings.HarvestMapper;

@Service
public class HarvestService {

    @Autowired
    private HarvestRepository harvestRepository;

    //
    @Autowired
    private RepoCultivo repoCultivo;

    @Autowired
    private HarvestMapper harvestMapper;

    @Autowired
    private RepoEmpleado repoEmpleado;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private RepoCosecha repoCosecha;

    public List<Harvest> getAll() {
        return harvestRepository.getAll();
    }

    public Optional<Harvest> getHarvest(Long id) {
        return harvestRepository.getByIdHarvest(id);
    }

    @Transactional
    public Harvest registerHarvest(Harvest harvest, List<Employee> employees) {
        System.out.println("Entro al registro de cosecha");
        Cosecha cosechaEntity = harvestMapper.toCosecha(harvest);

        // actualizando el cultivo
        Cultivo cultivoEntity = repoCultivo.findById(cosechaEntity.getCultivo().getIdCultivo()).orElseThrow();
        System.out.println("EL cultivo existe: " + cultivoEntity.getNombreParcela());
        cultivoEntity.setFechaEstimadaCosecha(LocalDate.now());

        for (Empleado emp : cultivoEntity.getEmpleados()) {
            emp.setDisponible(true);

            repoEmpleado.save(emp);
            System.out.println("EL empleado volvio a estar disponible");
        }
        cultivoEntity.getEmpleados().clear();
        System.out.println("se limpio la lista de empleados en el cultivo");
        Cultivo cultivoActualizado = repoCultivo.save(cultivoEntity);
        System.out.println("se guardo el cultivo sin empleados");

        // cosecha
        cosechaEntity.setCultivo(cultivoActualizado);
        if (cosechaEntity.getUnidadMedida().equalsIgnoreCase("ton")) {
            cosechaEntity.setCantidadCosechada(cosechaEntity.getCantidadCosechada() / 1000);
        }
        double rendimiento = cosechaEntity.getCantidadCosechada() / cultivoActualizado.getHectareas();
        cosechaEntity.setRendimietoXHectarea((double) Math.round(rendimiento * 100) / 100);
        System.out.println("rendimiento de la cosecha: " + cosechaEntity.getRendimietoXHectarea());
        Cosecha cosechaGuardado = repoCosecha.save(cosechaEntity);
        System.out.println("cosecha guardada 1");
        double costo = 0.0;
        int i = 0;
        for (Employee employee : employees) {
            System.out.println("revisando empleados disponibles para la cosecha: " + (i + 1));
            Empleado empleadoEntity = employeeMapper.toEmpleado(employee);

            Empleado existEmpleado = repoEmpleado.findById(empleadoEntity.getIdEmpleado()).orElseThrow();

            if (existEmpleado.isDisponible() == true) {
                System.out.println("Empleado disponible: " + existEmpleado.getNombres());
                costo += existEmpleado.getSalario();
                existEmpleado.setDisponible(false);
                System.out.println("El empleado fue asignado a la cosecha: " + existEmpleado.isDisponible());
                if (existEmpleado.getCosecha() == null) {
                    existEmpleado.setCultivo(new ArrayList<>());
                }

                if (cosechaGuardado.getEmpleados() == null) {
                    cosechaGuardado.setEmpleados(new ArrayList<>());
                }
                existEmpleado.getCosecha().add(cosechaGuardado);
                System.out.println("la cosecha se agrego al empleado");
                cosechaGuardado.getEmpleados().add(existEmpleado);
                System.out.println("el empleado se añadio a la cosecha");
                repoEmpleado.save(existEmpleado);
            }
            System.out.println("Empleado guardado");
        }
        cosechaGuardado.setCosto(costo);
        Cosecha cosechaFinal = repoCosecha.save(cosechaGuardado);

        return harvestMapper.toHarvest(cosechaFinal);
    }

    public Harvest updateHarvest(Long id, Harvest harvest) {
        System.out.println("Entro a la actualizacion");
        Harvest updateHarvest = harvestRepository.getByIdHarvest(id).orElseThrow();
        System.out.println("la cosecha existe: " + updateHarvest.getHarvestId());
        Cosecha cosechaEntity = harvestMapper.toCosecha(updateHarvest);
        Cultivo cultivo = repoCultivo.findById(cosechaEntity.getCultivo().getIdCultivo()).orElseThrow();

        if (harvest.getCultivation() != null) {
            System.out.println("el cultivo existe: " + cultivo.getNombreParcela());
            cosechaEntity.setCultivo(cultivo);
        }
        if (harvest.getHarvestQuantity() != null && harvest.getUnitMeasure() != null) {
            System.out.println("cnatidad: " + harvest.getHarvestQuantity());
            System.out.println("medida: " + harvest.getUnitMeasure());
            if (harvest.getUnitMeasure().equalsIgnoreCase("ton")) {
                harvest.setHarvestQuantity(harvest.getHarvestQuantity() / 1000);
            }
            System.out.println("noc 1");
            double rendimiento = harvest.getHarvestQuantity() / cultivo.getHectareas();
            System.out.println("noc 2");
            cosechaEntity.setCantidadCosechada(harvest.getHarvestQuantity());
            System.out.println("noc 3");
            cosechaEntity.setUnidadMedida(harvest.getUnitMeasure());
            System.out.println("Cosecha actualizada: " + cosechaEntity.getIdCosecha());
            System.out.println("cantidad: " + cosechaEntity.getCantidadCosechada());
            cosechaEntity.setRendimietoXHectarea((double) Math.round(rendimiento * 100) / 100);
            System.out.println("rendimiento: " + cosechaEntity.getRendimietoXHectarea());
        }
        Cosecha cosechaFinal = repoCosecha.save(cosechaEntity);
        return harvestMapper.toHarvest(cosechaFinal);
    }

    @Transactional
    public void deleteHarvest(Long id) {
        Harvest harvest = harvestRepository.getByIdHarvest(id).orElseThrow();
        Cosecha cosecha = harvestMapper.toCosecha(harvest);

        for (Empleado emp : cosecha.getEmpleados()) {
            Empleado empleado = repoEmpleado.findById(emp.getIdEmpleado()).orElseThrow();
            empleado.setDisponible(true);
            empleado.getCosecha().remove(cosecha);
            repoEmpleado.save(empleado);
        }
        harvestRepository.deleteById(id);
    }
}
