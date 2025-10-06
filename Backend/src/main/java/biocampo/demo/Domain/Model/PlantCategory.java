package biocampo.demo.Domain.Model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlantCategory {
    private Long categoryId;
    private String categoryName;
    private List<Plant> plant;
}
