package biocampo.demo.Persistance.CRUD;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biocampo.demo.Persistance.Entity.Empleado;



@Repository
public interface RepoEmpleado extends JpaRepository<Empleado, Long>{
    //Optional<Empleado> findByNombres(String nombres);
    Optional<Empleado> findByEmailEmpresarial(String emailEmpresarial);
    List<Empleado> findByDisponible(boolean disponible);
    Optional<Empleado> findByDni(String dni);
}
