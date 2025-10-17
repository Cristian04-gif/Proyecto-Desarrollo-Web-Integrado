package biocampo.demo.Domain.Services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.PlantCategory;
import biocampo.demo.Domain.Model.PostHarvest;
import biocampo.demo.Domain.Model.Product;
import biocampo.demo.Domain.Repository.PlantCategoryRepository;
import biocampo.demo.Domain.Repository.PostHarvestRepository;
import biocampo.demo.Domain.Repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PostHarvestRepository postHarvestRepository;

    @Autowired
    private PlantCategoryRepository plantCategoryRepository;

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

    // Registrar nuevo producto
    public Product registerProduct(Product product) {
        Optional<PostHarvest> exist = postHarvestRepository.getById(product.getPostHarvest().getPostHarvestId());
        if (exist.isPresent()) {
            PostHarvest postHarvest = exist.get();
            product.setPostHarvest(postHarvest);
            product.setStock(postHarvest.getStock());
        }

        Optional<PlantCategory> existeCat = plantCategoryRepository
                .getPlantCategory(product.getPlantCategory().getCategoryId());
        if (existeCat.isPresent()) {
            product.setPlantCategory(existeCat.get());
        } else {
            throw new IllegalArgumentException("Error! la categoria no existe");
        }
        return productRepository.save(product); // save() funciona igual
    }

    // Actualizar producto existente
    public Product updateProduct(Long id, Product updatedData) {
        Optional<Product> existingProduct = productRepository.getById(id);

        if (existingProduct.isPresent()) {
            Product toUpdate = existingProduct.get();

            if (updatedData.getPostHarvest() != null) {
                Optional<PostHarvest> exist = postHarvestRepository
                        .getById(updatedData.getPostHarvest().getPostHarvestId());
                if (exist.isPresent()) {
                    toUpdate.setPostHarvest(exist.get());
                } else {
                    throw new IllegalArgumentException("error! el producto no fue tratado");
                }
            }

            if (updatedData.getImageUrl() != null)
                toUpdate.setImageUrl(updatedData.getImageUrl());
            if (updatedData.getName() != null)
                toUpdate.setName(updatedData.getName());
            if (updatedData.getDescription() != null)
                toUpdate.setDescription(updatedData.getDescription());
            if (updatedData.getWeight() > 0)
                toUpdate.setWeight(updatedData.getWeight());
            if (updatedData.getPrice() != null)
                toUpdate.setPrice(updatedData.getPrice());
            if (updatedData.getStock() > 0)
                toUpdate.setStock(updatedData.getStock());
            toUpdate.setActive(true);
            if (updatedData.getPlantCategory() != null) {
                Optional<PlantCategory> exist = plantCategoryRepository
                        .getPlantCategory(updatedData.getPlantCategory().getCategoryId());
                if (exist.isPresent()) {
                    toUpdate.setPlantCategory(exist.get());
                } else {
                    throw new IllegalArgumentException("Erro! la categoria no existe");
                }
            }

            return productRepository.save(toUpdate);
        } else {
            return null;
        }
    }

    // Eliminar producto
    public void deleteProduct(Long id) {
        Optional<Product> existingProduct = productRepository.getById(id);
        if (existingProduct.isPresent()) {
            productRepository.deleteById(id);
        }
    }
}
