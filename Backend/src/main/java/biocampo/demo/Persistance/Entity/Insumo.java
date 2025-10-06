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
    private String descripcion;
    // kg, bolas, mallas, etc.
    private String unidad;

    @ManyToOne
    @JoinColumn(name = "idPlanta")
    private Planta planta;
    
    @ManyToOne
    @JoinColumn(name = "idCultivo")
    private Cultivo cultivo;

    @OneToMany(mappedBy = "insumo", cascade = CascadeType.ALL)
    private List<ProveedorInsumo> proveedorInsumos;

    @OneToMany(mappedBy = "insumo", cascade = CascadeType.ALL)
    private List<CultivoInsumo> cultivoInsumos;


    public enum Tipo {
        SEMILLA, FERTILIZANTE, PESTICIDA, HERBICIDA
    }
}
