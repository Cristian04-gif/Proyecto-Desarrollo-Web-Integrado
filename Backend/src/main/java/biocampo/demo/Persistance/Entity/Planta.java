package biocampo.demo.Persistance.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
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
public class Planta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlanta;
    private String nombre;
    private String descripcion;

    private double densidadSiembra; // plantas por metro cuadrado
    private double pesoSemillaPromedio; // en gramos
    private double pesoPorPaquete; // en kilogramos
    private int diasCosecha;
    private boolean disponible;

    @ManyToOne
    @JoinColumn(name = "idCategoria")
    @JsonBackReference
    private CategoriaPlanta categoria;

    @OneToMany(mappedBy = "planta")
    private List<Cultivo> cultivos;    
}
