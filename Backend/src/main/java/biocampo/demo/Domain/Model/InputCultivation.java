package biocampo.demo.Domain.Model;


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
    private int quantity;
    private Cultivation cultivation;
    private Input input;
    
}
