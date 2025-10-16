package biocampo.demo.Persistance.Mappings;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import biocampo.demo.Domain.Model.Plant;
import biocampo.demo.Persistance.Entity.Planta;

@Mapper(componentModel = "spring", uses = {PlantCategoryMapper.class})
public interface PlantMapper {

    @Mappings({
        @Mapping(source = "idPlanta", target = "plantId"),
        @Mapping(source = "nombre", target = "name"),
        @Mapping(source = "stock", target = "stock"),
        @Mapping(source = "disponible", target = "available"),
        @Mapping(source = "categoria", target = "category"),
    })

    Plant toPlant(Planta planta);

    List<Plant> toPlants(List<Planta> plantas);

    @InheritInverseConfiguration
    Planta toPlanta(Plant plant);
}
