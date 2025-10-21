package biocampo.demo.Persistance.CRUD;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biocampo.demo.Persistance.Entity.DetallePedido;
import biocampo.demo.Persistance.Entity.Pedido;

@Repository
public interface RepoDetallePedido extends JpaRepository<DetallePedido, Long>{
    List<DetallePedido> findByPedido(Pedido pedido);
}
