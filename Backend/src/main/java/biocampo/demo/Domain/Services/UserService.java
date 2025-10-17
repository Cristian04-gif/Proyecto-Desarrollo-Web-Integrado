package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.User;
import biocampo.demo.Domain.Repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.getUser(userId);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    public void deleteUser(Long userId) {
        userRepository.delete(userId);
    }

    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }
}
