package biocampo.demo.Domain.Repository;

import java.util.List;
import java.util.Optional;

import biocampo.demo.Domain.Model.PlantCategory;

public interface PlantCategoryRepository {

    List<PlantCategory> getAll();
    Optional<PlantCategory> getPlantCategory(Long id);
    PlantCategory save(PlantCategory category);
    void delete(Long id);

}

