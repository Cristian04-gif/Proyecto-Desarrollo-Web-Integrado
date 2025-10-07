package biocampo.demo.Persistance.Mappings;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import biocampo.demo.Domain.Model.InputCultivation;
import biocampo.demo.Persistance.Entity.CultivoInsumo;

@Mapper(componentModel = "spring", uses = {CultivationMapper.class, InputMapper.class})
public interface InputCultivationMapper {

    @Mappings({
        @Mapping(source = "id", target = "inputCultivationId"),
        @Mapping(source = "cantidad", target = "quantity"),
        @Mapping(source = "cultivo", target = "cultivation"),
        @Mapping(source = "insumo", target = "input"),
    })

    InputCultivation toInputCultivation(CultivoInsumo cultivoInsumo);
    List<InputCultivation> toInputCultivations(List<CultivoInsumo> cultivoInsumos);

    @InheritInverseConfiguration
    CultivoInsumo toCultivoInsumo(InputCultivation inputCultivation);
}
