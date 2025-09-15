package biocampo.demo.Domain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biocampo.demo.Persistance.Entity.PuestoEmpleado;

@Repository
public interface RepoPuestoEmpleado extends JpaRepository<PuestoEmpleado, Long>{

}
