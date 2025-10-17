package biocampo.demo.Domain.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import biocampo.demo.Domain.Model.PlantCategory;
import biocampo.demo.Domain.Model.Product;

public interface ProductRepository {

    List<Product> getAll();
    Optional<Product> getById(Long id);
    //List<Product> getAllById(List<Product> products);
    Product save(Product product);
    void deleteById(Long id);
    List<Product> getCategory(PlantCategory category);
    List<Product> getActive(boolean active);
    List<Product> getByPriceLess(BigDecimal price);
}
