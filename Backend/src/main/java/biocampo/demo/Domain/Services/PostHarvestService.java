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
        double perdida = postCosecha.getUnidadPerdida() != null ? postCosecha.getUnidadPerdida() : 0.0;
        double costoEmpleado = postCosecha.getCostoEmpleado();
        double sumaCostos = costoCosecha + costoCultivo + costoPostCosecha + costoEmpleado;
        if (perdida > 0) {
            sumaCostos += (perdida * (sumaCostos / convertirKg));
        }
        System.out.println("suma de costos: " + sumaCostos);
        double precioUnitario = (sumaCostos / convertirKg) * 1.5;
        System.out.println("precio unitario: " + precioUnitario);
        precioUnitario = (double) Math.round(precioUnitario * 100) / 100;
        System.out.println("precio rendondeado: " + precioUnitario);
        postCosecha.setPrecioUnidad(precioUnitario);
        System.out.println("precio unitario dentro del metodo double: " + postCosecha.getPrecioUnidad());
        postCosecha.setIngresoTotal(precioUnitario * convertirKg);
        postCosecha.setGanancia(postCosecha.getIngresoTotal() - sumaCostos);
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
        PostCosecha calculoPrecioUnitario = calculoPrecioUnitario(convertirKg, cosechaActualizada, postCosechaEntity);

        postCosechaGuardado.setPrecioUnidad(calculoPrecioUnitario.getPrecioUnidad());
        postCosechaGuardado.setIngresoTotal(calculoPrecioUnitario.getIngresoTotal());
        postCosechaGuardado.setGanancia(calculoPrecioUnitario.getGanancia());
        PostCosecha postCosechaFinal = repoPostCosecha.save(postCosechaGuardado);
        return postHarvestMapper.toPostHarvest(postCosechaFinal);
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
