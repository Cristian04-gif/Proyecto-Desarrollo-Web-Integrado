package biocampo.demo.Domain.Model;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobPosition {
    private Long positionId;
    private String positionName;
    //private List<Employee> employee;
}
