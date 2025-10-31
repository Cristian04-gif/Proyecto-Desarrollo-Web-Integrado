package biocampo.demo.Domain.DTO.Request;

import java.util.List;

import biocampo.demo.Domain.Model.Employee;
import biocampo.demo.Domain.Model.PostHarvest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostHarvestRequest {
    private PostHarvest postHarvest;
    private List<Employee> employees;
}
