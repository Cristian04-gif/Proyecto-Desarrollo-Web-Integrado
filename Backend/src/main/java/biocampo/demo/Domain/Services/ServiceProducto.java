package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.Product;
import biocampo.demo.Domain.Repository.ProductRepository;

@Service
public class ServiceProducto {

    @Autowired
    private ProductRepository productRepository;

    // Obtener todos los productos
    public List<Product> getAllProducts() {
        return productRepository.getAll(); // <-- Cambiado de findAll() a getAll()
    }

    // Obtener producto por ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.getById(id); // <-- Cambiado de findById() a getById()
    }

    // Registrar nuevo producto
    public Product registerProduct(Product product) {
        return productRepository.save(product); // save() funciona igual
    }

    // Actualizar producto existente
    public Product updateProduct(Long id, Product updatedData) {
        Optional<Product> existingProduct = productRepository.getById(id);

        if (existingProduct.isPresent()) {
            Product toUpdate = existingProduct.get();

            if (updatedData.getPostHarvest() != null)
                toUpdate.setPostHarvest(updatedData.getPostHarvest());
            if (updatedData.getImageUrl() != null)
                toUpdate.setImageUrl(updatedData.getImageUrl());
            if (updatedData.getName() != null)
                toUpdate.setName(updatedData.getName());
            if (updatedData.getDescription() != null)
                toUpdate.setDescription(updatedData.getDescription());
            if (updatedData.getWeight() != 0)
                toUpdate.setWeight(updatedData.getWeight());
            if (updatedData.getPrice() != null)
                toUpdate.setPrice(updatedData.getPrice());
            if (updatedData.getStock() != 0)
                toUpdate.setStock(updatedData.getStock());

            // Booleano
            toUpdate.setActive(updatedData.isActive());

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
