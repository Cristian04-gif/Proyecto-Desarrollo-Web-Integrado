package biocampo.demo.Domain.Model;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InputCultivation {
    private Long inputCultivationId;
    private BigDecimal quantity;
    private Cultivation cultivation;
    private Input input;
    
}
