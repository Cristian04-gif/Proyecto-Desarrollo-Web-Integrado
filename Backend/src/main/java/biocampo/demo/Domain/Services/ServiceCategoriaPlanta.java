package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.PlantCategory;
import biocampo.demo.Domain.Repository.RepoCategoriaPlanta;

@Service
public class ServiceCategoriaPlanta {

    @Autowired
    private RepoCategoriaPlanta repoCategoriaPlanta;

    // Obtener todas las categorías
    public List<PlantCategory> getAllCategories() {
        return repoCategoriaPlanta.getAll();
    }

    // Obtener categoría por ID
    public Optional<PlantCategory> getCategoryById(Long id) {
        return repoCategoriaPlanta.getById(id);
    }

    // Registrar nueva categoría
    public PlantCategory registerCategory(String name) {
        PlantCategory newCategory = new PlantCategory();
        newCategory.setCategoryName(name);
        return repoCategoriaPlanta.save(newCategory);
    }

    // Actualizar categoría existente
    public PlantCategory updateCategory(Long id, String name) {
        Optional<PlantCategory> existing = repoCategoriaPlanta.getById(id);

        if (existing.isPresent()) {
            PlantCategory toUpdate = existing.get();
            if (name != null)
                toUpdate.setCategoryName(name);
            return repoCategoriaPlanta.save(toUpdate);
        } else {
            // Si no existe, crear nueva
            PlantCategory newCategory = new PlantCategory();
            newCategory.setCategoryName(name);
            return repoCategoriaPlanta.save(newCategory);
        }
    }

    // Eliminar categoría
    public void deleteCategory(Long id) {
        Optional<PlantCategory> existing = repoCategoriaPlanta.getById(id);
        if (existing.isPresent()) {
            repoCategoriaPlanta.deleteById(id);
        }
    }
}
