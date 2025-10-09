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
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class PostCosecha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPostCosecha;

    @OneToOne
    @JoinColumn(name = "idCosecha")
    private Cosecha plantaCosechada;
    @CreationTimestamp
    private LocalDate fecha;
    private String limpieza;
    private String tratamiento;

    @Enumerated(EnumType.STRING)
    private Empaque empaque;
    
    @Enumerated(EnumType.STRING)
    private Almacenamiento almacenamiento;

    @ManyToMany(mappedBy = "postCosecha")
    private List<Empleado> empleados;
    
    public enum Empaque {
        SACO, CAJA, BANDEJA
    }

    public enum Almacenamiento {
        SILO, BODEGA, CAMARA_FRIA;
    }


}
