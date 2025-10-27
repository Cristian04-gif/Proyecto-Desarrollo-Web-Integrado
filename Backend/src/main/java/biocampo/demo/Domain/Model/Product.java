package biocampo.demo.Domain.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Product {
    private Long productId;
    private PostHarvest postHarvest;
    private String imageUrl;
    private String name;
    private String description;
    private float weight;
    private Double price;
    private int stock;
    private boolean active;
    private PlantCategory plantCategory;
    
}
