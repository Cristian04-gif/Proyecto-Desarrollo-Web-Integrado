package biocampo.demo.Persistance.Function;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import biocampo.demo.Domain.Model.OrderDetail;
import biocampo.demo.Domain.Repository.OrderDetailRepository;
import biocampo.demo.Persistance.CRUD.RepoDetallePedido;
import biocampo.demo.Persistance.Entity.DetallePedido;
import biocampo.demo.Persistance.Mappings.OrderDetailMapper;

@Repository
public class DetallePedidoRepository implements OrderDetailRepository {

    @Autowired
    private RepoDetallePedido repoDetallePedido;
    @Autowired
    private OrderDetailMapper detailMapper;

    @Override
    public List<OrderDetail> getAll() {
        List<DetallePedido> detallePedidos = repoDetallePedido.findAll();
        return detailMapper.toOrderDetails(detallePedidos);
    }

    @Override
    public Optional<OrderDetail> orderDetailGetById(Long id) {
        return repoDetallePedido.findById(id).map(dp -> detailMapper.toOrderDetail(dp));
    }

    @Override
    public OrderDetail save(OrderDetail detail) {
        DetallePedido detallePedido = detailMapper.toDetallePedido(detail);
        DetallePedido detallePedidoGuardado = repoDetallePedido.save(detallePedido);
        return detailMapper.toOrderDetail(detallePedidoGuardado);
    }

    @Override
    public void delete(Long id) {
        repoDetallePedido.deleteById(id);
    }

    @Override
    public List<OrderDetail> getByOrder(Long orderId) {
        List<DetallePedido> detallePedidos = repoDetallePedido.findByPedidoIdPedido(orderId);
        return detailMapper.toOrderDetails(detallePedidos);
    }

    

}
