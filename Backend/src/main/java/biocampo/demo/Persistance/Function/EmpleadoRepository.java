package biocampo.demo.Persistance.Function;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import biocampo.demo.Domain.Model.Employee;
import biocampo.demo.Domain.Repository.EmployeeRepository;
import biocampo.demo.Persistance.CRUD.RepoEmpleado;
import biocampo.demo.Persistance.Entity.Empleado;
import biocampo.demo.Persistance.Mappings.EmployeeMapper;

@Repository
public class EmpleadoRepository implements EmployeeRepository {

    @Autowired
    private RepoEmpleado repoEmpleado;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<Employee> getAll() {
        List<Empleado> empleados = repoEmpleado.findAll();
        return employeeMapper.toEmployees(empleados);
    }

    @Override
    public Optional<Employee> getEmployee(Long employeeId) {
        return repoEmpleado.findById(employeeId).map(empleado -> employeeMapper.toEmployee(empleado));
    }

    @Override
    public Employee save(Employee employee) {
        Empleado empleado = employeeMapper.toEmpleado(employee);
        Empleado empleadoGuardado = repoEmpleado.save(empleado);
        return employeeMapper.toEmployee(empleadoGuardado);
    }

    @Override
    public void delete(Long employeeId) {
        repoEmpleado.deleteById(employeeId);
    }

    @Override
    public Optional<Employee> getByBusinessEmail(String email) {
        Optional<Empleado> empleado = repoEmpleado.findByEmailEmpresarial(email);
        return empleado.map(e -> employeeMapper.toEmployee(e));
    }

    @Override
    public void deleteByBusinessEmail(String email) {
        Optional<Empleado> empleado = repoEmpleado.findByEmailEmpresarial(email);
        empleado.ifPresent(e -> repoEmpleado.delete(e));
    }

    @Override
    public List<Employee> getAvailable(boolean available) {
        List<Empleado> empleados = repoEmpleado.findByDisponible(available);
        return employeeMapper.toEmployees(empleados);
    }

    @Override
    public Optional<Employee> getByDni(String dni) {
        Optional<Empleado> empleado = repoEmpleado.findByDni(dni);
        return empleado.map(e -> employeeMapper.toEmployee(e));
    }

}
