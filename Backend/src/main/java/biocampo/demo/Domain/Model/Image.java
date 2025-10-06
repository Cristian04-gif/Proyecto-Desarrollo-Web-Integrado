package biocampo.demo.Domain.Model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Image {
    private Long imageId;
    private String url;
    private EntityType entityType;
    private Long referenceId;
    private LocalDate uploadDate;

    public enum EntityType {
        CULTIVARION, HARVEST, POSTHARVEST
    }
}
