package biocampo.demo.Persistance.CRUD;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biocampo.demo.Persistance.Entity.Cliente;
import biocampo.demo.Persistance.Entity.Cliente.Tipo;

import java.util.List;
import java.util.Optional;


@Repository
public interface RepoCliente extends JpaRepository<Cliente, Long>{
    List<Cliente> findByTipo(Tipo tipo);
    Optional<Cliente> findByUsuarioEmail(String emailUser);
}
