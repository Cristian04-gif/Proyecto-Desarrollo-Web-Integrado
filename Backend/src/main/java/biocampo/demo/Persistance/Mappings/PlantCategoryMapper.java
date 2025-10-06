package biocampo.demo.Persistance.Mappings;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import biocampo.demo.Domain.Model.PlantCategory;
import biocampo.demo.Persistance.Entity.CategoriaPlanta;

@Mapper(componentModel = "spring")
public interface PlantCategoryMapper {
    @Mappings({
        @Mapping(source = "idCategoriaPlanta", target="categoryId"),
        @Mapping(source = "nombre", target = "categoryName"),
    })

    PlantCategory toCategory(CategoriaPlanta categoriaPlanta);

    List<PlantCategory> toPlantCategories(List<CategoriaPlanta> categoriaPlantas);

    @InheritInverseConfiguration
    @Mapping(target = "planta", ignore = true)
    CategoriaPlanta toCategoriaPlanta(PlantCategory plantCategory);
}
