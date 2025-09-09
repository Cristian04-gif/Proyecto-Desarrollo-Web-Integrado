package biocampo.demo.Persistance.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Perdida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPerdida;
    private String tipoPerdida;
    private String accion;
    @ManyToOne
    private Cultivo cultivo;
    @ManyToOne
    private Cosecha cosecha;
}
