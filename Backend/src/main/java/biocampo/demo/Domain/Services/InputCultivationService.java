package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import biocampo.demo.Domain.Model.Input;
import biocampo.demo.Domain.Model.InputCultivation;

import biocampo.demo.Domain.Repository.INputCultivationRepository;
import biocampo.demo.Domain.Repository.InputRepository;

@Service
public class InputCultivationService {

    @Autowired
    private INputCultivationRepository inputCultivationRepository;
    @Autowired
    private InputRepository inputRepository;

    public List<InputCultivation> getAllInputApplications() {
        return inputCultivationRepository.getAll();
    }

    public Optional<InputCultivation> getById(Long idCultivo, Long idImpuesto){
        return inputCultivationRepository.getById(idCultivo, idImpuesto);
    }

    public List<InputCultivation> getInputCultivationsByCultivationId(Long idCultivo) {
        return inputCultivationRepository.findByCultivoIdCultivo(idCultivo);
    }

    @Transactional
    public InputCultivation updateInputCultivation(Long idCultivo, Long idInsumo, InputCultivation inputCultivation) {
        System.out.println("Entro a la actualizacion");
        InputCultivation exist = inputCultivationRepository.getById(idCultivo, idInsumo).orElseThrow();
        System.out.println("EL insumo cultivo existe");
        Input input = inputRepository.getById(exist.getInput().getInputId()).orElseThrow();

        input.setStock(input.getStock() + exist.getQuantity());
        input.setStock(input.getStock() - inputCultivation.getQuantity());
        //input.setTotalCost(input.getPriceUnit()*input.getPriceUnit());
        inputRepository.save(input);
        exist.setQuantity(inputCultivation.getQuantity());
        exist.setExtent(inputCultivation.getExtent());
        return inputCultivationRepository.save(exist);
    }

    public void deleteInputApplication(Long idCultivo, Long idInsumo) {
        inputCultivationRepository.deleteById(idCultivo, idCultivo);
    }

    @Transactional
    public void deleteByCultivationId(Long id) {
        inputCultivationRepository.deleteByCultivoIdCultivo(id);
    }

    @Transactional
    public void deleteByInputId(Long id) {
        inputCultivationRepository.deleteByInsumoIdInsumo(id);
    }
}
