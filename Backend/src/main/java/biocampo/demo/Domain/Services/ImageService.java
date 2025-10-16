package biocampo.demo.Domain.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.Image;
import biocampo.demo.Domain.Repository.ImageRepository;
import biocampo.demo.Persistance.Entity.Imagen.TipoEntidad;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public List<Image> getAll() {
        return imageRepository.getAll();
    }

    public Image uploadImage(Image image) {
        return imageRepository.save(image);
    }

    public List<Image> getRelatedImage(TipoEntidad entidad, Long idRelacionado) {
        return imageRepository.findByTipoEntidadAndIdReferencia(entidad, idRelacionado);
    }

    public void delete(Long id) {
        imageRepository.deleteById(id);
    }
}
