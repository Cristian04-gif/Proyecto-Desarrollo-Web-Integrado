package biocampo.demo.Domain.Repository;

import java.util.List;
import java.util.Optional;

import biocampo.demo.Domain.Model.InputSupplier;

public interface InputSupplierRepository {

    List<InputSupplier> getAll();
    Optional<InputSupplier> getById(Long id);
    InputSupplier save(InputSupplier inputSupplier);
    void deleteById(Long id);
}
