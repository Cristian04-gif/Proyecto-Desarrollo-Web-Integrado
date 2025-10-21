package biocampo.demo.Domain.Repository;

import java.util.List;
import java.util.Optional;

import biocampo.demo.Domain.Model.Order;

public interface OrderRepository {

    List<Order> getAll();
    Optional<Order> getById(Long id);
    Order save(Order inputSupplier);
    void deleteById(Long id);
}
