package biocampo.demo.Domain.Services;

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
import biocampo.demo.Persistance.Entity.Usuario;
import biocampo.demo.Persistance.Entity.Usuario.Rol;
import jakarta.transaction.Transactional;

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

    public Empleado registrarEmpleado(Empleado empleado) {
        Optional<PuestoEmpleado> existePuesto = repoPuestoEmpleado.findById(empleado.getPuesto().getIdPuesto());

        if (existePuesto.isPresent()) {
            Empleado empleadoGuardado = repoEmpleado.save(empleado);

            PuestoEmpleado cargoEmpleado = existePuesto.get();
            System.out.println(cargoEmpleado.getNombrePuesto());
            for (Rol rol : Rol.values()) {

                if (rol.toString().equalsIgnoreCase(cargoEmpleado.getNombrePuesto())) {
                    System.out.println("rol: " + rol.toString());

                    RegisterRequest registerRequest = RegisterRequest.builder()
                            .nombre(empleadoGuardado.getNombres())
                            .apellido(empleadoGuardado.getApellidos())
                            .email(empleadoGuardado.getEmailEmpresarial())
                            .contraseña("123")
                            .pais(empleadoGuardado.getPais()).build();
                    authServices.register(registerRequest);
                    break;
                }

            }
            return empleado;

        } else {
            return null;
        }

    }

    public Empleado actualizarEmpleado(Long id, Empleado empleado) {

        Optional<Empleado> existe = repoEmpleado.findById(id);
        System.out.println("Si existe el empleado");

        if (existe.isPresent()) {
            Empleado actualizar = existe.get();
            actualizar.setNombres(empleado.getNombres());
            actualizar.setApellidos(empleado.getApellidos());
            actualizar.setEdad(empleado.getEdad());
            actualizar.setTelefono(empleado.getTelefono());
            actualizar.setEmailPersonal(empleado.getEmailPersonal());
            actualizar.setDni(empleado.getDni());
            actualizar.setPais(empleado.getPais());
            actualizar.setDireccion(empleado.getDireccion());
            actualizar.setSalario(empleado.getSalario());

            Optional<PuestoEmpleado> existePuesto = repoPuestoEmpleado.findById(empleado.getPuesto().getIdPuesto());

            if (existePuesto.isPresent()) {
                PuestoEmpleado puesto = existePuesto.get();
                actualizar.setPuesto(puesto);

                Empleado empleadoActualizado = repoEmpleado.save(actualizar);

                if (servicesUsuario.buscarUsuarioEmail(empleadoActualizado.getEmailEmpresarial()).isPresent()) {
                    System.out.println("El usuario ya existe");
                    Optional<Usuario> usuarioEncontrado = servicesUsuario
                            .buscarUsuarioEmail(empleadoActualizado.getEmailEmpresarial());
                    if (usuarioEncontrado.isPresent()) {
                        Usuario usuario = usuarioEncontrado.get();
                        Long idUsuario = usuario.getIdUsuario();
                        System.out.println("ID USUARIO: " + idUsuario);
                        servicesUsuario.actualizar(idUsuario, usuario);

                    }

                } else {
                    for (Rol rol : Rol.values()) {

                        if (rol.toString().equalsIgnoreCase(puesto.getNombrePuesto())) {
                            RegisterRequest registerRequest = RegisterRequest.builder()
                                    .nombre(empleadoActualizado.getNombres())
                                    .apellido(empleadoActualizado.getApellidos())
                                    .email(empleadoActualizado.getEmailEmpresarial())
                                    .contraseña("123")
                                    .pais(empleadoActualizado.getPais()).build();
                            authServices.register(registerRequest);
                            break;
                        }
                    }
                }

            }

            return actualizar;
        } else {
            return null;
        }
    }

    @Transactional
    public void eliminarEmpleado(Long id) {
        Optional<Empleado> existe = repoEmpleado.findById(id);
        if (existe.isPresent()) {
            Empleado empleado = existe.get();
            servicesUsuario.eliminarUsuarioEmpleado(empleado.getEmailEmpresarial());
            repoEmpleado.deleteById(id);
        }
        
    }
}
