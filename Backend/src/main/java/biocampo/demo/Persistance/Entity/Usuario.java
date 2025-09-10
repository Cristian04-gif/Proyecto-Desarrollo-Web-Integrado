package biocampo.demo.Persistance.Entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    private String nombre;
    private String apellido;
    private int edad;
    private String email;
    private String contraseña;
    private String pais;
    private String telefono;
    private String direccion;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    @OneToMany
    private List<Venta> compras;
    public enum Rol {
        ADMIN, USER
    }
}
