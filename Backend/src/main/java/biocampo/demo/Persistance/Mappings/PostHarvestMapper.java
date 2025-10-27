package biocampo.demo.Persistance.Mappings;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import biocampo.demo.Domain.Model.PostHarvest;
import biocampo.demo.Persistance.Entity.PostCosecha;

@Mapper(componentModel = "spring", uses = {HarvestMapper.class, EmployeeMapper.class})
public interface PostHarvestMapper {

    @Mappings({
        @Mapping(source = "idPostCosecha", target = "postHarvestId"),
        @Mapping(source = "cosecha", target = "harvest"),
        @Mapping(source = "fecha", target = "dateProcessed"),
        @Mapping(source = "costoAlmacenamiento", target = "storageCost"),
        @Mapping(source = "costoEmpleado", target = "costEmployee"),
        @Mapping(source = "precioUnidad", target = "unitPrice"),
        @Mapping(source = "unidadPerdida", target = "lossUnit"),
        @Mapping(source = "ingresoTotal", target = "totalReveneu"),
        @Mapping(source = "ganancia", target = "profit"),
        @Mapping(source = "observaciones", target = "observations"),
        @Mapping(source = "empleados", target = "employees")
    })

    PostHarvest toPostHarvest(PostCosecha postCosecha);

    List<PostHarvest> toPostHarvests(List<PostCosecha> postCosechas);

    @InheritInverseConfiguration
    PostCosecha toPostCosecha(PostHarvest postHarvest);
}
