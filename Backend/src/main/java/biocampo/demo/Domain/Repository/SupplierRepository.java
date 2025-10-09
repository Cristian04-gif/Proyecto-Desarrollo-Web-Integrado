package biocampo.demo.Domain.Repository;

import java.util.List;
import java.util.Optional;

import biocampo.demo.Domain.Model.Supplier;


public interface SupplierRepository {

    List<Supplier> getAll();
    Optional<Supplier> getById(Long id);
    Supplier save(Supplier supplier);
    void deleteById(Long id);
}
