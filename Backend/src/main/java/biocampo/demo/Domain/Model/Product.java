package biocampo.demo.Domain.Model;


import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private String unitMeasure;
    private Double price;
    private int quantity;
    private int stock;
    private boolean active;
    private String lotCode;

    @CreationTimestamp
    private LocalDate registrationDate;
    @UpdateTimestamp
    private LocalDate updateDate;
    private PlantCategory plantCategory;
    
}
