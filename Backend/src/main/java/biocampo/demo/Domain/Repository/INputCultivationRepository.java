package biocampo.demo.Domain.Repository;

import java.util.List;
import java.util.Optional;

import biocampo.demo.Domain.Model.InputCultivation;

public interface INputCultivationRepository {

    List<InputCultivation> getAll();
    Optional<InputCultivation> getById(Long idCultivo, Long idInsumo);
    List<InputCultivation> findByCultivoIdCultivo(Long idCultivo);
    List<InputCultivation> findByInsumoIdInsumo(Long idInsumo);
    InputCultivation save(InputCultivation inputCultivation);
    void deleteById(Long idCultivo, Long idInsumo);
}
