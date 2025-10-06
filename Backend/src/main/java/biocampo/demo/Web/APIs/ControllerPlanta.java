package biocampo.demo.Web.APIs;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import biocampo.demo.Domain.Model.Plant;
import biocampo.demo.Domain.Services.PlantService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/planta")
public class ControllerPlanta {

    @Autowired
    private PlantService plantService;

    @GetMapping("/all")
    public ResponseEntity<List<Plant>> getAll() {
        List<Plant> all = plantService.getAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Plant> getPlant(@PathVariable Long id) {
        Optional<Plant> exist = plantService.getPlant(id);
        return exist.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/register")
    public ResponseEntity<Plant> register(@RequestBody Plant plant){
        try {
            Plant regist = plantService.registerPlant(plant);
            return new ResponseEntity<>(regist, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }   
    
    @PutMapping("update/{id}")
    public ResponseEntity<Plant> update(@PathVariable Long id, @RequestBody Plant plant){
        try {
            Plant update = plantService.updatePlant(id, plant);
            return new ResponseEntity<>(update, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            plantService.deletePlant(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /*
     * @Autowired
     * private ServicesPlanta servicesPlanta;
     * 
     * @GetMapping("/todos")
     * public ResponseEntity<List<Planta>> listarTodo() {
     * List<Planta> lista = servicesPlanta.listarTodo();
     * return new ResponseEntity<>(lista, HttpStatus.OK);
     * }
     * 
     * @GetMapping("/buscar/{id}")
     * public ResponseEntity<Planta> buscarPlanta(@PathVariable Long id) {
     * Optional<Planta> existe = servicesPlanta.buscarPlanta(id);
     * if (existe.isPresent()) {
     * Planta planta = existe.get();
     * return new ResponseEntity<>(planta, HttpStatus.OK);
     * } else {
     * return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     * }
     * }
     * 
     * @PostMapping("/registrar")
     * public ResponseEntity<Planta> registarPlanta(@RequestBody Planta planta) {
     * try {
     * Planta nueva = servicesPlanta.registrar(planta);
     * return new ResponseEntity<>(nueva, HttpStatus.CREATED);
     * } catch (Exception e) {
     * return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
     * }
     * }
     * 
     * 
     * 
     * @PutMapping("/actualizar/{id}")
     * public ResponseEntity<Planta> actualizarPlanta(@PathVariable Long
     * id, @RequestBody Planta planta) {
     * 
     * try {
     * Planta actualizar = servicesPlanta.actualizarPlanta(id, planta);
     * return new ResponseEntity<>(actualizar, HttpStatus.ACCEPTED);
     * } catch (Exception e) {
     * return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     * }
     * }
     * 
     * 
     * @DeleteMapping("/eliminar/{id}")
     * public ResponseEntity<Void> eliminar(@PathVariable Long id){
     * try {
     * servicesPlanta.eliminar(id);
     * return ResponseEntity.noContent().build();
     * } catch (EntityNotFoundException e) {
     * return ResponseEntity.notFound().build();
     * }
     * }
     */
}
