package biocampo.demo.Web.APIs;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import biocampo.demo.Domain.Model.InputCultivation;
import biocampo.demo.Domain.Services.InputCultivationService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/inputCultivation")
public class InputCultivationController {

    @Autowired
    private InputCultivationService inputCultivationService;

    @GetMapping("/all")
    public ResponseEntity<List<InputCultivation>> getAll() {
        List<InputCultivation> cultivations = inputCultivationService.getAllInputApplications();
        return new ResponseEntity<>(cultivations, HttpStatus.OK);
    }

    @GetMapping("/id/{idCultivo}/{idInsumo}")
    public ResponseEntity<InputCultivation> getByID(@PathVariable Long idCultivo, @PathVariable Long idInsumo) {
        Optional<InputCultivation> exist = inputCultivationService.getById(idCultivo, idInsumo);
        return exist.map(values -> new ResponseEntity<>(values, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/cultivation/{id}")
    public ResponseEntity<List<InputCultivation>> getAllByCutivation(@PathVariable Long id) {
        List<InputCultivation> inputCultivations = inputCultivationService.getInputCultivationsByCultivationId(id);
        return new ResponseEntity<>(inputCultivations, HttpStatus.OK);
    }

    @PutMapping("/update/{idCultivo}/{idInsumo}")
    public ResponseEntity<InputCultivation> updateInputCultivation(@PathVariable Long idCultivo, @PathVariable Long idInsumo, @RequestBody InputCultivation inputCultivation){
        try {
            InputCultivation cultivation = inputCultivationService.updateInputCultivation(idCultivo, idInsumo, inputCultivation);
            return new ResponseEntity<>(cultivation, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/delete/{idCultivo}/{idInsumo}")
    public ResponseEntity<Void> delete(@PathVariable Long idCultivo, @PathVariable Long idInsumo) {
        try {
            inputCultivationService.deleteInputApplication(idCultivo, idInsumo);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
