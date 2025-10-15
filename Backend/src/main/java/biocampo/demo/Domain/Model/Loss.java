package biocampo.demo.Domain.Model;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Loss {
    private Long lossId;
    private String typeLoss;
    private String description;
    private double pocentageAffect;
    @CreationTimestamp
    private LocalDate lossDate;
    private Cultivation cultivation;
    private Harvest harvest;

}
