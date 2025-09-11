package biocampo.demo.Persistance.Entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;
    @OneToOne
    private PostCosecha PantaPostCosecha;
    @Lob
    private String imgProducto;
    private String etiqueta;
    private float peso;
    private BigDecimal precio;
    private int cantidad;
    private boolean disponible;
}
