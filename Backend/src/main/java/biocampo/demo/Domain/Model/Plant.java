package biocampo.demo.Domain.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Plant {

    private Long PlantId;
    private String name;
    private int stock;
    private boolean available;
    private PlantCategory category;
}
