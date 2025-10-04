package biocampo.demo.Persistance.Function;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Persistance.CRUD.RepoEmpleado;
import biocampo.demo.Persistance.CRUD.RepoPuestoEmpleado;
import biocampo.demo.Persistance.CRUD.RepoUsuario;
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

    public Usuario actualizar(Long id, Usuario usuario) {
        System.out.println("Actualzar usuario");
        Optional<Usuario> existe = repoUsuario.findById(id);
        if (existe.isPresent()) {
            System.out.println("Si existe el usuario");
            Usuario actualizar = existe.get();
            if (usuario.getNombre() != null)
                actualizar.setNombre(usuario.getNombre());
            if (usuario.getApellido() != null)
                actualizar.setApellido(usuario.getApellido());
            if (usuario.getEmail() != null) {
                if (usuario.getEmail().toLowerCase().endsWith("@utp.edu.pe")) {

                    Optional<Empleado> existeEmpleado = repoEmpleado.findByEmailEmpresarial(usuario.getEmail());

                    if (existeEmpleado.isPresent()) {
                        System.out.println("EL usuario es un empleado");
                        Empleado empleado = existeEmpleado.get();
                        Optional<PuestoEmpleado> existePuesto = repoPuestoEmpleado
                                .findById(empleado.getPuesto().getIdPuesto());
                        if (existePuesto.isPresent()) {
                            
                            PuestoEmpleado puesto = existePuesto.get();
                            System.out.println("Si existe el puesto: " + puesto.getNombrePuesto());
                            boolean roleSet = false;
                            for (Rol rol : Rol.values()) {
                                if (rol.toString().equalsIgnoreCase(puesto.getNombrePuesto())) {
                                    actualizar.setRol(rol);
                                    roleSet = true;
                                    break;
                                    
                                }
                            }
                            if (roleSet == false ) {
                                actualizar.setRol(Rol.CLIENTE);
                            }
                        }
                    }
                } else {
                    actualizar.setRol(Rol.CLIENTE);
                }
                actualizar.setEmail(usuario.getEmail());
            }
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