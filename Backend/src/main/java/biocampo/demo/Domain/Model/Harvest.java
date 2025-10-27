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
    //private String season;
    //private String collector;
    //private List<Loss> losses;
    private List<Employee> employees;
    /*public enum Season {
        SPRING, SUMMER, AUTUMN, WINTER
    }
    public enum Colector {
        MACHINERY, MANUAL
    }*/
}
