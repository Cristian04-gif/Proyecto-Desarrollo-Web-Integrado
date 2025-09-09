package biocampo.demo.Persistance.Entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Cosecha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCosecha;
    @ManyToOne
    private Planta planta;
    private LocalDate fechaCosecha;
    @Enumerated(EnumType.STRING)
    private Temporada temporada;
    @Enumerated(EnumType.STRING)
    private Recolector recolector;
    @OneToMany
    private List<Perdida> idPerdida;

    enum Recolector {
        MAQUINARIA, MANUAL
    }

    enum Temporada{
        PRIMAVERA, VERANO, OTONO, INVIERNO
    }
}
