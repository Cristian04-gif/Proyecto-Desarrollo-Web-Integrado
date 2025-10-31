package biocampo.demo.Persistance.Mappings;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import biocampo.demo.Domain.Model.OrderDetail;
import biocampo.demo.Persistance.Entity.DetallePedido;

@Mapper(componentModel = "spring", uses = {InputMapper.class, OrderMapper.class})
public interface OrderDetailMapper {

    @Mappings({
        @Mapping(source = "idDetalle", target = "detailId"),
        @Mapping(source = "cantidad", target = "amount"),
        @Mapping(source = "precioUnitario", target ="priceUnit"),
        @Mapping(source = "pedido", target = "order"),
        @Mapping(source = "insumo", target = "input"),
    })

    OrderDetail toOrderDetail(DetallePedido detallePedido);
    List<OrderDetail> toOrderDetails(List<DetallePedido> detallePedidos);

    @InheritInverseConfiguration
    DetallePedido toDetallePedido(OrderDetail detail);
}
