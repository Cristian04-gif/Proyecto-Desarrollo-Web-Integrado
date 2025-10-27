package biocampo.demo.Domain.Model;

import java.time.LocalDate;
import java.util.List;

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
    
    private Double storageCost;
    private Double costEmployee;
    private Double unitPrice;
    private Double lossUnit;
    private Double totalReveneu;
    private Double profit;
    private String observations;
    private List<Employee> employees;

    /*public enum Packing {
        BAG, BOX, CRATE
    }

    public enum Storage {
        SILO, WAREHOUSE, REFRIGERATED_STORAGE;
    }*/
}
