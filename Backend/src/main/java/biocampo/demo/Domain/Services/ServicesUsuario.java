package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Repository.RepoUsuario;
import biocampo.demo.Persistance.Entity.Usuario;
import biocampo.demo.Persistance.Entity.Usuario.Rol;

@Service
public class ServicesUsuario {

    @Autowired
    private RepoUsuario repoUsuario;

    public List<Usuario> listarTodo() {
        return repoUsuario.findAll();
    }

    public Optional<Usuario> buscarUsuario(Long id) {
        return repoUsuario.findById(id);
    }

    public Usuario guardarUsuario(String nombre, String apellido, String email, String contra, String pais) {

        Usuario user = new Usuario();
        user.setNombre(nombre);
        user.setApellido(apellido);
        user.setEmail(email);
        user.setContraseña(contra);
        user.setPais(pais);

        String[] verificarCorreo = email.split("@");
        for (String i : verificarCorreo) {
            if (i != "utp.edu.pe") {
                user.setRol(Rol.CLIENTE);
            } else {
                user.setRol(Rol.ADMIN);
            }
        }
        return repoUsuario.save(user);
    }

    public Usuario actualizar(Long id, String nombre, String apellido, String email, String contra, String pais) {

        Optional<Usuario> existe = repoUsuario.findById(id);
        if (existe.isPresent()) {
            Usuario actualizar = existe.get();
            if (nombre != null)
                actualizar.setNombre(nombre);
            if (apellido != null)
                actualizar.setApellido(apellido);
            if (email != null)
                actualizar.setEmail(email);
            if (contra != null)
                actualizar.setContraseña(contra);
            if (pais != null)
                actualizar.setPais(pais);
            return repoUsuario.save(actualizar);
        } else {
            return null;
        }

    }

    public void eliminar(Long id){
        repoUsuario.deleteById(id);
    }
}
