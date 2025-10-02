package biocampo.demo.Domain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biocampo.demo.Persistance.Entity.Insumo;

@Repository
public interface RepoInsumo extends JpaRepository<Insumo, Long>{

}
