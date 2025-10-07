package biocampo.demo.Domain.Repository;

import java.util.List;
import java.util.Optional;

import biocampo.demo.Domain.Model.Cultivation;

public interface CultivationRepository {

    List<Cultivation> getAll();
    Optional<Cultivation> getById(Long id);
    Cultivation save(Cultivation cultivation);
    void deleteById(Long id);
}
