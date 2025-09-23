package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Repository.RepoEmpleado;
import biocampo.demo.Domain.Repository.RepoPuestoEmpleado;
import biocampo.demo.Persistance.Entity.Empleado;
import biocampo.demo.Persistance.Entity.PuestoEmpleado;
import biocampo.demo.Persistance.Entity.Usuario;
import biocampo.demo.Persistance.Entity.Usuario.Rol;

@Service
public class EmpleadoServices {

    @Autowired
    private RepoEmpleado repoEmpleado;

    @Autowired
    private RepoPuestoEmpleado repoPuestoEmpleado;

    @Autowired
    private ServicesUsuario servicesUsuario;

    public List<Empleado> listarTodo() {
        return repoEmpleado.findAll();
    }

    public Optional<Empleado> buscarEmpleado(Long id) {
        return repoEmpleado.findById(id);
    }

    public Empleado registrarEmpleado(Empleado empleado) {
        Optional<PuestoEmpleado> existePuesto = repoPuestoEmpleado.findById(empleado.getPuesto().getIdPuesto());

        if (existePuesto.isPresent()) {

            repoEmpleado.save(empleado);

            PuestoEmpleado cargoEmpleado = existePuesto.get();
            for (Rol rol : Rol.values()) {

                if (rol.toString().equalsIgnoreCase(cargoEmpleado.getNombrePuesto())) {
                    Usuario user = Usuario.builder()
                            .nombre(empleado.getNombres())
                            .apellido(empleado.getApellidos())
                            .email(empleado.getEmailEmpresarial())
                            .contraseña("123")
                            .pais(empleado.getPais())
                            .rol(rol)
                            .build();
                    servicesUsuario.registrarUsuario(user);
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
                } else {
                    for (Rol rol : Rol.values()) {

                        if (rol.toString().equalsIgnoreCase(puesto.getNombrePuesto())) {
                            Usuario user = Usuario.builder()
                                    .nombre(empleado.getNombres())
                                    .apellido(empleado.getApellidos())
                                    .email(empleado.getEmailEmpresarial())
                                    .contraseña("123")
                                    .pais(empleado.getPais())
                                    .rol(rol)
                                    .build();
                            servicesUsuario.registrarUsuario(user);
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

    public void eliminarEmpleado(Long id) {
        repoEmpleado.deleteById(id);
        servicesUsuario.eliminar(id);
    }
}
