package biocampo.demo.Domain.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Loss {
    private Long lossId;
    private String typeLoss;
    private String action;
    private Cultivation cultivation;
    private Harvest harvest;

}
