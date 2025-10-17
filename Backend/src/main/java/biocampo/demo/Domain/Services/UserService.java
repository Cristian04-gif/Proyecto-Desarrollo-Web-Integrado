package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.Employee;
import biocampo.demo.Domain.Model.JobPosition;
import biocampo.demo.Domain.Model.User;
import biocampo.demo.Domain.Repository.EmployeeRepository;
import biocampo.demo.Domain.Repository.JobPositionRepository;
import biocampo.demo.Domain.Repository.UserRepository;
import biocampo.demo.Persistance.Entity.Usuario;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JobPositionRepository jobPositionRepository;

    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.getUser(userId);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    /*public User updateUser(Long id, User user) {
        Optional<User> existingUser = userRepository.getUser(id);
        if (existingUser.isPresent()) {
            User toUpdate = existingUser.get();
            if (user.getFirstName() != null)
                toUpdate.setFirstName(user.getFirstName());
            if (user.getLastName() != null)
                toUpdate.setLastName(user.getLastName());

            if (user.getEmail() != null && toUpdate.getEmail() != user.getEmail()) {

                System.out.println("email: " + user.getEmail());

                if (user.getEmail().endsWith("@utp.edu.pe")) {
                    Optional<Employee> employee = employeeRepository.getByBusinessEmail(user.getEmail());
                    if (employee.isPresent()) {
                        Employee emp = employee.get();
                        System.out.println("El usuario es un empleado");
                        Optional<JobPosition> jobPosition = jobPositionRepository.getJobPosition(emp.getJobPosition().getPositionId());

                        if (jobPosition.isPresent()) {
                            JobPosition jp = jobPosition.get();
                            System.out.println("puesto del empleado: " + jp.getPositionName());
                            boolean existRol = false;

                            for (Usuario.Rol r : Usuario.Rol.values()) {
                                if (r.name().equalsIgnoreCase(jp.getPositionName())) {
                                    toUpdate.setRole(r.name());
                                    existRol = true;
                                    break;
                                }
                            }
                            if (!existRol) {
                                toUpdate.setRole(Usuario.Rol.CLIENTE.name());
                            }
                        } else {
                            toUpdate.setRole(Usuario.Rol.CLIENTE.name());
                        }
                    } else {
                        toUpdate.setRole(Usuario.Rol.CLIENTE.name());
                    }
                } else {

                }
                toUpdate.setEmail(user.getEmail());
            }
            if (user.getPassword() != null && toUpdate.getPassword() != user.getPassword())
                toUpdate.setPassword(user.getPassword());
            if (user.getCountry() != null) {
                toUpdate.setCountry(user.getCountry());
            }
            return userRepository.save(toUpdate);
        } else {
            return null;
        }
    }*/

    public void deleteUser(Long userId) {
        userRepository.delete(userId);
    }

    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }
}
