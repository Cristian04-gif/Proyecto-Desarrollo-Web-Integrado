package biocampo.demo.Persistance.Mappings;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import biocampo.demo.Domain.Model.SaleDetail;
import biocampo.demo.Persistance.Entity.DetalleVenta;

@Mapper(componentModel = "spring", uses = {SaleMapper.class, ProductMapper.class})
public interface SaleDetailMapper {

    @Mappings({
        @Mapping(source = "idDetalleVenta", target = "saleDetailId"),
        @Mapping(source = "venta", target = "sale"),
        @Mapping(source = "producto", target = "product"),
        @Mapping(source = "cantidad", target = "quantity"),
        @Mapping(source = "subtotal", target = "subTotal"),
        //@Mapping(source = "pago", target = "paymentMethod"),
    })

    SaleDetail toSaleDetail(DetalleVenta detalleVenta);

    List<SaleDetail> toSaleDetails(List<DetalleVenta> detalleVentas);

    @InheritInverseConfiguration
    DetalleVenta toDetalleVenta(SaleDetail saleDetail);
    List<DetalleVenta> toDetalleVentas(List<SaleDetail> detalleVentas);
}
