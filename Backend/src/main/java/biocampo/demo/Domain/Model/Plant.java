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
    private String description;
    private Double seedingDensity;
    private Double averageSeedWeight;
    private Double weightPerPackage;
    private int harvestDays;
    private boolean available;
    private PlantCategory category;
}
