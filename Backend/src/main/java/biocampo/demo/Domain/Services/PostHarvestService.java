package biocampo.demo.Domain.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.Employee;
import biocampo.demo.Domain.Model.PostHarvest;
import biocampo.demo.Domain.Repository.PostHarvestRepository;
import biocampo.demo.Persistance.CRUD.RepoCosecha;
import biocampo.demo.Persistance.CRUD.RepoEmpleado;
import biocampo.demo.Persistance.CRUD.RepoPostCosecha;
import biocampo.demo.Persistance.Entity.Cosecha;
import biocampo.demo.Persistance.Entity.Empleado;
import biocampo.demo.Persistance.Entity.PostCosecha;
import biocampo.demo.Persistance.Entity.PostCosecha.EstadoPostCosecha;
import biocampo.demo.Persistance.Mappings.EmployeeMapper;
import biocampo.demo.Persistance.Mappings.PostHarvestMapper;

@Service
public class PostHarvestService {

    @Autowired
    private PostHarvestRepository postHarvestRepository;

    @Autowired
    private PostHarvestMapper postHarvestMapper;
    @Autowired
    private RepoPostCosecha repoPostCosecha;
    @Autowired
    private RepoCosecha repoCosecha;
    @Autowired
    private RepoEmpleado repoEmpleado;
    @Autowired
    private EmployeeMapper employeeMapper;

    // Obtener todas las postcosechas
    public List<PostHarvest> getAllPostHarvests() {
        return postHarvestRepository.getAll();
    }

    // Obtener postcosecha por ID
    public Optional<PostHarvest> getPostHarvestById(Long postHarvestId) {
        return postHarvestRepository.getById(postHarvestId);
    }

    public PostCosecha calculoPrecioUnitario(double convertirKg, Cosecha cosecha, PostCosecha postCosecha) {
        System.out.println("convertirkg: " + convertirKg);
        double costoCultivo = cosecha.getCultivo().getCosto();
        double costoCosecha = cosecha.getCosto();
        double costoPostCosecha = postCosecha.getCostoAlmacenamiento();
        double perdida = postCosecha.getKgPerdidos() != null ? postCosecha.getKgPerdidos() : 0.0;
        double costoEmpleado = postCosecha.getCostoEmpleado();

        double sumaCostos = costoCosecha + costoCultivo + costoPostCosecha + costoEmpleado;
        double margen = 2;
        double kgComercializables = convertirKg - perdida;

        System.out.println("suma de costos: " + sumaCostos);
        double precioUnitario = (sumaCostos / kgComercializables) * margen;
        System.out.println("precio unitario: " + precioUnitario);
        precioUnitario = (double) Math.round(precioUnitario * 100) / 100;

        double ingresoTotal = precioUnitario * kgComercializables;
        double ganancias = ingresoTotal - sumaCostos;
        System.out.println("precio rendondeado: " + precioUnitario);
        postCosecha.setKgComerciables(kgComercializables);
        postCosecha.setPrecioKg(precioUnitario);
        System.out.println("precio unitario dentro del metodo double: " + postCosecha.getPrecioKg());
        postCosecha.setIngresoTotal((double) Math.round(ingresoTotal * 100) / 100);
        postCosecha.setGanancia((double) Math.round(ganancias * 100) / 100);
        return postCosecha;
    }

    // Registrar nueva postcosecha
    public PostHarvest registerPostharvest(PostHarvest postHarvest, List<Employee> employees) {
        System.out.println("Entro al registro de la postcosecha");
        System.out.println("COsecha: " + postHarvest.getHarvest().getHarvestId());
        PostCosecha postCosechaEntity = postHarvestMapper.toPostCosecha(postHarvest);
        Cosecha cosechaEntity = repoCosecha.findById(postCosechaEntity.getCosecha().getIdCosecha()).orElseThrow();
        System.out.println("la cosecha repacionada existe");
        for (Empleado emp : cosechaEntity.getEmpleados()) {
            System.out.println("Empleado: " + emp.getNombres());
            emp.setDisponible(true);
            // emp.getCosecha().remove(cosechaEntity);
            repoEmpleado.save(emp);
            System.out.println("Empleado: " + emp.getNombres() + " actualizado");
        }
        cosechaEntity.getEmpleados().clear();
        Cosecha cosechaActualizada = repoCosecha.save(cosechaEntity);
        postCosechaEntity.setCosecha(cosechaActualizada);

        //
        double convertirKg = 0.0;
        if (cosechaActualizada.getUnidadMedida().equalsIgnoreCase("ton")) {
            convertirKg = cosechaActualizada.getCantidadCosechada() * 1000;
        } else {
            convertirKg = cosechaActualizada.getCantidadCosechada();
        }

        System.out.println("cnatidad de cosecha: " + cosechaActualizada.getCantidadCosechada());
        System.out.println(convertirKg);

        PostCosecha postCosechaGuardado = repoPostCosecha.save(postCosechaEntity);

        double sumaSalario = 0.0;
        for (Employee emp : employees) {
            Empleado empleadoEntity = employeeMapper.toEmpleado(emp);
            Empleado existEmpleado = repoEmpleado.findById(empleadoEntity.getIdEmpleado()).orElseThrow();
            if (existEmpleado.isDisponible()) {
                existEmpleado.setDisponible(false);
                sumaSalario += existEmpleado.getSalario();
                if (existEmpleado.getPostCosecha() == null) {
                    existEmpleado.setPostCosecha(new ArrayList<>());
                }
                if (postCosechaGuardado.getEmpleados() == null) {
                    postCosechaGuardado.setEmpleados(new ArrayList<>());
                }
                existEmpleado.getPostCosecha().add(postCosechaGuardado);
                postCosechaGuardado.getEmpleados().add(existEmpleado);
            }
        }
        postCosechaGuardado.setCostoEmpleado(sumaSalario);
        postCosechaGuardado = calculoPrecioUnitario(convertirKg, cosechaActualizada, postCosechaEntity);
        postCosechaGuardado.setKgComerciables(postCosechaGuardado.getKgComerciables());
        postCosechaGuardado.setPrecioKg(postCosechaGuardado.getPrecioKg());
        postCosechaGuardado.setIngresoTotal(postCosechaGuardado.getIngresoTotal());
        postCosechaGuardado.setGanancia(postCosechaGuardado.getGanancia());
        postCosechaGuardado.setEstado(EstadoPostCosecha.EN_ALMACENAMIENTO);
        postCosechaGuardado.setFechaConversion(null);
        PostCosecha postCosechaFinal = repoPostCosecha.save(postCosechaGuardado);
        return postHarvestMapper.toPostHarvest(postCosechaFinal);
    }

    public PostHarvest updatePostHarvest(Long id, PostHarvest postHarvest) {
        PostHarvest exist = postHarvestRepository.getById(id).orElseThrow();
        PostCosecha postCosechaEntity = postHarvestMapper.toPostCosecha(exist);

        Cosecha cosechaEntity = repoCosecha.findById(postCosechaEntity.getCosecha().getIdCosecha()).orElseThrow();

        if (postHarvest.getHarvest() != null && postHarvest.getHarvest().getHarvestId() != null) {
            for (Empleado emp : cosechaEntity.getEmpleados()) {
                Empleado existeEmpleado = repoEmpleado.findById(emp.getIdEmpleado()).orElseThrow();
                existeEmpleado.setDisponible(true);
                repoEmpleado.save(existeEmpleado);
            }
            cosechaEntity.getEmpleados().clear();
            postCosechaEntity.setCosecha(cosechaEntity);
        }
        Cosecha cosechaActualizada = repoCosecha.save(cosechaEntity);
        if (postHarvest.getStorageCost() != null) {
            postCosechaEntity.setCostoAlmacenamiento(postHarvest.getStorageCost());
        }

        if (postHarvest.getCostEmployee() != null) {
            postCosechaEntity.setCostoEmpleado(postHarvest.getCostEmployee());
        }
        if (postHarvest.getLossKg() != null) {
            postCosechaEntity.setKgPerdidos(postHarvest.getLossKg());
        }
        if (postHarvest.getObservations() != null) {
            postCosechaEntity.setObservaciones(postHarvest.getObservations());
        }
        double convertirKg = 0.0;
        if (cosechaActualizada.getUnidadMedida().equalsIgnoreCase("ton")) {
            convertirKg = cosechaActualizada.getCantidadCosechada() * 1000;
        } else {
            convertirKg = cosechaActualizada.getCantidadCosechada();
        }

        PostCosecha postCosechaGuardado = repoPostCosecha.save(postCosechaEntity);
        postCosechaGuardado = calculoPrecioUnitario(convertirKg, cosechaActualizada, postCosechaGuardado);
        postCosechaGuardado.setEstado(EstadoPostCosecha.EN_ALMACENAMIENTO);
        postCosechaGuardado.setKgComerciables(postCosechaGuardado.getKgComerciables());
        postCosechaGuardado.setPrecioKg(postCosechaGuardado.getPrecioKg());
        postCosechaGuardado.setIngresoTotal(postCosechaGuardado.getIngresoTotal());
        postCosechaGuardado.setGanancia(postCosechaGuardado.getGanancia());
        PostCosecha postCosechaFInal = repoPostCosecha.save(postCosechaGuardado);
        return postHarvestMapper.toPostHarvest(postCosechaFInal);
    }

    // Eliminar postcosecha
    public void deletePostHarvest(Long postHarvestId) {
        PostHarvest postHarvest = postHarvestRepository.getById(postHarvestId).orElseThrow();
        PostCosecha postCosecha = postHarvestMapper.toPostCosecha(postHarvest);
        for (Empleado emp : postCosecha.getEmpleados()) {
            Empleado empleado = repoEmpleado.findById(emp.getIdEmpleado()).orElseThrow();
            empleado.setDisponible(true);
            empleado.getPostCosecha().remove(postCosecha);
            repoEmpleado.save(empleado);
        }
        postHarvestRepository.deleteById(postHarvestId);

    }
}
