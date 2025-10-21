package biocampo.demo.Persistance.CRUD;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biocampo.demo.Persistance.Entity.CultivoInsumo;
import biocampo.demo.Persistance.Entity.CultivoInsumoId;

@Repository
public interface RepoCultivoInsumo extends JpaRepository<CultivoInsumo, CultivoInsumoId>{
    List<CultivoInsumo> findByCultivoIdCultivo(Long idCultivo);
    List<CultivoInsumo> findByInsumoIdInsumo(Long idInsumo);
}
