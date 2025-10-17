package biocampo.demo.Web.APIs;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import biocampo.demo.Domain.Model.InputSupplier;
import biocampo.demo.Domain.Services.InputSupplierService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/inputSuplier")
public class InputSupplierController {

    @Autowired
    private InputSupplierService inputSupplierService;

    @GetMapping("/all")
    public ResponseEntity<List<InputSupplier>> getAll() {
        List<InputSupplier> inputSuppliers = inputSupplierService.getAll();
        return new ResponseEntity<>(inputSuppliers, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<InputSupplier> getInputSupplier(@PathVariable Long id) {
        Optional<InputSupplier> optional = inputSupplierService.getInputSupplier(id);
        return optional.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/register")
    public ResponseEntity<InputSupplier> registerInputSupplier(@RequestBody InputSupplier inputSupplier) {
        try {
            InputSupplier inputSupplier2 = inputSupplierService.registerInputSupplier(inputSupplier);
            return new ResponseEntity<>(inputSupplier2, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<InputSupplier> updateInputSupplier(@PathVariable Long id, @RequestBody InputSupplier inputSupplier){
        try {
            InputSupplier inputSupplier2 = inputSupplierService.updataInputSupplier(id, inputSupplier);
            return new ResponseEntity<>(inputSupplier2, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            inputSupplierService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
