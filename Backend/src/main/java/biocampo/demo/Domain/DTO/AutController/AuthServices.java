package biocampo.demo.Domain.DTO.AutController;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.DTO.JWT.JwtServices;
import biocampo.demo.Persistance.CRUD.RepoEmpleado;
import biocampo.demo.Persistance.CRUD.RepoPuestoEmpleado;
import biocampo.demo.Persistance.CRUD.RepoUsuario;
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
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails user = repoUsuario.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        String token = jwtServices.getToken(user);
        String usuario = request.getEmail();
        return AuthResponse.builder().token(token).email(usuario).build();
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
            user.setRol(Rol.ADMIN);

        } else if (request.getEmail().toLowerCase().endsWith("@biocampo.com")) {
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
        repoUsuario.save(user);

        return AuthResponse.builder().token(jwtServices.getToken(user)).build();

    }

    public AuthResponse update(String email, RegisterRequest request) {
        Optional<Usuario> usuarioExistente = repoUsuario.findByEmail(email);
        if (usuarioExistente.isEmpty()) {
            throw new RuntimeException("El correo no está registrado");
        }
        Usuario user = usuarioExistente.get();
        user.setNombre(request.getNombre());
        user.setApellido(request.getApellido());
        user.setEmail(request.getEmail());
        user.setPais(request.getPais());

        if (request.getContraseña() != null && !request.getContraseña().isEmpty()) {
            user.setContraseña(passwordEncoder.encode(request.getContraseña()));
        }
        if (request.getEmail().toLowerCase().endsWith("@utp.edu.pe")) {
            user.setRol(Rol.ADMIN);
        } else {
            Optional<Empleado> existe = repoEmpleado.findByEmailEmpresarial(request.getEmail());
            if (existe.isPresent()) {
                Empleado empleado = existe.get();

                Optional<PuestoEmpleado> existePuesto = repoPuestoEmpleado.findById(empleado.getPuesto().getIdPuesto());

                if (existePuesto.isPresent()) {
                    PuestoEmpleado puesto = existePuesto.get();
                    boolean positionEmployee = false;
                    for (Rol rol : Rol.values()) {
                        if (rol.toString().equalsIgnoreCase(puesto.getNombrePuesto())) {
                            user.setRol(rol);
                            positionEmployee = true;
                            break;
                        }
                    }
                    if (!positionEmployee) {
                        user.setRol(Rol.CLIENTE);
                    }
                }
            } else {
                user.setRol(Rol.CLIENTE);
            }
        }

        repoUsuario.save(user);
        return AuthResponse.builder().token(jwtServices.getToken(user)).build();

    }
}
