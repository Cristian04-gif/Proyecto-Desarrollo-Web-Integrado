package biocampo.demo.Domain.DTO.AutController;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    String nombre;
    String apellido;
    String email;
    String contraseña;
    String pais;
}
