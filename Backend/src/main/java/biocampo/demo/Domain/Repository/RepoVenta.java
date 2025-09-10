package biocampo.demo.Domain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biocampo.demo.Persistance.Entity.Venta;

@Repository
public interface RepoVenta extends JpaRepository<Venta, Long>{

}
