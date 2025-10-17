package biocampo.demo.Persistance.Mappings;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import biocampo.demo.Domain.Model.Harvest;
import biocampo.demo.Persistance.Entity.Cosecha;

@Mapper(componentModel = "spring", uses = {CultivationMapper.class})
public interface HarvestMapper {

    @Mappings({
        @Mapping(source = "idCosecha", target = "harvestId"),
        @Mapping(source = "cultivo", target = "cultivation"),
        @Mapping(source = "fechaCosecha", target = "dateHarvested"),
        @Mapping(source = "temporada", target = "season"),
        @Mapping(source = "recolector", target = "collector")

    })

    Harvest toHarvest(Cosecha cosecha);
    List<Harvest> toHarvests(List<Cosecha> cosechas);

    @InheritInverseConfiguration
    @Mapping(target = "idPerdida", ignore = true)
    @Mapping(target = "empleados", ignore = true)
    Cosecha toCosecha(Harvest harvest);
    
}
