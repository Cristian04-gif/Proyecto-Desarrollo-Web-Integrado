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
public class Image {
    private Long imageId;
    private String url;
    private String entityType;
    private Long referenceId;
    @CreationTimestamp
    private LocalDate uploadDate;

}
