package biocampo.demo.Persistance.Function;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import biocampo.demo.Domain.Model.Harvest;
import biocampo.demo.Domain.Repository.HarvestRepository;
import biocampo.demo.Persistance.CRUD.RepoCosecha;
import biocampo.demo.Persistance.Entity.Cosecha;
import biocampo.demo.Persistance.Mappings.HarvestMapper;

@Repository
public class CosechaRepository implements HarvestRepository {

    @Autowired
    private RepoCosecha repoCosecha;
    @Autowired
    private HarvestMapper harvestMapper;

    @Override
    public List<Harvest> getAll() {
        List<Cosecha> all = repoCosecha.findAll();
        return harvestMapper.toHarvests(all);
    }

    @Override
    public Optional<Harvest> getByIdHarvest(Long id) {
        return repoCosecha.findById(id).map(cosecha -> harvestMapper.toHarvest(cosecha));
    }

    @Override
    public Harvest save(Harvest harvest) {
        Cosecha cosecha = harvestMapper.toCosecha(harvest);
        Cosecha cosechaGuardada = repoCosecha.save(cosecha);
        return harvestMapper.toHarvest(cosechaGuardada);
    }

    @Override
    public void deleteById(Long id) {
        repoCosecha.deleteById(id);
    }

}
