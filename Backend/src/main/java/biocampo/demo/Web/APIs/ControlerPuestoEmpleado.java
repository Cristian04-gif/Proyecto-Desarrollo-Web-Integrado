package biocampo.demo.Web.APIs;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import biocampo.demo.Domain.Model.JobPosition;
import biocampo.demo.Domain.Services.JobPositionService;
import biocampo.demo.Persistance.Entity.PuestoEmpleado;
import biocampo.demo.Persistance.Function.PuestoEmpleadoServices;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/puesto")
public class ControlerPuestoEmpleado {

    @Autowired
    private JobPositionService jobPositionService;

    @GetMapping("/all")
    public ResponseEntity<List<JobPosition>> getAllJobPositions() {
        List<JobPosition> lista = jobPositionService.getAllJobPositions();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JobPosition> getJobPositionById(@PathVariable Long id) {
        Optional<JobPosition> existe = jobPositionService.getJobPositionById(id);

        return existe.map(value -> new ResponseEntity<>(existe.get(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/register")
    public ResponseEntity<JobPosition> createJobPosition(@RequestBody JobPosition jobPosition) {
        try {
            JobPosition nuevo = jobPositionService.createJobPosition(jobPosition);
            return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    

    @PutMapping("update/{id}")
    public ResponseEntity<JobPosition> updateJobPosition(@PathVariable Long id, @RequestBody JobPosition jobPosition) {
        try {
            JobPosition actualizar = jobPositionService.updateJobPosition(id, jobPosition);
            return new ResponseEntity<>(actualizar, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteJobPosition(@PathVariable Long id) {
        try {
            jobPositionService.deleteJobPosition(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    /*
     * @Autowired
     * private PuestoEmpleadoServices puestoEmpleadoServices;
     * 
     * @GetMapping("/todos")
     * public ResponseEntity<List<PuestoEmpleado>> listarTodo() {
     * List<PuestoEmpleado> lista = puestoEmpleadoServices.listarTodo();
     * return new ResponseEntity<>(lista, HttpStatus.OK);
     * }
     * 
     * @GetMapping("/buscar/{id}")
     * public ResponseEntity<Optional<PuestoEmpleado>> buscar(@PathVariable Long id)
     * {
     * Optional<PuestoEmpleado> existe = puestoEmpleadoServices.buscar(id);
     * 
     * return existe.map(value -> new ResponseEntity<>(existe, HttpStatus.OK))
     * .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
     * }
     * 
     * @PostMapping("/registrar")
     * public ResponseEntity<PuestoEmpleado> registrar(@RequestBody PuestoEmpleado
     * puesto) {
     * try {
     * PuestoEmpleado nuevo = puestoEmpleadoServices.registrar(puesto);
     * return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
     * } catch (Exception e) {
     * return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
     * }
     * }
     * 
     * @PutMapping("/actualizar/{id}")
     * public ResponseEntity<PuestoEmpleado> actualizar(@PathVariable Long
     * id, @RequestBody PuestoEmpleado puesto) {
     * try {
     * PuestoEmpleado actualizar = puestoEmpleadoServices.actualizar(id, puesto);
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
     * puestoEmpleadoServices.eliminar(id);
     * return ResponseEntity.noContent().build();
     * } catch (EntityNotFoundException e) {
     * return ResponseEntity.notFound().build();
     * }
     * }
     */
}
