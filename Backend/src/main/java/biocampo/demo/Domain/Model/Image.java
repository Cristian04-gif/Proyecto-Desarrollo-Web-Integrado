package biocampo.demo.Domain.Model;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
