package biocampo.demo.Domain.DTO.Request;

import java.util.List;

import biocampo.demo.Domain.Model.Cultivation;
import biocampo.demo.Domain.Model.Employee;
import biocampo.demo.Domain.Model.InputCultivation;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CultivationRequest {

    private Cultivation cultivation;
    private List<InputCultivation> inputCultivation;
    private List<Employee> employees;
}
