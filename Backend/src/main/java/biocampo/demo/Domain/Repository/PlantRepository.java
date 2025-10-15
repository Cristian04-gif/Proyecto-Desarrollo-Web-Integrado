package biocampo.demo.Domain.Repository;

import java.util.List;
import java.util.Optional;

import biocampo.demo.Domain.Model.Plant;
import biocampo.demo.Domain.Model.PlantCategory;

public interface PlantRepository {

    List<Plant> getAll();
    Optional<Plant> getById(Long id);
    Plant save(Plant plant);
    void deleteById(Long id);
    List<Plant> getAvailable(boolean disponible);
    List<Plant> getByCategory(PlantCategory category);
}
