package biocampo.demo.Persistance.CRUD;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biocampo.demo.Persistance.Entity.CategoriaPlanta;
import biocampo.demo.Persistance.Entity.Producto;
import java.math.BigDecimal;


@Repository
public interface RepoProducto extends JpaRepository<Producto, Long>{
    List<Producto> findByCategoriaPlanta(CategoriaPlanta categoriaPlanta);
    List<Producto> findByDisponible(boolean disponible);
    List<Producto> findByPrecioLessThan(BigDecimal precio);
}
