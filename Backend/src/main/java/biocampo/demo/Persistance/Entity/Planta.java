package biocampo.demo.Persistance.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Planta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlanta;
    private String nombre;
    private int stock;
    @ManyToOne
    @JoinColumn(name = "idCategoria")
    private CategoriaPlanta categoria;
    private boolean disponible;
}
