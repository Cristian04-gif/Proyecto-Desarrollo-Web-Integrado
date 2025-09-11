package biocampo.demo.Persistance.Entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idImagen;

    @Lob
    @Column(nullable = false, length = 500)
    private String url;
    @Enumerated(EnumType.STRING)
    private TipoEntidad tipoEntidad;
    private Long idReferencia;
    @CreationTimestamp
    private LocalDate fechaSubida;

    public enum TipoEntidad {
        CULTIVO, COSECHA, POSTCOSECHA
    }
}
