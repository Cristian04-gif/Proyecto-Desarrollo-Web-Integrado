package biocampo.demo.Domain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biocampo.demo.Persistance.Entity.ProveedorInsumo;

@Repository
public interface RepoProveedorInsumo extends JpaRepository<ProveedorInsumo, Long>{

}
