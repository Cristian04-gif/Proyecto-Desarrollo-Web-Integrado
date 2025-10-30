package biocampo.demo.Persistance.Mappings;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import biocampo.demo.Domain.Model.Product;
import biocampo.demo.Persistance.Entity.Producto;

@Mapper(componentModel = "spring", uses = {PostHarvestMapper.class, PlantCategoryMapper.class})
public interface ProductMapper {
    @Mappings({
            @Mapping(source = "idProducto", target = "productId"),
            @Mapping(source = "postCosecha", target = "postHarvest"),
            @Mapping(source = "imgProducto", target = "imageUrl"),
            @Mapping(source = "etiqueta", target = "name"),
            @Mapping(source = "descripcion", target = "description"),
            @Mapping(source = "peso", target = "weight"),
            @Mapping(source = "unidadMedida", target = "unitMeasure"),
            @Mapping(source = "precio", target = "price"),
            @Mapping(source = "cantidad", target = "quantity"),
            @Mapping(source = "stock", target = "stock"),
            @Mapping(source = "disponible", target = "active"),
            @Mapping(source = "codigoLote", target = "lotCode"),
            @Mapping(source = "fechaRegistro", target = "registrationDate"),
            @Mapping(source = "fechaActualizacion", target = "updateDate"),
            @Mapping(source = "categoriaPlanta", target = "plantCategory")
    })

    Product toProduct(Producto producto);

    List<Product> toProducts(List<Producto> productos);

    @InheritInverseConfiguration
    @Mapping(target = "detalles", ignore = true)
    Producto toProducto(Product product);
    List<Producto> toProductos(List<Product> products);

}