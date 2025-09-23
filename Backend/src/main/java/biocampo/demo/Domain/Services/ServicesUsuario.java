package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Repository.RepoEmpleado;
import biocampo.demo.Domain.Repository.RepoPuestoEmpleado;
import biocampo.demo.Domain.Repository.RepoUsuario;
import biocampo.demo.Persistance.Entity.Empleado;
import biocampo.demo.Persistance.Entity.PuestoEmpleado;
import biocampo.demo.Persistance.Entity.Usuario;
import biocampo.demo.Persistance.Entity.Usuario.Rol;

@Service
public class ServicesUsuario {

    @Autowired
    private RepoUsuario repoUsuario;

    @Autowired
    private RepoEmpleado repoEmpleado;

    @Autowired
    private RepoPuestoEmpleado repoPuestoEmpleado;

    public List<Usuario> listarTodo() {
        return repoUsuario.findAll();
    }

    public Optional<Usuario> buscarUsuario(Long id) {
        return repoUsuario.findById(id);
    }
    public Optional<Usuario> buscarUsuarioEmail(String email) {
        return repoUsuario.findByEmail(email);
    }

    public Usuario registrarUsuario(Usuario usuario) {

        if (usuario.getEmail().toLowerCase().endsWith("@utp.edu.pe")) {

            Optional<Empleado> existe = repoEmpleado.findByEmailEmpresarial(usuario.getEmail());

            if (existe.isPresent()) {
                Empleado empleado = existe.get();

                Optional<PuestoEmpleado> exstePuesto = repoPuestoEmpleado.findById(empleado.getPuesto().getIdPuesto());
                if (exstePuesto.isPresent()) {
                    PuestoEmpleado cargoEmpleado = exstePuesto.get();
                    for (Rol rol : Rol.values()) {
                        if (rol.toString().equalsIgnoreCase(cargoEmpleado.getNombrePuesto())) {
                            usuario.setRol(rol);
                            break;
                        }
                    }
                }
                /*
                 * String cargo = empleado.getPuesto().getNombrePuesto().toUpperCase();
                 * 
                 * for (Rol rol : Rol.values()) {
                 * if (rol.toString().equalsIgnoreCase(cargo)) {
                 * usuario.setRol(rol);
                 * break;
                 * }
                 * }
                 */

            } else {
                System.out.println("NO SE ENCONTRO EL CORREO");
            }
        } else {
            usuario.setRol(Rol.CLIENTE);
        }
        return repoUsuario.save(usuario);
    }
    /*
     * public Usuario registrarUsuario(String nombre, String apellido, String email,
     * String contra, String pais) {
     * 
     * Usuario user = new Usuario();
     * user.setNombre(nombre);
     * user.setApellido(apellido);
     * user.setEmail(email);
     * user.setContraseña(contra);
     * user.setPais(pais);
     * 
     * if (email.toLowerCase().endsWith("@utp.edu.pe")) {
     * 
     * Optional<Empleado> existe = repoEmpleado.findByEmailEmpresarial(email);
     * 
     * if (existe.isPresent()) {
     * Empleado empleado = existe.get();
     * String cargo = empleado.getPuesto().getNombrePuesto().toUpperCase();
     * for (Rol rol : Rol.values()) {
     * if (rol.toString().equalsIgnoreCase(cargo)) {
     * user.setRol(rol);
     * break;
     * }
     * }
     * 
     * }else{
     * System.out.println("NO SE ENCONTRO EL CORREO");
     * }
     * } else {
     * user.setRol(Rol.CLIENTE);
     * }
     * return repoUsuario.save(user);
     * }
     */

    /*
     * public Usuario actualizar(Long id, String nombre, String apellido, String
     * email, String contra, String pais) {
     * 
     * Optional<Usuario> existe = repoUsuario.findById(id);
     * 
     * if (existe.isPresent()) {
     * 
     * Usuario actualizar = existe.get();
     * if (nombre != null)
     * actualizar.setNombre(nombre);
     * if (apellido != null)
     * actualizar.setApellido(apellido);
     * if (email != null)
     * 
     * if (email.toLowerCase().endsWith("@utp.edu.pe")) {
     * 
     * Optional<Empleado> existeEmpleado =
     * repoEmpleado.findByEmailEmpresarial(email);
     * if (existe.isPresent()) {
     * 
     * Empleado empleado = existeEmpleado.get();
     * String cargo = empleado.getPuesto().getNombrePuesto().toUpperCase();
     * for (Rol rol : Rol.values()) {
     * if (rol.toString().equalsIgnoreCase(cargo)) {
     * actualizar.setRol(rol);
     * break;
     * }
     * }
     * }
     * } else {
     * actualizar.setRol(Rol.CLIENTE);
     * }
     * actualizar.setEmail(email);
     * if (contra != null)
     * actualizar.setContraseña(contra);
     * if (pais != null)
     * actualizar.setPais(pais);
     * 
     * return repoUsuario.save(actualizar);
     * } else {
     * return null;
     * }
     * 
     * }
     */
    public Usuario actualizar(Long id, Usuario usuario) {

        Optional<Usuario> existe = repoUsuario.findById(id);
        if (existe.isPresent()) {

            Usuario actualizar = existe.get();
            if (usuario.getNombre() != null)
                actualizar.setNombre(usuario.getNombre());
            if (usuario.getApellido() != null)
                actualizar.setApellido(usuario.getApellido());
            if (usuario.getEmail() != null)

                if (usuario.getEmail().toLowerCase().endsWith("@utp.edu.pe")) {

                    Optional<Empleado> existeEmpleado = repoEmpleado.findByEmailEmpresarial(usuario.getEmail());
                    if (existe.isPresent()) {

                        Empleado empleado = existeEmpleado.get();
                        String cargo = empleado.getPuesto().getNombrePuesto().toUpperCase();
                        for (Rol rol : Rol.values()) {
                            if (rol.toString().equalsIgnoreCase(cargo)) {
                                actualizar.setRol(rol);
                                break;
                            }
                        }
                    }
                } else {
                    actualizar.setRol(Rol.CLIENTE);
                }
            actualizar.setEmail(usuario.getEmail());
            if (usuario.getContraseña() != null)
                actualizar.setContraseña(usuario.getContraseña());
            if (usuario.getPais() != null)
                actualizar.setPais(usuario.getPais());

            return repoUsuario.save(actualizar);
        } else {
            return null;
        }
    }

    public void eliminar(Long id) {
        repoUsuario.deleteById(id);
    }

    public void eliminarUsuarioEmpleado(String email) {
        repoUsuario.deleteByEmail(email);
    }
}