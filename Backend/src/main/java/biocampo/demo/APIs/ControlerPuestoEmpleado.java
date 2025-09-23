package biocampo.demo.APIs;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import biocampo.demo.Domain.Services.PuestoEmpleadoServices;
import biocampo.demo.Persistance.Entity.PuestoEmpleado;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/puesto")
public class ControlerPuestoEmpleado {

    @Autowired
    private PuestoEmpleadoServices puestoEmpleadoServices;

    @GetMapping("/todos")
    public ResponseEntity<List<PuestoEmpleado>> listarTodo() {
        List<PuestoEmpleado> lista = puestoEmpleadoServices.listarTodo();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Optional<PuestoEmpleado>> buscar(@PathVariable Long id) {
        Optional<PuestoEmpleado> existe = puestoEmpleadoServices.buscar(id);
        
        return existe.map(value -> new ResponseEntity<>(existe, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/registrar")
    public ResponseEntity<PuestoEmpleado> registrar(@RequestBody PuestoEmpleado puesto) {
        try {
            PuestoEmpleado nuevo = puestoEmpleadoServices.registrar(puesto);
            return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<PuestoEmpleado> actualizar(@PathVariable Long id, @RequestBody PuestoEmpleado puesto) {
        try {
            PuestoEmpleado actualizar = puestoEmpleadoServices.actualizar(id, puesto);
            return new ResponseEntity<>(actualizar, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        try {
        puestoEmpleadoServices.eliminar(id);
        return ResponseEntity.noContent().build();            
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
