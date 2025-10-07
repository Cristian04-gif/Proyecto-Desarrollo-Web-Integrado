package biocampo.demo.Domain.Repository;

import java.util.List;
import java.util.Optional;

import biocampo.demo.Domain.Model.Product;

public interface ProductRepository {

    List<Product> getAll();
    Optional<Product> getById(Long id);
    Product save(Product product);
    void deleteById(Long id);
}
