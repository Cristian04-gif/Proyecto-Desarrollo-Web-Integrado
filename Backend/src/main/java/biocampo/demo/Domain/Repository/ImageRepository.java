package biocampo.demo.Domain.Repository;

import java.util.List;
import java.util.Optional;

import biocampo.demo.Domain.Model.Image;
import biocampo.demo.Persistance.Entity.Imagen.TipoEntidad;

public interface ImageRepository {
    List<Image> getAll();
    Optional<Image> getByIdImage(Long id);
    List<Image> findByTipoEntidadAndIdReferencia(TipoEntidad tipo, Long id);
    Image save(Image image);
    void deleteById(Long id);
}
