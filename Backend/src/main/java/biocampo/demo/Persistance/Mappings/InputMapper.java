package biocampo.demo.Persistance.Mappings;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import biocampo.demo.Domain.Model.Input;
import biocampo.demo.Persistance.Entity.Insumo;

@Mapper(componentModel = "spring", uses = {PlantMapper.class, CultivationMapper.class})
public interface InputMapper {

    @Mappings({
        @Mapping(source = "idInsumo", target = "inputId"),
        @Mapping(source = "nombre", target = "name"),
        @Mapping(source = "tipo", target = "type"),
        @Mapping(source = "descripcion", target = "description"),
        @Mapping(source = "unidad", target = "unit"),
        @Mapping(source = "precio", target = "price"),
        @Mapping(source = "planta", target = "plant"),
        //@Mapping(source = "cultivo", target = "cultivation"),
    })

    Input toInput(Insumo insumo);
    List<Input> toInputs(List<Insumo> insumos);

    @InheritInverseConfiguration
    @Mapping(target = "proveedorInsumos", ignore = true)
    @Mapping(target = "cultivoInsumos", ignore = true)
    Insumo toInsumo(Input input);
}
