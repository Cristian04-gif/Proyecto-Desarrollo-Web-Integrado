package biocampo.demo.Persistance.CRUD;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biocampo.demo.Persistance.Entity.DetalleVenta;
import biocampo.demo.Persistance.Entity.Venta;

import java.util.List;



@Repository
public interface RepoDetalleVenta extends JpaRepository<DetalleVenta, Long>{
    List<DetalleVenta> findByVenta(Venta venta);
    
}
