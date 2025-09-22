package biocampo.demo.Domain.Services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.DTO.AutController.AuthServices;
import biocampo.demo.Domain.DTO.AutController.RegisterRequest;
import biocampo.demo.Domain.Repository.RepoEmpleado;
import biocampo.demo.Domain.Repository.RepoPuestoEmpleado;
import biocampo.demo.Persistance.Entity.Empleado;
import biocampo.demo.Persistance.Entity.PuestoEmpleado;
import biocampo.demo.Persistance.Entity.Usuario.Rol;

@Service
public class EmpleadoServices {

    @Autowired
    private RepoEmpleado repoEmpleado;

    @Autowired
    private RepoPuestoEmpleado repoPuestoEmpleado;

    @Autowired
    private ServicesUsuario servicesUsuario;

    @Autowired
    private AuthServices authServices;

    public List<Empleado> listarTodo() {
        return repoEmpleado.findAll();
    }

    public Optional<Empleado> buscarEmpleado(Long id) {
        return repoEmpleado.findById(id);
    }

    public Empleado registrarEmpleado(String nombres, String apellidos, int edad, String telefono,
            String emailPersonal, String dni, String pais, String direccion, PuestoEmpleado puesto,
            BigDecimal salario) {

        String correoEmpresarial = "E" + dni + "@utp.edu.pe";

        Optional<PuestoEmpleado> existePuesto = repoPuestoEmpleado.findById(puesto.getIdPuesto());

        if (existePuesto.isPresent()) {

            Empleado nuevo = Empleado.builder()
                    .nombres(nombres)
                    .apellidos(apellidos)
                    .edad(edad)
                    .telefono(telefono)
                    .emailPersonal(emailPersonal)
                    .emailEmpresarial(correoEmpresarial)
                    .dni(dni)
                    .pais(pais)
                    .direccion(direccion)
                    .puesto(puesto)
                    .salario(salario)
                    .build();

            repoEmpleado.save(nuevo);

            String cargo = nuevo.getPuesto().getNombrePuesto().toUpperCase();
            for (Rol rol : Rol.values()) {

                if (rol.toString().equalsIgnoreCase(cargo)) {
                    System.out.println("rol: " + rol.toString());
                    // servicesUsuario.registrarUsuario(nombres, apellidos, correoEmpresarial,
                    // "123", pais);
                    RegisterRequest registerRequest = RegisterRequest.builder()
                            .nombre(nombres)
                            .apellido(apellidos)
                            .email(correoEmpresarial)
                            .contraseña("123")
                            .pais(pais).build();
                    authServices.register(registerRequest);
                    break;
                }

            }
            return nuevo;

        } else {
            return null;
        }

    }

    public Empleado actualizarEmpleado(Long id, String nombres, String apellidos, int edad, String telefono,
            String emailPersonal, String dni, String pais, String direccion, PuestoEmpleado puesto,
            BigDecimal salario) {

        Optional<Empleado> existe = repoEmpleado.findById(id);

        if (existe.isPresent()) {
            Empleado actualizar = existe.get();
            actualizar.setNombres(nombres);
            actualizar.setApellidos(apellidos);
            actualizar.setEdad(edad);
            actualizar.setTelefono(telefono);
            actualizar.setEmailPersonal(emailPersonal);
            actualizar.setDni(dni);
            actualizar.setPais(pais);
            actualizar.setDireccion(direccion);
            actualizar.setSalario(salario);

            Optional<PuestoEmpleado> existePuesto = repoPuestoEmpleado.findById(puesto.getIdPuesto());
            if (existePuesto.isPresent()) {

                actualizar.setPuesto(puesto);
                repoEmpleado.save(actualizar);

                String cargo = actualizar.getPuesto().getNombrePuesto().toUpperCase();
                for (Rol rol : Rol.values()) {
                    if (rol.toString().equalsIgnoreCase(cargo)) {
                        servicesUsuario.registrarUsuario(nombres, apellidos, actualizar.getEmailEmpresarial(), "321",
                                pais);
                        break;
                    } else {
                        servicesUsuario.eliminar(actualizar.getIdEmpleado());
                    }
                }
            }

            return actualizar;
        } else {
            return null;
        }
    }

    public void eliminarEmpleado(Long id) {
        repoEmpleado.deleteById(id);
        servicesUsuario.eliminar(id);
    }
}
