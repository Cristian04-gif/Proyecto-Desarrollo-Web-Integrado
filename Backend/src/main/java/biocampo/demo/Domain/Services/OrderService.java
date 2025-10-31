package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import biocampo.demo.Domain.Model.Order;
import biocampo.demo.Domain.Model.OrderDetail;
import biocampo.demo.Domain.Repository.OrderRepository;
import biocampo.demo.Persistance.CRUD.RepoDetallePedido;
import biocampo.demo.Persistance.CRUD.RepoInsumo;
import biocampo.demo.Persistance.CRUD.RepoOrden;
import biocampo.demo.Persistance.CRUD.RepoProveedor;
import biocampo.demo.Persistance.Entity.DetallePedido;
import biocampo.demo.Persistance.Entity.Insumo;
import biocampo.demo.Persistance.Entity.Pedido;
import biocampo.demo.Persistance.Entity.Proveedor;
import biocampo.demo.Persistance.Mappings.OrderDetailMapper;
import biocampo.demo.Persistance.Mappings.OrderMapper;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;


    //
    @Autowired
    private RepoOrden repoOrden;
    @Autowired
    private RepoProveedor repoProveedor;
    @Autowired
    private RepoInsumo repoInsumo;
    @Autowired
    private RepoDetallePedido repoDetallePedido;

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    
    //

    public List<Order> getAll() {
        return orderRepository.getAll();
    }

    public Optional<Order> getInputSupplier(long id) {
        return orderRepository.getById(id);
    }

    @Transactional
    public Order registerOrder(Order order, List<OrderDetail> detail) {
        System.out.println("Clase del supplier: " + order.getSupplier().getClass());

        System.out.println("SE ingreso al registro de ordenes");
        Proveedor proveedorEntity = repoProveedor.findById(order.getSupplier().getSupplierId()).orElseThrow();

        Pedido pedidoEntity = orderMapper.toProveedorInsumo(order);
        pedidoEntity.setProveedor(proveedorEntity);
        pedidoEntity.setTotal(0.0);
        
        Pedido savedOrder = repoOrden.save(pedidoEntity);
        

        if (detail == null || detail.isEmpty()) {
            throw new IllegalArgumentException("Error! los detalles estan vacios");
        }
        System.out.println("los detalles no extan vacios");
        // detalles del pedido

        double total = 0;
        for (OrderDetail orderDetail : detail) {
            DetallePedido detallePedido = orderDetailMapper.toDetallePedido(orderDetail);

            Insumo insumoEntity = repoInsumo.findById(orderDetail.getInput().getInputId()).orElseThrow();

            detallePedido.setPedido(savedOrder);
            detallePedido.setInsumo(insumoEntity);

            total+= orderDetail.getPriceUnit()*orderDetail.getAmount();
            repoDetallePedido.save(detallePedido);

            insumoEntity.setStock(insumoEntity.getStock()+orderDetail.getAmount());
            insumoEntity.setPrecioUnitario(orderDetail.getPriceUnit());
            insumoEntity.setCostoTotal(insumoEntity.getStock()*insumoEntity.getPrecioUnitario());
            repoInsumo.save(insumoEntity);

        }
        savedOrder.setTotal(total);
        Pedido savedFinal = repoOrden.save(savedOrder);
        return orderMapper.toInputSupplier(savedFinal);
    }

    public void deleteOrder(Long id){
        Pedido pedido = repoOrden.findById(id).orElseThrow();
        for (DetallePedido detalle : pedido.getDetallePedidos()) {
            repoDetallePedido.deleteById(detalle.getIdDetalle());
        }
        repoOrden.deleteById(id);
    }
}
