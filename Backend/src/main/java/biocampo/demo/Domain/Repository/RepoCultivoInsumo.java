package biocampo.demo.Domain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biocampo.demo.Persistance.Entity.CultivoInsumo;

@Repository
public interface RepoCultivoInsumo extends JpaRepository<CultivoInsumo, Long>{

}
