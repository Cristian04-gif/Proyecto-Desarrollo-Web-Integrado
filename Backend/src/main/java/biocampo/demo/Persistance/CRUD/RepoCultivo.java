package biocampo.demo.Persistance.CRUD;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biocampo.demo.Persistance.Entity.Cosecha.Temporada;
import biocampo.demo.Persistance.Entity.Cultivo;
import java.util.List;


@Repository
public interface RepoCultivo extends JpaRepository<Cultivo, Long>{
    List<Cultivo> findByTemporada(Temporada temporada);
}
