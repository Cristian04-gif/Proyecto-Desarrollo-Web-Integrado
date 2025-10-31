package biocampo.demo.Persistance.Entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
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
    private PostCosecha postCosecha;
    @Lob
    private String imgProducto;
    private String etiqueta;
    private String descripcion;
    private float peso;
    private String unidadMedida;
    private Double precio;
    private int cantidad; //cantidad inicial
    private int stock; //cantidad actualmente disponible
    private boolean disponible;
    private String codigoLote;

    @CreationTimestamp
    private LocalDate fechaRegistro;
    @UpdateTimestamp
    private LocalDate fechaActualizacion;
    @ManyToOne
    @JoinColumn(name = "idCategoriaPlanta")
    private CategoriaPlanta categoriaPlanta;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.MERGE)
    private List<DetalleVenta> detalles;
}
