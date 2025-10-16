package biocampo.demo.Persistance.Function;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import biocampo.demo.Domain.Model.Image;
import biocampo.demo.Domain.Repository.ImageRepository;
import biocampo.demo.Persistance.CRUD.RepoImagenes;
import biocampo.demo.Persistance.Entity.Imagen;
import biocampo.demo.Persistance.Entity.Imagen.TipoEntidad;
import biocampo.demo.Persistance.Mappings.ImagesMapper;

@Repository
public class ImagenRepository implements ImageRepository{

    @Autowired
    private RepoImagenes repoImagenes;
    @Autowired
    private ImagesMapper imagesMapper;


    @Override
    public List<Image> getAll() {
        List<Imagen> todos = repoImagenes.findAll();
        return imagesMapper.toImages(todos);
    }

    @Override
    public Optional<Image> getByIdImage(Long id) {
        return repoImagenes.findById(id).map(imagen -> imagesMapper.toImage(imagen));
    }

    @Override
    public List<Image> findByTipoEntidadAndIdReferencia(TipoEntidad tipo, Long id) {
        List<Imagen> imagenRelacion = repoImagenes.findByTipoEntidadAndIdReferencia(tipo, id);
        return imagesMapper.toImages(imagenRelacion);
    }

    @Override
    public Image save(Image image) {
        Imagen imagen = imagesMapper.toImagen(image);
        Imagen imagenGuardado = repoImagenes.save(imagen);
        return imagesMapper.toImage(imagenGuardado);
    }

    @Override
    public void deleteById(Long id) {
        repoImagenes.deleteById(id);
    }

}
