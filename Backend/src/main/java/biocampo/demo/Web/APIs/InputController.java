package biocampo.demo.Web.APIs;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import biocampo.demo.Domain.Model.Input;
import biocampo.demo.Domain.Services.InputService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/input")
public class InputController {

    @Autowired
    private InputService inputService;

    @GetMapping("/all")
    public ResponseEntity<List<Input>> getAll() {
        List<Input> inputs = inputService.getAllInputs();
        return new ResponseEntity<>(inputs, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Input> getInput(@PathVariable Long id) {
        Optional<Input> optional = inputService.getInputById(id);
        return optional.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/register")
    public ResponseEntity<Input> registerInput(@RequestBody Input input){
        try {
            Input input2 = inputService.registerInput(input);
            return new ResponseEntity<>(input2, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Input> updateInput(@PathVariable Long id, @RequestBody Input input) {
        try {
            Input input2 = inputService.updateInput(id, input);
            return new ResponseEntity<>(input2, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteInput(@PathVariable Long id){
        try {
            inputService.deleteInput(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
