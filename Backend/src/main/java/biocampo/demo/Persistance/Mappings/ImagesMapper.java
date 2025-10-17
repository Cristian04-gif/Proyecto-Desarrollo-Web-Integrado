package biocampo.demo.Persistance.Mappings;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import biocampo.demo.Domain.Model.Image;
import biocampo.demo.Persistance.Entity.Imagen;

@Mapper(componentModel = "spring")
public interface ImagesMapper {
    @Mappings({
        @Mapping(source = "idImagen", target = "imageId"),
        @Mapping(source = "url", target = "url"),
        @Mapping(source = "tipoEntidad", target = "entityType"),
        @Mapping(source = "idReferencia", target = "referenceId"),
        @Mapping(source = "fechaSubida", target = "uploadDate"),
    })
    Image toImage(Imagen imagen);

    List<Image> toImages(List<Imagen> imagens);

    @InheritInverseConfiguration
    Imagen toImagen(Image image);
    
}