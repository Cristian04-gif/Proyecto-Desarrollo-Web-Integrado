package biocampo.demo.Persistance.Mappings;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import biocampo.demo.Domain.Model.Supplier;
import biocampo.demo.Persistance.Entity.Proveedor;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    @Mappings({
        @Mapping(source = "idProveedor", target = "supplierId"),
        @Mapping(source = "nombre", target = "name"),
        @Mapping(source = "ruc", target = "ruc"),
        @Mapping(source = "telefono", target = "phone"),
        @Mapping(source = "email", target = "email"),
        @Mapping(source = "direccion", target = "address"),
    })

    Supplier toSupplier(Proveedor proveedor);
    List<Supplier> toSuppliers(List<Proveedor> proveedors);

    @InheritInverseConfiguration
    @Mapping(target = "pedidos", ignore = true)
    Proveedor toProveedor(Supplier supplier);
}
