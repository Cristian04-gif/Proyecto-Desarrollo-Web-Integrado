package biocampo.demo.Domain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biocampo.demo.Persistance.Entity.Proveedor;

@Repository
public interface RepoProveedor extends JpaRepository<Proveedor, Long>{

}
