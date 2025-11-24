package biocampo.demo.Domain.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private Double kgComerciables;
    private Double priceKg;
    private Double lossKg;
    private Double totalReveneu;
    private Double profit;
    private String status;
    private LocalDateTime conversionDate;
    private String observations;
    //private List<Employee> employees;

}
