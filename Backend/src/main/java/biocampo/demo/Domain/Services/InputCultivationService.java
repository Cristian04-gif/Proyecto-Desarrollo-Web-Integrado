package biocampo.demo.Domain.Services;

import java.util.List;

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

    

    public InputCultivation registerInputApplication(InputCultivation inputCultivation) {
        Input input = inputRepository.getById(inputCultivation.getInput().getInputId())
                .orElseThrow(() -> new IllegalArgumentException("no existe el insumo"));
        Cultivation cultivation = cultivationRepository.getById(inputCultivation.getCultivation().getCultivationId())
                .orElseThrow(() -> new IllegalArgumentException("NO existe el cultivo relacionado"));

        inputCultivation.setInput(input);
        inputCultivation.setCultivation(cultivation);

        
        //
        return inputCultivationRepository.save(inputCultivation);

    }

    public void deleteInputApplication(Long idCultivo, Long idInsumo) {
        inputCultivationRepository.deleteById(idCultivo, idCultivo);
    }
}
