package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.PlantCategory;
import biocampo.demo.Domain.Repository.PlantCategoryRepository;

@Service
public class PlantCategoryService {

    @Autowired
    private PlantCategoryRepository categoryRepository;

    // Obtener todas las categorías
    public List<PlantCategory> getAll() {
        return categoryRepository.getAll();
    }

    // Obtener categoría por ID
    public Optional<PlantCategory> getPlantCategory(Long id) {
        return categoryRepository.getPlantCategory(id);
    }

    // Registrar nueva categoría
    public PlantCategory registerCategory(PlantCategory category) {
        return categoryRepository.save(category);
    }

    // Actualizar categoría existente
    public PlantCategory updateCategory(Long id, PlantCategory updatedCategory) {
        Optional<PlantCategory> existing = categoryRepository.getPlantCategory(id);

        if (existing.isPresent()) {
            PlantCategory toUpdate = existing.get();

            // Evita sobreescribir con nulos
            if (updatedCategory.getCategoryName() != null) {
                toUpdate.setCategoryName(updatedCategory.getCategoryName());
            }

            return categoryRepository.save(toUpdate);
        } else {
            // Podrías devolver null o lanzar excepción personalizada
            return null;
        }
    }

    // Eliminar categoría
    public void deleteCategory(Long id) {
        categoryRepository.delete(id);
    }
}
