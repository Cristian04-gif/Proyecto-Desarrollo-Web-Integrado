package biocampo.demo.Persistance.Mappings;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import biocampo.demo.Domain.Model.PostHarvest;
import biocampo.demo.Persistance.Entity.PostCosecha;

@Mapper(componentModel = "spring", uses = {HarvestMapper.class})
public interface PostHarvestMapper {

    @Mappings({
        @Mapping(source = "idPostCosecha", target = "postHarvestId"),
        @Mapping(source = "plantaCosechada", target = "harvest"),
        @Mapping(source = "fecha", target = "dateProcessed"),
        @Mapping(source = "limpieza", target = "cleaningMethod"),
        @Mapping(source = "tratamiento", target = "treatmentMethod"),
        @Mapping(source = "empaque", target = "packing"),
        @Mapping(source = "almacenamiento", target = "storage"),
    })

    PostHarvest toPostHarvest(PostCosecha postCosecha);

    List<PostHarvest> toPostHarvests(List<PostCosecha> postCosechas);

    @InheritInverseConfiguration
    @Mapping(target = "empleados", ignore = true)
    PostCosecha toPostCosecha(PostHarvest postHarvest);
}
