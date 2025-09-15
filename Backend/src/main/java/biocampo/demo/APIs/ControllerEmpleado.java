package biocampo.demo.APIs;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import biocampo.demo.Domain.Services.EmpleadoServices;
import biocampo.demo.Persistance.Entity.Empleado;
import biocampo.demo.Persistance.Entity.PuestoEmpleado;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/empleados")
public class ControllerEmpleado {

    @Autowired
    private EmpleadoServices empleadoServices;

    @GetMapping("/todos")
    public ResponseEntity<List<Empleado>> listarTodo() {
        List<Empleado> lista = empleadoServices.listarTodo();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Empleado> buscarEmpleado(@PathVariable Long id) {
        Optional<Empleado> existe = empleadoServices.buscarEmpleado(id);
        return existe.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/registrar")
    public ResponseEntity<Empleado> registrar(@RequestParam("nombres") String nombres,
            @RequestParam("apellidos") String apellidos, @RequestParam("edad") int edad,
            @RequestParam("telefono") String telefono, @RequestParam("emailPersonal") String emailPersonal,
            @RequestParam("dni") String dni, @RequestParam("pais") String pais,
            @RequestParam("direccion") String direccion, @RequestParam("puesto") PuestoEmpleado puesto,
            @RequestParam("salario") BigDecimal salario) {

        try {
            Empleado nuevo = empleadoServices.registrarEmpleado(nombres, apellidos, edad, telefono, emailPersonal, dni,
                    pais, direccion, puesto, salario);
            return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("actualizar/{id}")
    public ResponseEntity<Empleado> actualizar(@PathVariable Long id, @RequestParam("nombres") String nombres,
            @RequestParam("apellidos") String apellidos, @RequestParam("edad") int edad,
            @RequestParam("telefono") String telefono, @RequestParam("emailPersonal") String emailPersonal,
            @RequestParam("dni") String dni, @RequestParam("pais") String pais,
            @RequestParam("direccion") String direccion, @RequestParam("puesto") PuestoEmpleado puesto,
            @RequestParam("salario") BigDecimal salario) {

        try {
            Empleado empleado = empleadoServices.actualizarEmpleado(id, nombres, apellidos, edad, telefono,
                    emailPersonal, dni, pais, direccion, puesto, salario);
            return new ResponseEntity<>(empleado, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id){
        try {
            empleadoServices.eliminarEmpleado(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
