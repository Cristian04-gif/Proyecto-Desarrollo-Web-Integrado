package biocampo.demo.Domain.Repository;

import java.util.List;
import java.util.Optional;

import biocampo.demo.Domain.Model.Employee;

public interface EmployeeRepository {

    List<Employee> getAll();
    Optional<Employee> getEmployee(Long employeeId);
    Employee save(Employee employee);
    void delete(Long employeeId);
    //Employee getByNames(String names);
    Optional<Employee> getByBusinessEmail(String email);
    void deleteByBusinessEmail(String email);
    List<Employee> getAvailable(boolean available);
    Optional<Employee> getByDni(String dni);
}
