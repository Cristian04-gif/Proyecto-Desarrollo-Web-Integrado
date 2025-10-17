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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


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

    @GetMapping("/id/{id}")
    public ResponseEntity<InputCultivation> getInputCultivation(@PathVariable Long id) {
        Optional<InputCultivation> optional = inputCultivationService.getInputApplicationById(id);
        return optional.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.OK));
    }

    @PostMapping("/register")
    public ResponseEntity<InputCultivation> registerInputCultivation(@RequestBody InputCultivation inputCultivation) {
        try {
            InputCultivation cultivation = inputCultivationService.registerInputApplication(inputCultivation);
            return new ResponseEntity<>(cultivation, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<InputCultivation> updateInputCultivation(@PathVariable Long id, @RequestBody InputCultivation inputCultivation){
        try {
            InputCultivation cultivation = inputCultivationService.updateInputApplication(id, inputCultivation);
            return new ResponseEntity<>(cultivation, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            inputCultivationService.deleteInputApplication(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
