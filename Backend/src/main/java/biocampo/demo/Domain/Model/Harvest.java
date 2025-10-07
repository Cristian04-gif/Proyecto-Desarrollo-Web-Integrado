package biocampo.demo.Domain.Model;

import java.time.LocalDate;

import biocampo.demo.Persistance.Entity.Cultivo;
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
    private Cultivo cultivation;
    private LocalDate dateHarvested;
    private String season;
    private String collector;
    //private List<Loss> losses;
    //private List<Employee> employees;
    /*public enum Season {
        SPRING, SUMMER, AUTUMN, WINTER
    }
    public enum Colector {
        MACHINERY, MANUAL
    }*/
}
