package biocampo.demo.APIs;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import biocampo.demo.Domain.Services.ServicesPlanta;
import biocampo.demo.Persistance.Entity.CategoriaPlanta;
import biocampo.demo.Persistance.Entity.Planta;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/planta")
public class ControllerPlanta {

    @Autowired
    private ServicesPlanta servicesPlanta;

    @GetMapping("/todos")
    public ResponseEntity<List<Planta>> listarTodo() {
        List<Planta> lista = servicesPlanta.listarTodo();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Planta> buscarPlanta(@PathVariable Long id) {
        Optional<Planta> existe = servicesPlanta.buscarPlanta(id);
        if (existe.isPresent()) {
            Planta planta = existe.get();
            return new ResponseEntity<>(planta, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<Planta> registarPlanta(@RequestParam("nombre") String nombre,
            @RequestParam("stock") int stock, @RequestParam("categoria") CategoriaPlanta categoria) {

        try {
            Planta nueva = servicesPlanta.registrar(nombre, stock, categoria);
            return new ResponseEntity<>(nueva, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Planta> actualizarPlanta(@PathVariable Long id, @RequestParam("nombre") String nombre,
            @RequestParam("stock") int stock, @RequestParam("categoria") CategoriaPlanta categoria) {

        try {
            Planta actualizar = servicesPlanta.actualizarPlanta(id, nombre, stock, categoria);
            return ResponseEntity.ok(actualizar);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        try {
            servicesPlanta.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
