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
public class Cultivation {
    private Long cultivationId;
    private String plotName;
    private Plant plant;
    private Double hectares;
    private Double requiredPackages;
    private Double cost;
    @CreationTimestamp
    private LocalDate startDate;
    private int eachIrrigation;
    private String season;
    private LocalDate endDate;
    private List<Employee> employees;

}
