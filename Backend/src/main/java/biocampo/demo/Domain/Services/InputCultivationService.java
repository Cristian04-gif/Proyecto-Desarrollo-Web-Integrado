package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.Input;
import biocampo.demo.Domain.Model.Cultivation;
import biocampo.demo.Domain.Model.InputCultivation;
import biocampo.demo.Domain.Repository.InputRepository;
import biocampo.demo.Domain.Repository.CultivationRepository;
import biocampo.demo.Domain.Repository.INputCultivationRepository;

@Service
public class InputCultivationService {

    @Autowired
    private INputCultivationRepository inputCultivationRepository;

    @Autowired
    private InputRepository inputRepository;

    @Autowired
    private CultivationRepository cultivationRepository;

    public List<InputCultivation> getAllInputApplications() {
        return inputCultivationRepository.getAll();
    }

    public Optional<InputCultivation> getInputApplicationById(Long id) {
        return inputCultivationRepository.getById(id);
    }

    public InputCultivation registerInputApplication(InputCultivation inputCultivation) {
        Optional<Input> input = inputRepository.getById(inputCultivation.getInput().getInputId());
        Optional<Cultivation> cultivation = cultivationRepository.getById(inputCultivation.getCultivation().getCultivationId());

        if (input.isPresent() && cultivation.isPresent()) {
            inputCultivation.setInput(input.get());
            inputCultivation.setCultivation(cultivation.get());
            return inputCultivationRepository.save(inputCultivation);
        } else {
            return null;
        }
    }

    public InputCultivation updateInputApplication(Long id, InputCultivation inputCultivation) {
        Optional<InputCultivation> existing = inputCultivationRepository.getById(id);
        if (existing.isPresent()) {
            InputCultivation toUpdate = existing.get();
            toUpdate.setQuantity(inputCultivation.getQuantity());

            Optional<Input> input = inputRepository.getById(inputCultivation.getInput().getInputId());
            input.ifPresent(toUpdate::setInput);

            Optional<Cultivation> cultivation = cultivationRepository.getById(inputCultivation.getCultivation().getCultivationId());
            cultivation.ifPresent(toUpdate::setCultivation);

            return inputCultivationRepository.save(toUpdate);
        } else {
            return null;
        }
    }

    public void deleteInputApplication(Long id) {
        inputCultivationRepository.deleteById(id);
    }
}
