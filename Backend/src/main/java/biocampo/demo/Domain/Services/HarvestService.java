package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.Cultivation;
import biocampo.demo.Domain.Model.Harvest;
import biocampo.demo.Domain.Repository.CultivationRepository;
import biocampo.demo.Domain.Repository.HarvestRepository;
import biocampo.demo.Persistance.Entity.Cosecha.Recolector;
import biocampo.demo.Persistance.Entity.Cosecha.Temporada;

@Service
public class HarvestService {

    @Autowired
    private HarvestRepository harvestRepository;

    @Autowired
    private CultivationRepository cultivationRepository;

    public List<Harvest> getAll() {
        return harvestRepository.getAll();
    }

    public Optional<Harvest> getHarvest(Long id) {
        return harvestRepository.getByIdHarvest(id);
    }

    public Harvest registerHarvest(Harvest harvest) {
        Cultivation exist = cultivationRepository.getById(harvest.getCultivation().getCultivationId())
                .orElseThrow(() -> new IllegalArgumentException("El cultivo relacionado no existe"));
        harvest.setCultivation(exist);

        boolean existTemp = false;
        for (Temporada temp : Temporada.values()) {
            if (temp.toString().equalsIgnoreCase(harvest.getSeason())) {
                harvest.setSeason(temp.name());
                existTemp = true;
                break;
            }
        }
        if (!existTemp) {
            throw new IllegalArgumentException("La temporada no existe");
        }

        boolean existCollector = false;
        for (Recolector recolec : Recolector.values()) {
            if (recolec.toString().equalsIgnoreCase(harvest.getCollector())) {
                harvest.setCollector(recolec.name());
                existCollector = true;
                break;
            }
        }
        if (!existCollector) {
            throw new IllegalArgumentException("La tipo de recolecion no existe");
        }
        return harvestRepository.save(harvest);
    }

    public Harvest updateHarvest(Long id, Harvest harvest) {
        Harvest updateHarvest = harvestRepository.getByIdHarvest(id)
                .orElseThrow(() -> new IllegalArgumentException("La cosecha no exixte"));

        Cultivation existCultivation = cultivationRepository
                .getById(harvest.getCultivation().getCultivationId())
                .orElseThrow(() -> new IllegalArgumentException("El cultivo relacionado no existe"));
        harvest.setCultivation(existCultivation);

        boolean existTemp = false;
        for (Temporada temp : Temporada.values()) {
            if (temp.toString().equalsIgnoreCase(harvest.getSeason())) {
                harvest.setSeason(temp.name());
                existTemp = true;
                break;
            }
        }
        if (!existTemp) {
            throw new IllegalArgumentException("La temporada no existe");
        }

        boolean existCollector = false;
        for (Recolector recolec : Recolector.values()) {
            if (recolec.toString().equalsIgnoreCase(harvest.getCollector())) {
                harvest.setCollector(recolec.name());
                existCollector = true;
                break;
            }
        }
        if (!existCollector) {
            throw new IllegalArgumentException("La tipo de recolecion no existe");
        }

        // Harvest updateHarvest = existHarvest;
        updateHarvest.setCultivation(harvest.getCultivation());
        updateHarvest.setSeason(harvest.getSeason());
        updateHarvest.setCollector(harvest.getCollector());
        return harvestRepository.save(updateHarvest);

    }

    public void deleteHarvest(Long id) {
        harvestRepository.deleteById(id);
    }
}
