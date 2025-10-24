package biocampo.demo.Persistance.Entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    private Double cantidad;
    private String unidadMedida;
    @CreationTimestamp
    private LocalDate fechaAplicacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idCultivo")
    @JoinColumn(name = "id_cultivo")
    @JsonBackReference
    private Cultivo cultivo;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idInsumo")
    @JoinColumn(name = "id_insumo")
    @JsonBackReference
    private Insumo insumo;
}
