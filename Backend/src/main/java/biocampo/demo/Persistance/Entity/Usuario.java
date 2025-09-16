package biocampo.demo.Persistance.Entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private String contraseña;
    private String pais;
    @CreationTimestamp
    private LocalDateTime fechaRegistro;
    @Enumerated(EnumType.STRING)
    private Rol rol;

    public enum Rol {
        ADMIN, CLIENTE, AGRONOMO, COMPRADOR, SUPERVISOR, ALMACEN, VENDEDOR
    }
}
