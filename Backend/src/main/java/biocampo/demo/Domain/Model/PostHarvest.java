package biocampo.demo.Domain.Model;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PostHarvest {
    private Long postHarvestId;
    private Harvest harvest;
    private LocalDate dateProcessed;
    private String cleaningMethod;
    private String treatmentMethod;
    private String packing;
    private String storage;
    //private List<Employee> employees;

    /*public enum Packing {
        BAG, BOX, CRATE
    }

    public enum Storage {
        SILO, WAREHOUSE, REFRIGERATED_STORAGE;
    }*/
}
