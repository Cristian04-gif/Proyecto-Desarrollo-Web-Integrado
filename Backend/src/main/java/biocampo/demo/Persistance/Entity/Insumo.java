package biocampo.demo.Persistance.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Insumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInsumo;
    private String nombre;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    
    private String unidadMedida;
    private double stock;
    private double precioUnitario;
    private double costoTotal;

    

    @ManyToOne
    @JoinColumn(name = "idProveedor")
    private Proveedor proveedor;

    @OneToMany(mappedBy = "insumo", cascade = CascadeType.MERGE)
    private List<DetallePedido> detallePedidos;

    @OneToMany(mappedBy = "insumo", cascade = CascadeType.MERGE)
    private List<CultivoInsumo> cultivoInsumos;

    public enum Tipo {
        SEMILLA, FERTILIZANTE, PESTICIDA, HERBICIDA
    }
}
