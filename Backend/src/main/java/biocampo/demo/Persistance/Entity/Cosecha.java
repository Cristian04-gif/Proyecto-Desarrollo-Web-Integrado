package biocampo.demo.Persistance.Entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
    @JoinColumn(name = "idCultivo")
    private Cultivo cultivo;

    @CreationTimestamp
    private LocalDate fechaCosecha;

    @Enumerated(EnumType.STRING)
    private Temporada temporada;

    @Enumerated(EnumType.STRING)
    private Recolector recolector;
    
    @OneToMany(mappedBy = "cosecha")
    private List<Perdida> idPerdida;

    @ManyToMany(mappedBy = "cosecha")
    private List<Empleado> empleados;

    public enum Recolector {
        MAQUINARIA, MANUAL
    }

    public enum Temporada {
        PRIMAVERA, VERANO, OTONO, INVIERNO
    }
}
