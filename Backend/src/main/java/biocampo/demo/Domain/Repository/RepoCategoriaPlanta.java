package biocampo.demo.Domain.Repository;

import java.util.List;
import java.util.Optional;

import biocampo.demo.Domain.Model.PlantCategory;

public interface RepoCategoriaPlanta {

    // Obtener todas las categorías
    List<PlantCategory> getAll();

    // Obtener categoría por ID
    Optional<PlantCategory> getById(Long id);

    // Guardar o actualizar categoría
    PlantCategory save(PlantCategory category);

    // Eliminar categoría por ID
    void deleteById(Long id);
}
