package biocampo.demo.Domain.Model;


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
    private String type;
    private String unitStatet;
    private double stock;
    private double priceUnit;
    private double totalCost;
    
    private Supplier supplier;
    //private Cultivation cultivation;
    //private List<InputSupplier> suppliers;
    //private List<InputCultivation> applications;
    /*public enum Type {
        SEED, FERTILIZER, PESTICIDE, HERBICIDE
    }*/
}
