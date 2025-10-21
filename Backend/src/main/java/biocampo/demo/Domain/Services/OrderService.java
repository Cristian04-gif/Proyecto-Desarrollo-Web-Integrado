package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import biocampo.demo.Domain.Model.Order;
import biocampo.demo.Domain.Model.OrderDetail;
import biocampo.demo.Domain.Repository.InputRepository;
import biocampo.demo.Domain.Repository.OrderDetailRepository;
import biocampo.demo.Domain.Repository.OrderRepository;
import biocampo.demo.Domain.Repository.SupplierRepository;
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

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private InputRepository inputRepository;

    @Autowired
    private OrderDetailRepository detailRepository;

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

    

    /*
     * public Order registerInputSupplier(Order inputSupplier) {
     * Optional<Supplier> existSupplier =
     * supplierRepository.getById(inputSupplier.getSupplier().getSupplierId());
     * //Optional<Input> existInput =
     * inputRepository.getById(inputSupplier.getInput().getInputId());
     * 
     * /*if (existSupplier.isPresent() && existInput.isPresent()) {
     * inputSupplier.setSupplier(existSupplier.get());
     * inputSupplier.setInput(existInput.get());
     * } else {
     * throw new IllegalArgumentException("El proveedor o insumo no existe");
     * }
     * return inputSupplierRepository.save(inputSupplier);
     * }
     * 
     * public Order updataInputSupplier(Long id, Order inputSupplier) {
     * Optional<Order> exist = inputSupplierRepository.getById(id);
     * if (exist.isPresent()) {
     * Order update = exist.get();
     * 
     * if (inputSupplier.getSupplier() != null) {
     * Optional<Supplier> existSupplier = supplierRepository
     * .getById(inputSupplier.getSupplier().getSupplierId());
     * if (existSupplier.isPresent()) {
     * update.setSupplier(existSupplier.get());
     * }
     * }
     * /*if (inputSupplier.getInput() != null) {
     * Optional<Input> existInput =
     * inputRepository.getById(inputSupplier.getInput().getInputId());
     * if (existInput.isPresent()) {
     * update.setInput(existInput.get());
     * }
     * }
     */
    /*
     * if (inputSupplier.getPrice() != null) {
     * update.setPrice(inputSupplier.getPrice());
     * }
     * 
     * return inputSupplierRepository.save(update);
     * }else{
     * return null;
     * }
     * }
     * 
     * public void delete(Long id){
     * inputSupplierRepository.deleteById(id);
     * }
     */
}
