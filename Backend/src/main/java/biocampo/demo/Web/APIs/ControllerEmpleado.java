package biocampo.demo.Web.APIs;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import biocampo.demo.Domain.Model.Employee;
import biocampo.demo.Domain.Services.EmployeeService;
import biocampo.demo.Persistance.Entity.Empleado;
import biocampo.demo.Persistance.Function.EmpleadoServices;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/empleados")
public class ControllerEmpleado {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return employee.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Employee> getEmployeeByBusinessEmail(@PathVariable String email) {
        Optional<Employee> employee = employeeService.getEmployeeByBusinessEmail(email);
        return employee.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/register")
    public ResponseEntity<Employee> registerEmployee(@RequestBody Employee employee) {
        String workEmail = "E" + employee.getDni() + "@utp.edu.pe";
        employee.setWorkEmail(workEmail);
        try {
            Employee newEmployee = employeeService.registerEmployee(employee);
            return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, employee);
            return new ResponseEntity<>(updatedEmployee, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    /*
     * @Autowired
     * private EmpleadoServices empleadoServices;
     * 
     * @GetMapping("/todos")
     * public ResponseEntity<List<Empleado>> listarTodo() {
     * List<Empleado> lista = empleadoServices.listarTodo();
     * return new ResponseEntity<>(lista, HttpStatus.OK);
     * }
     * 
     * @GetMapping("/buscar/{id}")
     * public ResponseEntity<Empleado> buscarEmpleado(@PathVariable Long id) {
     * Optional<Empleado> existe = empleadoServices.buscarEmpleado(id);
     * return existe.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
     * .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
     * }
     * 
     * 
     * @PostMapping("/registrar")
     * public ResponseEntity<Empleado> registrar(@RequestBody Empleado empleado) {
     * String correoEmpresarial = "E" + empleado.getDni() + "@utp.edu.pe";
     * empleado.setEmailEmpresarial(correoEmpresarial);
     * try {
     * System.out.println(empleado.getPuesto().getNombrePuesto());
     * System.out.println(empleado.getEmailEmpresarial());
     * Empleado nuevo = empleadoServices.registrarEmpleado(empleado);
     * 
     * return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
     * } catch (Exception e) {
     * return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
     * }
     * }
     * 
     * 
     * @PutMapping("/actualizar/{id}")
     * public ResponseEntity<Empleado> actualizar(@PathVariable Long
     * id, @RequestBody Empleado empleado) {
     * try {
     * Empleado actualizar = empleadoServices.actualizarEmpleado(id, empleado);
     * return new ResponseEntity<>(actualizar, HttpStatus.ACCEPTED);
     * } catch (Exception e) {
     * return new ResponseEntity<>(HttpStatus.CONFLICT);
     * }
     * }
     * 
     * @DeleteMapping("/eliminar/{id}")
     * public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id){
     * try {
     * empleadoServices.eliminarEmpleado(id);
     * return ResponseEntity.noContent().build();
     * } catch (EntityNotFoundException e) {
     * return ResponseEntity.notFound().build();
     * }
     * }
     */
}
