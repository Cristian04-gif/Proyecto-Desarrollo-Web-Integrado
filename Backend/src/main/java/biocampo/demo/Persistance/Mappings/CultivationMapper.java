package biocampo.demo.Persistance.Mappings;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import biocampo.demo.Domain.Model.Cultivation;
import biocampo.demo.Persistance.Entity.Cultivo;

@Mapper(componentModel = "spring", uses = {PlantMapper.class})
public interface CultivationMapper {

    @Mappings({
        @Mapping(source = "idCultivo", target = "cultivationId"),
        @Mapping(source = "planta", target = "plant"),
        @Mapping(source = "hectareas", target = "hectares"),
        @Mapping(source = "paquetesRequeridos",target= "requiredPackages"),
        @Mapping(source = "costo", target = "cost"),
        @Mapping(source = "fechaCultivo", target = "startDate"),
        @Mapping(source = "cadaRiego", target = "eachIrrigation"),
        @Mapping(source = "temporada", target = "season"),
        @Mapping(source = "fechaEstimadaCosecha", target = "endDate"),
    })

    Cultivation toCultivation(Cultivo cultivo);
    List<Cultivation> toCultivations(List<Cultivo> cultivos);

    @InheritInverseConfiguration
    @Mapping(target = "perdida", ignore = true)
    @Mapping(target = "insumo", ignore = true)
    @Mapping(target = "empleados", ignore = true)
    Cultivo toCultivo(Cultivation cultivation);
}
