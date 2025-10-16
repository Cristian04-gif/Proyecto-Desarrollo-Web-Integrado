package biocampo.demo.Domain.Repository;

import java.util.List;
import java.util.Optional;

import biocampo.demo.Domain.Model.Loss;

public interface LossRepository {
    List<Loss> getAll();
    Optional<Loss> getById(Long id);
    Loss save(Loss loss);
    void deleteById(Long id);
}
