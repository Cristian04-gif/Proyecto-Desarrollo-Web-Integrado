package biocampo.demo.Persistance.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CultivoInsumo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int cantidad;

    @ManyToOne
    @JoinColumn(name="idCultivo")
    private Cultivo cultivo;

    @ManyToOne
    @JoinColumn(name = "idInsumo")
    private Insumo insumo;
}
