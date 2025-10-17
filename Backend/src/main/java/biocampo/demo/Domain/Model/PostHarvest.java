package biocampo.demo.Domain.Model;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

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
    @CreationTimestamp
    private LocalDate dateProcessed;
    private String cleaningMethod;
    private String treatmentMethod;
    private String packing;
    private String storage;
    private int stock;
    //private List<Employee> employees;

    /*public enum Packing {
        BAG, BOX, CRATE
    }

    public enum Storage {
        SILO, WAREHOUSE, REFRIGERATED_STORAGE;
    }*/
}
