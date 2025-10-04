package biocampo.demo.Persistance.CRUD;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biocampo.demo.Persistance.Entity.Imagen;
import biocampo.demo.Persistance.Entity.Imagen.TipoEntidad;

@Repository
public interface RepoImagenes extends JpaRepository<Imagen, Long> {

    List<Imagen> findByTipoEntidadAndIdReferencia(TipoEntidad tipo, Long id);
}