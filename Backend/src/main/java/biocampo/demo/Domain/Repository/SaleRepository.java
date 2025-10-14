package biocampo.demo.Domain.Repository;

import java.util.List;
import java.util.Optional;

import biocampo.demo.Domain.Model.Sale;

public interface SaleRepository {

    List<Sale> getAll();
    Optional<Sale> getById(Long id);
    Sale save(Sale sale);
    void deleteById(Long id);
}
