package biocampo.demo.Domain.Model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class InputCultivation {
    private Long inputCultivationId;
    private BigDecimal quantity;
    private Cultivation cultivation;
    private Input input;
    
}
