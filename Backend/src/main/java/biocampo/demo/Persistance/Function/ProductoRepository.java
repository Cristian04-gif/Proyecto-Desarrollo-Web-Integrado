package biocampo.demo.Persistance.Function;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import biocampo.demo.Domain.Model.Product;
import biocampo.demo.Domain.Repository.ProductRepository;
import biocampo.demo.Persistance.CRUD.RepoProducto;
import biocampo.demo.Persistance.Entity.Producto;
import biocampo.demo.Persistance.Mappings.ProductMapper;

@Repository
public class ProductoRepository implements ProductRepository {

    @Autowired
    private RepoProducto repoProducto;
    @Autowired
    private ProductMapper productMapper;


    @Override
    public List<Product> getAll() {
        List<Producto> list = repoProducto.findAll();
        return productMapper.toProducts(list);
    }

    @Override
    public Optional<Product> getById(Long id) {
        return repoProducto.findById(id).map(producto -> productMapper.toProduct(producto));
    }

    @Override
    public Product save(Product product) {
        Producto producto = productMapper.toProducto(product);
        Producto producto2 = repoProducto.save(producto);
        return productMapper.toProduct(producto2);
    }

    @Override
    public void deleteById(Long id) {
        repoProducto.deleteById(id);
    }

    @Override
    public List<Product> getCategory(Long idCategory) {
        List<Producto> list = repoProducto.findByCategoriaPlantaIdCategoriaPlanta(idCategory);
        return productMapper.toProducts(list);
    }

    @Override
    public List<Product> getActive(boolean active) {
        List<Producto> list = repoProducto.findByDisponible(active);
        return productMapper.toProducts(list);
    }

    @Override
    public List<Product> getByPriceLess(BigDecimal price) {
        List<Producto> menorPercio = repoProducto.findByPrecioLessThan(price);
        return productMapper.toProducts(menorPercio);
    }

    /*@Override
    public List<Product> getAllById(List<Product> products) {
        List<Producto> productos = productMapper.toProductos(products);
        List<Long> productosIds = 
        List<Producto> productos2 = repoProducto.findAllById(productosIds);
    }*/

}
