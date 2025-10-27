package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import biocampo.demo.Domain.Model.Input;
import biocampo.demo.Domain.Model.Order;
import biocampo.demo.Domain.Model.OrderDetail;
import biocampo.demo.Domain.Repository.InputRepository;
import biocampo.demo.Domain.Repository.OrderDetailRepository;
import biocampo.demo.Domain.Repository.OrderRepository;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository detailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InputRepository inputRepository;

    public List<OrderDetail> getAll() {
        return detailRepository.getAll();
    }

    public List<OrderDetail> getAllByOrder(Long orderId) {
        return detailRepository.getByOrder(orderId);
    }

    public Optional<OrderDetail> getOrderDetail(Long id) {
        return detailRepository.orderDetailGetById(id);
    }

    @Transactional
    public OrderDetail updateOrderDetail(Long id, OrderDetail detail) {
        System.out.println("Se ingreso a actualizar");
        OrderDetail existeDetail = detailRepository.orderDetailGetById(id)
                .orElseThrow(() -> new IllegalArgumentException("EL detalle de pedido no existe"));

        // ACTUALIZANDO EL PEDIDO
        System.out.println("Existe el detalle de pedido");
        Order order = orderRepository.getById(existeDetail.getOrder().getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("NO existe el order relacionada"));
        System.out.println("existe el pedido");

        // quitando el costo anterior
        double restar = existeDetail.getPriceUnit() * existeDetail.getAmount();
        System.out.println("Costo del pedido antes de actualizar el detalle: " + order.getTotal());
        order.setTotal(order.getTotal() - restar);
        System.out.println("COsto restando antes de actualizar: " + order.getTotal());
        // sumando la nueva cantidad
        double aumentar = detail.getAmount() * detail.getPriceUnit();
        order.setTotal(order.getTotal() + aumentar);
        System.out.println("NUevo costo del detalle: " + aumentar);
        System.out.println("costo actualizado: " + order.getTotal());
        orderRepository.save(order);

        // ACTUALIZANDO EL INSUMO
        Input input = inputRepository.getById(existeDetail.getInput().getInputId()).orElseThrow();
        System.out.println("existe el insumo");
        System.out.println("stock antes de actualizar: " + input.getStock());
        input.setStock(input.getStock() - existeDetail.getAmount());
        System.out.println("precio unitario antes de actualizar: " + input.getPriceUnit());
        input.setPriceUnit(detail.getPriceUnit());
        System.out.println("costo total antes de actualizar: " + input.getTotalCost());
        System.out.println("stock despues de actualizar: " + input.getStock());
        input.setStock(input.getStock() + detail.getAmount());
        System.out.println("precio unitario despues de actualizar: " + input.getPriceUnit());

        input.setTotalCost(input.getStock() * input.getPriceUnit());
        System.out.println("costo total antes de actualizar: " + input.getTotalCost());

        inputRepository.save(input);

        // actualizando el detalle
        existeDetail.setAmount(detail.getAmount());
        existeDetail.setPriceUnit(detail.getPriceUnit());
        existeDetail.setOrder(order);
        existeDetail.setInput(input);
        return detailRepository.save(existeDetail);
    }

    public void deteleOrderDetail(Long id) {
        detailRepository.delete(id);
    }
}
