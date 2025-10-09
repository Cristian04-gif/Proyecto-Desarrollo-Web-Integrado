package biocampo.demo.Persistance.Function;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import biocampo.demo.Domain.Model.PostHarvest;
import biocampo.demo.Domain.Repository.PostHarvestRepository;
import biocampo.demo.Persistance.CRUD.RepoPostCosecha;
import biocampo.demo.Persistance.Entity.PostCosecha;
import biocampo.demo.Persistance.Mappings.PostHarvestMapper;

@Repository
public class PostCosechaRepository implements PostHarvestRepository{

    @Autowired
    private RepoPostCosecha repoPostCosecha;
    @Autowired
    private PostHarvestMapper postHarvestMapper;

    @Override
    public List<PostHarvest> getAll() {
        List<PostCosecha> cosechas = repoPostCosecha.findAll();
        return postHarvestMapper.toPostHarvests(cosechas);
    }

    @Override
    public Optional<PostHarvest> getById(Long id) {
        return repoPostCosecha.findById(id).map(pc -> postHarvestMapper.toPostHarvest(pc));
    }

    @Override
    public PostHarvest save(PostHarvest postHarvest) {
        PostCosecha postCosecha = postHarvestMapper.toPostCosecha(postHarvest);
        PostCosecha postCosecha2 = repoPostCosecha.save(postCosecha);
        return postHarvestMapper.toPostHarvest(postCosecha2);
    }

    @Override
    public void deleteById(Long id) {
        repoPostCosecha.deleteById(id);
    }

}
