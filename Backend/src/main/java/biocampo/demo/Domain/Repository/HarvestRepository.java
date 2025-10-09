package biocampo.demo.Domain.Repository;

import java.util.List;
import java.util.Optional;

import biocampo.demo.Domain.Model.Harvest;

public interface HarvestRepository {

    List<Harvest> getAll();
    Optional<Harvest> getByIdHarvest(Long id);
    Harvest save(Harvest harvest);
    void deleteById(Long id);
}
