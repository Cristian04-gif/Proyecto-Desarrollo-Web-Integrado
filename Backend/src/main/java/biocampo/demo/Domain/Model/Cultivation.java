package biocampo.demo.Domain.Model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Cultivation {
    private Long cultivationId;
    private Plant plant;
    private int hectares;
    private BigDecimal cost;
    private LocalDate startDate;
    private String season;
    private LocalDate endDate;
    //private List<Loss> losses;
    //private List<InputCultivation> inputs;
    //private List<Employee> employees;

}
