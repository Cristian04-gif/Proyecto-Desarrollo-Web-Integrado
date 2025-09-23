package biocampo.demo.Domain.DTO.AutController;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.DTO.JWT.JwtServices;
import biocampo.demo.Domain.Repository.RepoEmpleado;
import biocampo.demo.Domain.Repository.RepoUsuario;
import biocampo.demo.Persistance.Entity.Empleado;
import biocampo.demo.Persistance.Entity.Usuario;
import biocampo.demo.Persistance.Entity.Usuario.Rol;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServices {

    @Autowired
    private RepoEmpleado repoEmpleado;

    @Autowired
    private RepoUsuario repoUsuario;

    private final PasswordEncoder passwordEncoder;
    private final JwtServices jwtServices;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails user = repoUsuario.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        String token = jwtServices.getToken(user);
        return AuthResponse.builder().token(token).build();
    }

    public AuthResponse register(RegisterRequest request) {

        Usuario user = new Usuario();
        user.setNombre(request.getNombre());
        user.setApellido(request.getApellido());
        user.setEmail(request.getEmail());
        user.setContraseña(passwordEncoder.encode(request.getContraseña()));
        user.setPais(request.getPais());

        if (request.getEmail().toLowerCase().endsWith("@utp.edu.pe")) {
            Optional<Empleado> existe = repoEmpleado.findByEmailEmpresarial(request.getEmail());
            if (existe.isPresent()) {
                Empleado empleado = existe.get();
                String cargo = empleado.getPuesto().getNombrePuesto().toUpperCase();
                for (Rol rol : Rol.values()) {
                    if (rol.toString().equalsIgnoreCase(cargo)) {
                        user.setRol(rol);
                        break;
                    }
                }
            } else {
                System.out.println("NO SE ENCONTRO EL CORREO");
            }
        } else {
            user.setRol(Rol.CLIENTE);
        }
        repoUsuario.save(user);

        return AuthResponse.builder().token(jwtServices.getToken(user)).build();

    }

}
