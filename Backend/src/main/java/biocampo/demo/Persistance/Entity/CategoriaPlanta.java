package biocampo.demo.Persistance.Entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class CategoriaPlanta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCateogriaPlanta;
    private String nombre;
    @OneToMany(mappedBy = "idPlanta")
    private List<Planta> planta;
}
