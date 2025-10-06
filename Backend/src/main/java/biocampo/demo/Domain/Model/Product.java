package biocampo.demo.Domain.Model;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product {
    private Long productId;
    private PostHarvest postHarvest;
    private String imageUrl;
    private String name;
    private String description;
    private float weight;
    private BigDecimal price;
    private int stock;
    private boolean isActive;
    
}
