package biocampo.demo.Domain.Repository;

import java.util.List;
import java.util.Optional;

import biocampo.demo.Domain.Model.Order;
import biocampo.demo.Domain.Model.OrderDetail;

public interface OrderDetailRepository {

    List<OrderDetail> getAll();
    Optional<OrderDetail> orderDetailGetById(Long id);
    OrderDetail save(OrderDetail detail);
    void delete(Long id);
    List<OrderDetail> getByOrder(Order order);
    
}
