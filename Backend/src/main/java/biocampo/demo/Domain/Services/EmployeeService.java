package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.DTO.AutController.AuthServices;
import biocampo.demo.Domain.DTO.AutController.RegisterRequest;
import biocampo.demo.Domain.Model.Employee;
import biocampo.demo.Domain.Model.JobPosition;
import biocampo.demo.Domain.Model.User;
import biocampo.demo.Domain.Repository.EmployeeRepository;
import biocampo.demo.Domain.Repository.JobPositionRepository;
import biocampo.demo.Persistance.Entity.Usuario;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JobPositionRepository jobPositionRepository;

    @Autowired
    private AuthServices authServices;

    @Autowired
    private UserService userService;

    public List<Employee> getAllEmployees() {
        return employeeRepository.getAll();
    }

    public Optional<Employee> getEmployeeById(Long employeeId) {
        return employeeRepository.getEmployee(employeeId);
    }

    public Optional<Employee> getEmployeeByBusinessEmail(String email) {
        return employeeRepository.getByBusinessEmail(email);
    }

    public Employee registerEmployee(Employee employee) {

        Optional<JobPosition> jobPosition = jobPositionRepository
                .getJobPosition(employee.getJobPosition().getPositionId());
        Employee emp;
        if (jobPosition.isPresent()) {
            JobPosition jp = jobPosition.get();
            employee.setJobPosition(jp);
            System.out.println("puesto: " + jp.getPositionName());
            emp = employeeRepository.save(employee);
            System.out.println("Empleado registrado");
            System.out.println("posicion del empleado: " + emp.getJobPosition().getPositionName());
            for (Usuario.Rol rol : Usuario.Rol.values()) {
                if (rol.toString().equalsIgnoreCase(emp.getJobPosition().getPositionName())) {
                    System.out.println("rol: " + rol.toString());

                    RegisterRequest registerRequest = RegisterRequest.builder()
                            .nombre(emp.getFirstName())
                            .apellido(emp.getLastName())
                            .email(emp.getWorkEmail())
                            .contraseña("123")
                            .pais(emp.getCountry()).build();
                    authServices.register(registerRequest);
                    System.out.println("Usuario registrado");
                    break;
                }
            }
        } else {
            throw new IllegalArgumentException("La posición de trabajo no existe");
        }

        return emp;
    }

    public Employee updateEmployee(Long id, Employee employee) {
        Optional<Employee> existingEmployee = employeeRepository.getEmployee(id);
        if (existingEmployee.isPresent()) {
            Employee toUpdate = existingEmployee.get();
            if (employee.getFirstName() != null)
                toUpdate.setFirstName(employee.getFirstName());
            if (employee.getLastName() != null)
                toUpdate.setLastName(employee.getLastName());
            if (employee.getAge() != 0)
                toUpdate.setAge(employee.getAge());
            if (employee.getPhone() != null)
                toUpdate.setPhone(employee.getPhone());
            if (employee.getPersonalEmail() != null)
                toUpdate.setPersonalEmail(employee.getPersonalEmail());
            if (employee.getWorkEmail() != null)
                toUpdate.setWorkEmail(employee.getWorkEmail());
            if (employee.getDni() != null)
                toUpdate.setDni(employee.getDni());
            if (employee.getCountry() != null)
                toUpdate.setCountry(employee.getCountry());
            if (employee.getAddress() != null)
                toUpdate.setAddress(employee.getAddress());

            if (employee.getJobPosition() != null && employee.getJobPosition().getPositionId() != null) {

                Optional<JobPosition> jobPosition = jobPositionRepository
                        .getJobPosition(employee.getJobPosition().getPositionId());

                if (jobPosition.isPresent()) {
                    toUpdate.setJobPosition(jobPosition.get());
                    Employee employeeUpdate = employeeRepository.save(toUpdate);

                    if (userService.getUserByEmail(employeeUpdate.getWorkEmail()).isPresent()) {
                        System.out.println("El usuario ya existe");

                        Optional<User> userFound = userService.getUserByEmail(employeeUpdate.getWorkEmail());
                        if (userFound.isPresent()) {
                            User user = userFound.get();
                            Long userId = user.getUserId();
                            System.out.println("ID USUARIO: " + userId);
                            userService.updateUser(userId, user);
                        }
                    } else {
                        for (Usuario.Rol rol : Usuario.Rol.values()) {

                            if (rol.toString().equalsIgnoreCase(jobPosition.get().getPositionName())) {
                                RegisterRequest registerRequest = RegisterRequest.builder()
                                        .nombre(employeeUpdate.getFirstName())
                                        .apellido(employeeUpdate.getLastName())
                                        .email(employeeUpdate.getWorkEmail())
                                        .contraseña("123")
                                        .pais(employeeUpdate.getCountry()).build();
                                authServices.register(registerRequest);
                                break;
                            }
                        }

                    }

                    for (Usuario.Rol rol : Usuario.Rol.values()) {
                        if (rol.toString().equalsIgnoreCase(toUpdate.getJobPosition().getPositionName())) {
                            System.out.println("rol: " + rol.toString());

                            break;
                        }
                    }
                }

                // return toUpdate;
            } else {
                throw new IllegalArgumentException("El empleado con ID " + id + " no existe");
            }
        }
        return employee;
    }

    public void deleteEmployee(Long id) {
        Optional<Employee> existingEmployee = employeeRepository.getEmployee(id);
        if (existingEmployee.isPresent()) {
            Employee employee = existingEmployee.get();
            userService.deleteByEmail(employee.getWorkEmail());
            employeeRepository.delete(id);
        }
    }
}
