package biocampo.demo.Domain.Model;


import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import biocampo.demo.Persistance.Entity.CultivoInsumoId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class InputCultivation {
    private CultivoInsumoId inputCultivationId;
    private Double quantity;
    private String extent;
    @CreationTimestamp
    private LocalDate applicationDate;
    private Cultivation cultivation;
    private Input input;
    
}
