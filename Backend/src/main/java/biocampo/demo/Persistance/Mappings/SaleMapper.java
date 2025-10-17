package biocampo.demo.Persistance.Mappings;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import biocampo.demo.Domain.Model.Sale;
import biocampo.demo.Persistance.Entity.Venta;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface SaleMapper {
    @Mappings({
        @Mapping(source = "idVenta", target = "saleId"),
        @Mapping(source = "cliente", target = "customer"),
        @Mapping(source = "fechaVenta", target = "saleDate"),
        @Mapping(source = "total", target = "total"),
        @Mapping(source = "pago", target = "paymentMethod")
    })

    Sale toSale(Venta venta);
    List<Sale> toSales(List<Venta> ventas);

    @InheritInverseConfiguration
    @Mapping(target = "detalle", ignore = true)
    Venta toVenta(Sale sale);
}
