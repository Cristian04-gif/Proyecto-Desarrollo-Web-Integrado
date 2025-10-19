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
        Input input = inputRepository.getById(inputCultivation.getInput().getInputId())
                .orElseThrow(() -> new IllegalArgumentException("no existe el insumo"));
        Cultivation cultivation = cultivationRepository.getById(inputCultivation.getCultivation().getCultivationId())
                .orElseThrow(() -> new IllegalArgumentException("NO existe el cultivo relacionado"));

        inputCultivation.setInput(input);
        inputCultivation.setCultivation(cultivation);

        //
        if (inputCultivation.getQuantity() > input.getUnit()) {
            System.out.println("Error! no hay suficientes insumos");
            throw new IllegalArgumentException("Error! no hay suficientes insumos");
        } else {
            input.setUnit(input.getUnit() - inputCultivation.getQuantity());
            inputRepository.save(input);
            cultivation.setCost(cultivation.getCost() + (input.getPrice() * inputCultivation.getQuantity()));
            cultivationRepository.save(cultivation);

        }
        //
        return inputCultivationRepository.save(inputCultivation);

    }

    public InputCultivation updateInputApplication(Long id, InputCultivation inputCultivation) {
        InputCultivation toUpdate = inputCultivationRepository.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe ese objeto"));

        Input input = inputRepository.getById(inputCultivation.getInput().getInputId())
                .orElseThrow(() -> new IllegalArgumentException("No existe el insumo"));

        Cultivation cultivation = cultivationRepository
                .getById(inputCultivation.getCultivation().getCultivationId())
                .orElseThrow(() -> new IllegalArgumentException("El cultivo relacionado no existe"));

        //
        System.out.println("cantidad Insumo antes de actualizar: "+input.getUnit());
        input.setUnit(input.getUnit() + toUpdate.getQuantity());
        if (inputCultivation.getQuantity() < input.getUnit()) {
            input.setUnit(input.getUnit() - inputCultivation.getQuantity());
        }
        System.out.println("Cantidad de insumo despues de actualizar: "+input.getUnit());
        inputRepository.save(input);

        toUpdate.setInput(input);
        toUpdate.setCultivation(cultivation);
        cultivation.setCost(cultivation.getCost() - (toUpdate.getQuantity() * input.getPrice()));
        
        toUpdate.setQuantity(inputCultivation.getQuantity());
        cultivation.setCost(cultivation.getCost() + (toUpdate.getQuantity() * input.getPrice()));
        cultivationRepository.save(cultivation);
        //

        return inputCultivationRepository.save(toUpdate);
    }

    public void deleteInputApplication(Long id) {
        inputCultivationRepository.deleteById(id);
    }
}
