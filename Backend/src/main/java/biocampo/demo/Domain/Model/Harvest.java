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
public class Harvest {
    private Long harvestId;
    private Plant plant;
    private LocalDate dateHarvested;
    private Season season;
    private Colector collector;
    private List<Loss> losses;
    private List<Employee> employees;
    public enum Season {
        SPRING, SUMMER, AUTUMN, WINTER
    }
    public enum Colector {
        MACHINERY, MANUAL
    }
}
