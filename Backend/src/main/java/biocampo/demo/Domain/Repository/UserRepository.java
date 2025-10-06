package biocampo.demo.Domain.Repository;

import java.util.List;
import java.util.Optional;

import biocampo.demo.Domain.Model.User;

public interface UserRepository {
    List<User> getAll();
    Optional<User> getUser(Long userId);
    User save(User user);
    void delete(Long userId);
    Optional<User> getByEmail(String email);
    void deleteByEmail(String email);
    
    //boolean existsByEmail(String email);
}
