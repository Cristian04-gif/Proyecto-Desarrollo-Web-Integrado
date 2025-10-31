package biocampo.demo.Persistance.CRUD;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biocampo.demo.Persistance.Entity.DetallePedido;

@Repository
public interface RepoDetallePedido extends JpaRepository<DetallePedido, Long>{
    List<DetallePedido> findByPedidoIdPedido(Long idPedido);
}
