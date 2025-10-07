package biocampo.demo.Persistance.Mappings;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import biocampo.demo.Domain.Model.InputSupplier;
import biocampo.demo.Persistance.Entity.ProveedorInsumo;

@Mapper(componentModel = "spring", uses = {InputMapper.class, SupplierMapper.class})
public interface InputSupplierMapper {

    @Mappings({
        @Mapping(source = "id", target = "inputSupplierId"),
        @Mapping(source = "proveedor", target = "supplier"),
        @Mapping(source = "insumo", target = "input"),
        @Mapping(source = "precio", target = "price"),
    })

    InputSupplier toInputSupplier(ProveedorInsumo proveedorInsumo);
    List<InputSupplier> toInputSuppliers(List<ProveedorInsumo> proveedorInsumos);

    @InheritInverseConfiguration
    ProveedorInsumo toProveedorInsumo(InputSupplier inputSupplier);
}
