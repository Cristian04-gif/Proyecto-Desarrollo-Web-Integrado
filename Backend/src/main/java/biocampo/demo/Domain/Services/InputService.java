package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.Input;
import biocampo.demo.Domain.Model.Plant;
import biocampo.demo.Domain.Model.Cultivation;
import biocampo.demo.Domain.Repository.InputRepository;
import biocampo.demo.Domain.Repository.PlantRepository;
import biocampo.demo.Domain.Repository.CultivationRepository;

@Service
public class InputService {

    @Autowired
    private InputRepository inputRepository;

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private CultivationRepository cultivationRepository;

    public List<Input> getAllInputs() {
        return inputRepository.getAll();
    }

    public Optional<Input> getInputById(Long id) {
        return inputRepository.getById(id);
    }

    public Input registerInput(Input input) {
        Optional<Plant> plant = plantRepository.getById(input.getPlant().getPlantId());
        Optional<Cultivation> cultivation = cultivationRepository.getById(input.getCultivation().getCultivationId());

        if (plant.isPresent() && cultivation.isPresent()) {
            input.setPlant(plant.get());
            input.setCultivation(cultivation.get());
            return inputRepository.save(input);
        } else {
            return null;
        }
    }

    public Input updateInput(Long id, Input input) {
        Optional<Input> existing = inputRepository.getById(id);
        if (existing.isPresent()) {
            Input toUpdate = existing.get();
            toUpdate.setName(input.getName());
            toUpdate.setType(input.getType());
            toUpdate.setDescription(input.getDescription());
            toUpdate.setUnit(input.getUnit());

            Optional<Plant> plant = plantRepository.getById(input.getPlant().getPlantId());
            plant.ifPresent(toUpdate::setPlant);

            Optional<Cultivation> cultivation = cultivationRepository.getById(input.getCultivation().getCultivationId());
            cultivation.ifPresent(toUpdate::setCultivation);

            return inputRepository.save(toUpdate);
        } else {
            return null;
        }
    }

    public void deleteInput(Long id) {
        inputRepository.deleteById(id);
    }
}
