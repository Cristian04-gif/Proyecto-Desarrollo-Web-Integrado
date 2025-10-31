package biocampo.demo.Persistance.Mappings;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import biocampo.demo.Domain.Model.Order;
import biocampo.demo.Persistance.Entity.Pedido;

@Mapper(componentModel = "spring", uses = {SupplierMapper.class})
public interface OrderMapper {

    @Mappings({
        @Mapping(source = "idPedido", target = "orderId"),
        @Mapping(source = "fecha", target = "date"),
        @Mapping(source = "total", target = "total"),
        @Mapping(source = "proveedor", target = "supplier"),
        //@Mapping(source = "detallePedidos", target = "orderDetails")
    })

    Order toInputSupplier(Pedido proveedorInsumo);
    List<Order> toInputSuppliers(List<Pedido> proveedorInsumos);

    @InheritInverseConfiguration
    @Mapping(target = "detallePedidos", ignore = true)
    Pedido toProveedorInsumo(Order inputSupplier);
}
