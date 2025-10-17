package biocampo.demo.Web.APIs;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import biocampo.demo.Domain.Model.PlantCategory;
import biocampo.demo.Domain.Services.PlantCategoryService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/plantCategory")
public class PlantCategiryController {

    @Autowired
    private PlantCategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<List<PlantCategory>> getAll() {
        List<PlantCategory> all = categoryService.getAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PlantCategory> getById(Long id) {
        Optional<PlantCategory> exist = categoryService.getPlantCategory(id);

        return exist.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/register")
    public ResponseEntity<PlantCategory> register(@RequestBody PlantCategory category){
        try {
            PlantCategory registerCategory = categoryService.registerCategory(category);
            return new ResponseEntity<>(registerCategory, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    

    @PutMapping("update/{id}")
    public ResponseEntity<PlantCategory> update(@PathVariable Long id, @RequestBody PlantCategory category){
        try {
            PlantCategory register = categoryService.updateCategory(id, category);
            return new ResponseEntity<>(register, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
