package biocampo.demo.Domain.Services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import biocampo.demo.Domain.Model.PlantCategory;
import biocampo.demo.Domain.Model.PostHarvest;
import biocampo.demo.Domain.Model.Product;
import biocampo.demo.Domain.Repository.PlantCategoryRepository;
import biocampo.demo.Domain.Repository.PostHarvestRepository;
import biocampo.demo.Domain.Repository.ProductRepository;
import biocampo.demo.Persistance.CRUD.RepoCategoriaPlanta;
import biocampo.demo.Persistance.CRUD.RepoEmpleado;
import biocampo.demo.Persistance.CRUD.RepoPostCosecha;
import biocampo.demo.Persistance.CRUD.RepoProducto;
import biocampo.demo.Persistance.Entity.CategoriaPlanta;
import biocampo.demo.Persistance.Entity.Empleado;
import biocampo.demo.Persistance.Entity.PostCosecha;
import biocampo.demo.Persistance.Entity.Producto;
import biocampo.demo.Persistance.Entity.PostCosecha.EstadoPostCosecha;
import biocampo.demo.Persistance.Mappings.ProductMapper;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PostHarvestRepository postHarvestRepository;

    @Autowired
    private PlantCategoryRepository plantCategoryRepository;

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private RepoProducto repoProducto;
    @Autowired
    private RepoPostCosecha repoPostCosecha;
    @Autowired
    private RepoEmpleado repoEmpleado;
    @Autowired
    private RepoCategoriaPlanta repoCategoriaPlanta;

    // Obtener todos los productos
    public List<Product> getAllProducts() {
        return productRepository.getAll(); // <-- Cambiado de findAll() a getAll()
    }

    // Obtener producto por ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.getById(id); // <-- Cambiado de findById() a getById()
    }

    public List<Product> getPriceLess(BigDecimal price) {
        return productRepository.getByPriceLess(price);
    }

    public List<Product> getActive(Boolean active) {
        return productRepository.getActive(active);
    }

    @Transactional
    public Product registerProduct(Product product) {
        Producto productoEntity = productMapper.toProducto(product);

        PostCosecha postCosechaEntity = repoPostCosecha.findById(productoEntity.getPostCosecha().getIdPostCosecha())
                .orElseThrow();
        CategoriaPlanta categoriaPlantaEntity = repoCategoriaPlanta
                .findById(productoEntity.getCategoriaPlanta().getIdCategoriaPlanta()).orElseThrow();
        
        for (Empleado emp : postCosechaEntity.getEmpleados()) {
            Empleado existeEmpleado = repoEmpleado.findById(emp.getIdEmpleado()).orElseThrow();
            existeEmpleado.setDisponible(true);
            repoEmpleado.save(existeEmpleado);
        }
        postCosechaEntity.setEstado(EstadoPostCosecha.CONVERTIDA_EN_PRODUCTO);
        postCosechaEntity.setFechaConversion(LocalDateTime.now());
        repoPostCosecha.save(postCosechaEntity);
        

        double precioKg = postCosechaEntity.getPrecioKg();
        double peso = productoEntity.getPeso();
        double kgComercializables = postCosechaEntity.getKgComerciables();
        double precioFinal = precioKg * peso;
        double stock = Math.floor(kgComercializables / peso);

        productoEntity.setPrecio(precioFinal);
        productoEntity.setCantidad((int) stock);
        productoEntity.setStock((int) stock);
        if (productoEntity.getStock() > (productoEntity.getCantidad() * 0.15)) {
            productoEntity.setDisponible(true);
        } else {
            productoEntity.setDisponible(false);
        }
        productoEntity.setPostCosecha(postCosechaEntity);
        productoEntity.setCategoriaPlanta(categoriaPlantaEntity);
        Producto productoGuardado =repoProducto.save(productoEntity);
        return productMapper.toProduct(productoGuardado);
    }
    

    @Transactional
    public Product updateProduct(Long id, Product product) {
        Product existProduct = productRepository.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("NO existe el producto"));

        PostHarvest postHarvest = postHarvestRepository.getById(existProduct.getPostHarvest().getPostHarvestId())
                .orElseThrow(() -> new IllegalArgumentException("NO existe la postcosecha"));
        PlantCategory plantCategory = plantCategoryRepository
                .getPlantCategory(existProduct.getPlantCategory().getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("No existe la categoria"));

        existProduct.setPlantCategory(plantCategory);
        existProduct.setPostHarvest(postHarvest);
        if (product.getImageUrl() != null) {
            existProduct.setImageUrl(product.getImageUrl());
        }
        if (product.getName() != null) {
            existProduct.setName(product.getName());
        }
        if (product.getDescription() != null) {
            existProduct.setDescription(product.getDescription());
        }
        if (product.getWeight() > 0 && product.getWeight() != existProduct.getWeight()) {
            existProduct.setWeight(product.getWeight());
        }
        if (product.getUnitMeasure() != null) {
            existProduct.setUnitMeasure(product.getUnitMeasure());
        }

        double precioKg = postHarvest.getPriceKg();
        double peso = existProduct.getWeight();
        double kgComercializables = postHarvest.getKgComerciables();
        double precioFinal = precioKg * peso;
        double stock = Math.floor(kgComercializables / peso);

        existProduct.setPrice(precioFinal);
        existProduct.setQuantity((int) stock);
        existProduct.setStock((int) stock);
        if (existProduct.getStock() > existProduct.getQuantity() * 0.15) {
            existProduct.setActive(true);
        } else {
            existProduct.setActive(false);
        }
        return productRepository.save(existProduct);
    }

    // Eliminar producto
    public void deleteProduct(Long id) {
        Optional<Product> existingProduct = productRepository.getById(id);
        if (existingProduct.isPresent()) {
            productRepository.deleteById(id);
        }
    }
}
