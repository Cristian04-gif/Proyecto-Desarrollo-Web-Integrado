package biocampo.demo.Domain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biocampo.demo.Persistance.Entity.DetalleVenta;


@Repository
public interface RepoDetalleVenta extends JpaRepository<DetalleVenta, Long>{

}
