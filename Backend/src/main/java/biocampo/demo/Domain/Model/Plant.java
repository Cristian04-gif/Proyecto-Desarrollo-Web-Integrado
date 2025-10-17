package biocampo.demo.Domain.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Plant {

    private Long plantId;
    private String name;
    private int stock;
    private double seedingDensity;
    private double averageSeedWeight;
    private double weightPerPackage;
    private boolean available;
    private PlantCategory category;
}
