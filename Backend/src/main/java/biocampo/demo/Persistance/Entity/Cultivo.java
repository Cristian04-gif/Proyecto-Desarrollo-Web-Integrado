package biocampo.demo.Persistance.Entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import biocampo.demo.Persistance.Entity.Cosecha.Temporada;
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
public class Cultivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCultivo;
    
    @ManyToOne
    private Planta semilla;
    // 1 hectarea = 10 000 m2
    private int hectareas;
    private String fertilizante;
    private BigDecimal costo;
    
    @CreationTimestamp
    private LocalDate fechaCultivo;
    private int cadaRiego;
    
    @Enumerated(EnumType.STRING)
    private Temporada temporada;
    private Date fechaEstimadaCosecha;

    @OneToMany
    private List<Perdida> perdida;
}
