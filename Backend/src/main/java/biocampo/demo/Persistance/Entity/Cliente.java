package biocampo.demo.Persistance.Entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @OneToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
    
    private int edad;
    private String telefono;
    private String direccion;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @OneToMany
    @JoinColumn(name = "idCliente")
    private List<Venta> compras;

    public enum Tipo {
        MAYORISTA, MINORISTA
    }
}
