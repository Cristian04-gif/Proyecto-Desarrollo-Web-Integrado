package biocampo.demo.Persistance.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private Cosecha plantaCosechada;
    private String limpieza;
    private String tratamiento;

    @Enumerated(EnumType.STRING)
    private Empaque empaque;
    
    @Enumerated(EnumType.STRING)
    private Almacenamiento almacenamiento;

    enum Empaque {
        SACO, CAJA, BANDEJA
    }

    enum Almacenamiento {
        SILO, BODEGA, CAMARAFRIA;
    }
}
