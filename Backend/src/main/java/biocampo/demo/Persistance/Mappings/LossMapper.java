package biocampo.demo.Persistance.Mappings;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import biocampo.demo.Domain.Model.Loss;
import biocampo.demo.Persistance.Entity.Perdida;

@Mapper(componentModel = "spring", uses = {CultivationMapper.class, HarvestMapper.class})
public interface LossMapper {

    @Mappings({
        @Mapping(source = "idPerdida", target = "lossId"),
        @Mapping(source = "tipoPerdida", target = "typeLoss"),
        @Mapping(source = "accion", target = "action"),
        @Mapping(source = "cultivo", target = "cultivation"),
        @Mapping(source = "cosecha", target = "harvest"),
    })

    Loss toLoss(Perdida perdida);
    List<Loss> toLosses(List<Perdida> perdidas);

    @InheritInverseConfiguration
    Perdida toPerdida(Loss loss);
}
