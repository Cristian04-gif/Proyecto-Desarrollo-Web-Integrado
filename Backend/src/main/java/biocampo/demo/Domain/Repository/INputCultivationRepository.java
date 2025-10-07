package biocampo.demo.Domain.Repository;

import java.util.List;
import java.util.Optional;

import biocampo.demo.Domain.Model.InputCultivation;

public interface INputCultivationRepository {

    List<InputCultivation> getAll();
    Optional<InputCultivation> getById(Long id);
    InputCultivation save(InputCultivation inputCultivation);
    void deleteById(Long id);
}
