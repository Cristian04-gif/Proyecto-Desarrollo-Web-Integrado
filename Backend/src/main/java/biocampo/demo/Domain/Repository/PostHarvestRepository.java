package biocampo.demo.Domain.Repository;

import java.util.List;
import java.util.Optional;

import biocampo.demo.Domain.Model.PostHarvest;

public interface PostHarvestRepository {
    List<PostHarvest> getAll();
    Optional<PostHarvest> getById(Long id);
    PostHarvest save(PostHarvest postHarvest);
    void deleteById(Long id);
}
