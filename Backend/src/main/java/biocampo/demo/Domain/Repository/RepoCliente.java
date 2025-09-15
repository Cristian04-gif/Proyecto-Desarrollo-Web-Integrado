package biocampo.demo.Domain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biocampo.demo.Persistance.Entity.Cliente;

@Repository
public interface RepoCliente extends JpaRepository<Cliente, Long>{

}
