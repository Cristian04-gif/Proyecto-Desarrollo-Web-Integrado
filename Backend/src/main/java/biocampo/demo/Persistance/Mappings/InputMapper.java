package biocampo.demo.Persistance.Mappings;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import biocampo.demo.Domain.Model.Input;
import biocampo.demo.Persistance.Entity.Insumo;

@Mapper(componentModel = "spring", uses = {SupplierMapper.class})
public interface InputMapper {

    @Mappings({
        @Mapping(source = "idInsumo", target = "inputId"),
        @Mapping(source = "nombre", target = "name"),
        @Mapping(source = "tipo", target = "type"),
        @Mapping(source = "unidadMedida", target = "unitStatet"),
        @Mapping(source = "stock", target = "stock"),
        @Mapping(source = "precioUnitario", target = "priceUnit"),
        @Mapping(source = "costoTotal", target = "totalCost"),
        //@Mapping(source = "planta", target = "plant"),
        @Mapping(source = "proveedor", target = "supplier")
        //@Mapping(source = "cultivo", target = "cultivation"),
    })

    Input toInput(Insumo insumo);
    List<Input> toInputs(List<Insumo> insumos);

    @InheritInverseConfiguration
    @Mapping(target = "detallePedidos", ignore = true)
    @Mapping(target = "cultivoInsumos", ignore = true)
    Insumo toInsumo(Input input);
}
