package biocampo.demo.Persistance.Entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CultivoInsumoId implements Serializable {

    @Column(name = "id_cultivo")
    private Long idCultivo;
    @Column(name = "id_insumo")
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
    
}
