package biocampo.demo.Persistance.Entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    @JoinColumn(name = "idPostCosecha")
    private PostCosecha plantaPostCosecha;
    @Lob
    private String imgProducto;
    private String etiqueta;
    private String descripcion;
    private float peso;
    private BigDecimal precio;
    private int cantidad;
    private boolean disponible;
    @ManyToOne
    @JoinColumn(name = "idCategoriaPlanta")
    private CategoriaPlanta categoriaPlanta;

    @OneToMany(mappedBy = "producto")
    private List<DetalleVenta> detalles;
}
