package biocampo.demo.Domain.Repository;

import java.util.List;
import java.util.Optional;

import biocampo.demo.Domain.Model.Input;

public interface InputRepository {
    List<Input> getAll();
    Optional<Input> getById(Long id);
    Input save(Input input);
    void deleteById(Long id);
}
