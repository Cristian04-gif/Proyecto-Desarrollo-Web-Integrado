package biocampo.demo.Domain.Model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Input {
    private Long inputId;
    private String name;
    private Type type;
    private String description;
    private String unit;
    private Plant plant;
    private Cultivation cultivation;
    private List<InputSupplier> suppliers;
    private List<InputCultivation> applications;
    public enum Type {
        SEED, FERTILIZER, PESTICIDE, HERBICIDE
    }
}
