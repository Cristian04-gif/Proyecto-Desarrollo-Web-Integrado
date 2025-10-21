package biocampo.demo.Persistance.Entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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

    @EmbeddedId
    private CultivoInsumoId id;

    private double cantidad;
    private String unidadMedida;
    @CreationTimestamp
    private LocalDate fechaAplicacion;

    @ManyToOne
    @MapsId("idCultivo")
    @JoinColumn(name = "idCultivo")
    private Cultivo cultivo;

    @ManyToOne
    @MapsId("idInsumo")
    @JoinColumn(name = "idInsumo")
    private Insumo insumo;
}
