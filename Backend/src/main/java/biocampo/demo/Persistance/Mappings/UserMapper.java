package biocampo.demo.Persistance.Mappings;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import biocampo.demo.Domain.Model.User;
import biocampo.demo.Persistance.Entity.Usuario;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(source = "idUsuario", target = "userId"),
            @Mapping(source = "nombre", target = "firstName"),
            @Mapping(source = "apellido", target = "lastName"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "contraseña", target = "password"),
            @Mapping(source = "pais", target = "country"),
            @Mapping(source = "fechaRegistro", target = "dateRegistered"),
            @Mapping(source = "rol", target = "role")
    })
    User toUser(Usuario user);

    List<User> toUsers(List<Usuario> usuarios);

    @InheritInverseConfiguration
    //@Mapping(target = "contraseña", ignore = true)
    // @Mapping(target = "idUsuario", ignore = true)
    //@Mapping(target = "fechaRegistro", ignore = true)
    //@Mapping(target = "rol", ignore = true)
    Usuario toUsuario(User user);
}
