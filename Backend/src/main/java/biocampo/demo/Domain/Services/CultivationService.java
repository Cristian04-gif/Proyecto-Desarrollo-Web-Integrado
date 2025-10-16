package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.Cultivation;
import biocampo.demo.Domain.Model.Plant;
import biocampo.demo.Domain.Repository.CultivationRepository;
import biocampo.demo.Domain.Repository.PlantRepository;
import jakarta.transaction.Transactional;
@Service
public class CultivationService {

    @Autowired
    private CultivationRepository cultivationRepository;

    @Autowired
    private PlantRepository plantRepository;

    public List<Cultivation> getAllCultivations() {
        return cultivationRepository.getAll();
    }

    public Optional<Cultivation> getCultivationById(Long id) {
        return cultivationRepository.getById(id);
    }

    public Cultivation registerCultivation(Cultivation cultivation) {
        Optional<Plant> plant = plantRepository.getById(cultivation.getPlant().getPlantId());
        if (plant.isPresent()) {
            cultivation.setPlant(plant.get());
            return cultivationRepository.save(cultivation);
        } else {
            return null;
        }
    }

    public Cultivation updateCultivation(Long id, Cultivation cultivation) {
        Optional<Cultivation> existing = cultivationRepository.getById(id);
        if (existing.isPresent()) {
            Cultivation toUpdate = existing.get();
            toUpdate.setHectares(cultivation.getHectares());
            toUpdate.setCost(cultivation.getCost());
            toUpdate.setStartDate(cultivation.getStartDate());
            toUpdate.setEndDate(cultivation.getEndDate());
            toUpdate.setEachIrrigation(cultivation.getEachIrrigation());
            toUpdate.setSeason(cultivation.getSeason());

            Optional<Plant> plant = plantRepository.getById(cultivation.getPlant().getPlantId());
            plant.ifPresent(toUpdate::setPlant);

            return cultivationRepository.save(toUpdate);
        } else {
            return null;
        }
    }

    public void deleteCultivation(Long id) {
        cultivationRepository.deleteById(id);
    }

    public List<Cultivation> getBySeason(String season) {
        return cultivationRepository.findBySeason(season);
    }
}
