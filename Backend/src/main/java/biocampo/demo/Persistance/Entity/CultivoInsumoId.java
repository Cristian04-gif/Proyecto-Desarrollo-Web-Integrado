package biocampo.demo.Persistance.Entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class CultivoInsumoId implements Serializable {

    private Long idCultivo;
    private Long idInsumo;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof CultivoInsumoId))
            return false;
        CultivoInsumoId that = (CultivoInsumoId) o;
        return idCultivo.equals(that.idCultivo) && idInsumo.equals(that.idInsumo);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(idCultivo, idInsumo);
    }

    public CultivoInsumoId(Long idCultivo, Long idInsumo) {
        this.idCultivo = idCultivo;
        this.idInsumo = idInsumo;
    }
    
}
