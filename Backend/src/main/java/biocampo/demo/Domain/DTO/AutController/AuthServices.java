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
import biocampo.demo.Domain.Repository.RepoPuestoEmpleado;
import biocampo.demo.Domain.Repository.RepoUsuario;
import biocampo.demo.Persistance.Entity.Empleado;
import biocampo.demo.Persistance.Entity.PuestoEmpleado;
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

    @Autowired
    private RepoPuestoEmpleado repoPuestoEmpleado;

    private final PasswordEncoder passwordEncoder;
    private final JwtServices jwtServices;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        System.out.println("Entro al login de usuario");
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails user = repoUsuario.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        System.out.println("El usuario encontrado es: " + user.getUsername());
        String token = jwtServices.getToken(user);
        System.out.println("El token generado es: " + token);
        System.out.println("Se inicio sesion el usuario con email: " + request.getEmail());
        return AuthResponse.builder().token(token).build();
    }

    public AuthResponse register(RegisterRequest request) {
        System.out.println("Entro al register de usuario");
        Optional<Usuario> usuarioExistente = repoUsuario.findByEmail(request.getEmail());
        if (usuarioExistente.isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Usuario user = new Usuario();
        user.setNombre(request.getNombre());
        user.setApellido(request.getApellido());
        user.setEmail(request.getEmail());
        user.setContraseña(passwordEncoder.encode(request.getContraseña()));
        user.setPais(request.getPais());

        if (request.getEmail().toLowerCase().endsWith("@utp.edu.pe")) {
            System.out.println("EL correo es empresarial");
            Optional<Empleado> existe = repoEmpleado.findByEmailEmpresarial(request.getEmail());
            if (existe.isPresent()) {
                System.out.println("Se encontro el correo");
                Empleado empleado = existe.get();

                Optional<PuestoEmpleado> existePuesto = repoPuestoEmpleado.findById(empleado.getPuesto().getIdPuesto());

                if (existePuesto.isPresent()) {
                    System.out.println("El cargo existe");
                    PuestoEmpleado puesto = existePuesto.get();
                    for (Rol rol : Rol.values()) {
                    if (rol.toString().equalsIgnoreCase(puesto.getNombrePuesto())) {
                        user.setRol(rol);
                        break;
                    }
                }
                }
            } else {
                System.out.println("NO SE ENCONTRO EL CORREO");
            }
        } else {
            user.setRol(Rol.CLIENTE);
        }
        System.out.println("EL usruario se registro con el rol: " + user.getRol());
        repoUsuario.save(user);

        return AuthResponse.builder().token(jwtServices.getToken(user)).build();

    }

}
