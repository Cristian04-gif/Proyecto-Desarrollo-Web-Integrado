package biocampo.demo.Domain.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Loss {
    private Long lossId;
    private String typeLoss;
    private String action;
    private Cultivation cultivation;
    private Harvest harvest;

}
