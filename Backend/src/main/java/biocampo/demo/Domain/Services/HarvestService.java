package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.Cultivation;
import biocampo.demo.Domain.Model.Harvest;
import biocampo.demo.Domain.Repository.CultivationRepository;
import biocampo.demo.Domain.Repository.HarvestRepository;

@Service
public class HarvestService {

    @Autowired
    private HarvestRepository harvestRepository;

    @Autowired
    private CultivationRepository cultivationRepository;

    public List<Harvest> getAllHarvests() {
        return harvestRepository.getAll();
    }

    public Optional<Harvest> getHarvestById(Long id) {
        return harvestRepository.getByIdHarvest(id);
    }

    public Harvest registerHarvest(Harvest harvest) {
        Optional<Cultivation> cultivation = cultivationRepository.getById(harvest.getCultivation().getCultivationId());
        if (cultivation.isPresent()) {
            harvest.setCultivation(cultivation.get());
            return harvestRepository.save(harvest);
        } else {
            return null;
        }
    }

    public Harvest updateHarvest(Long id, Harvest harvest) {
        Optional<Harvest> existing = harvestRepository.getByIdHarvest(id);
        if (existing.isPresent()) {
            Harvest toUpdate = existing.get();
            toUpdate.setDateHarvested(harvest.getDateHarvested());
            toUpdate.setSeason(harvest.getSeason());
            toUpdate.setCollector(harvest.getCollector());

            Optional<Cultivation> cultivation = cultivationRepository.getById(harvest.getCultivation().getCultivationId());
            cultivation.ifPresent(toUpdate::setCultivation);

            return harvestRepository.save(toUpdate);
        } else {
            return null;
        }
    }

    public void deleteHarvest(Long id) {
        harvestRepository.deleteById(id);
    }
}
