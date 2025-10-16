package biocampo.demo.Persistance.CRUD;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biocampo.demo.Persistance.Entity.Cliente;
import biocampo.demo.Persistance.Entity.Cliente.Tipo;

import java.util.List;


@Repository
public interface RepoCliente extends JpaRepository<Cliente, Long>{
    List<Cliente> findByTipo(Tipo tipo);
}
