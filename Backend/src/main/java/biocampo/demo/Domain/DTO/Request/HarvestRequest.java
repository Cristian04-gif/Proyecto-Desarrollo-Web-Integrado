package biocampo.demo.Domain.DTO.Request;

import java.util.List;

import biocampo.demo.Domain.Model.Employee;
import biocampo.demo.Domain.Model.Harvest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HarvestRequest {

    private Harvest harvest;
    private List<Employee> employees;
}
