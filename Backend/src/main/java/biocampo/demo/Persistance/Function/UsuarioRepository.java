package biocampo.demo.Persistance.Function;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import biocampo.demo.Domain.Model.User;
import biocampo.demo.Domain.Repository.UserRepository;
import biocampo.demo.Persistance.CRUD.RepoUsuario;
import biocampo.demo.Persistance.Entity.Usuario;
import biocampo.demo.Persistance.Mappings.UserMapper;

@Repository
public class UsuarioRepository implements UserRepository {

    @Autowired
    private RepoUsuario repoUsuario;

    @Autowired
    private UserMapper mapper;

    @Override
    public List<User> getAll() {
        List<Usuario> usuarios = repoUsuario.findAll();
        return mapper.toUsers(usuarios);
    }

    @Override
    public Optional<User> getUser(Long userId) {
        
        return repoUsuario.findById(userId).map(usuario -> mapper.toUser(usuario));
    }

    @Override
    public User save(User user) {
        Usuario usuario = mapper.toUsuario(user);
        Usuario usuarioGuardado = repoUsuario.save(usuario);
        return mapper.toUser(usuarioGuardado);
    }

    @Override
    public void delete(Long userId) {
        repoUsuario.deleteById(userId);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return repoUsuario.findByEmail(email).map(usuario -> mapper.toUser(usuario));
    }

    @Override
    public void deleteByEmail(String email) {
        Optional<Usuario> usuario = repoUsuario.findByEmail(email);
        usuario.ifPresent(u -> repoUsuario.delete(u));
    }

}
