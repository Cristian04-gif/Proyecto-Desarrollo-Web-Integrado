package biocampo.demo.Persistance.CRUD;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biocampo.demo.Persistance.Entity.CategoriaPlanta;
import biocampo.demo.Persistance.Entity.Planta;
import java.util.List;


@Repository
public interface RepoPlanta extends JpaRepository<Planta, Long>{
    List<Planta> findByDisponible(boolean disponible);
    List<Planta> findByCategoria(CategoriaPlanta categoria);
}
