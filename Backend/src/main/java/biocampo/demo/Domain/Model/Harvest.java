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
public class Harvest {
    private Long harvestId;
    private Cultivation cultivation;
    @CreationTimestamp
    private LocalDate dateHarvested;

    private Double harvestQuantity;
    private String unitMeasure;
    private Double yeilfHectare;
    private Double cost;

    private List<Employee> employees;
}
