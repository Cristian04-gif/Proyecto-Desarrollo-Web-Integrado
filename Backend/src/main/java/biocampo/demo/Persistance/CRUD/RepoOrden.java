package biocampo.demo.Persistance.CRUD;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biocampo.demo.Persistance.Entity.Pedido;

@Repository
public interface RepoOrden extends JpaRepository<Pedido, Long>{

}
