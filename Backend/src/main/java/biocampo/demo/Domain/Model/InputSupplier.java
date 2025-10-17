package biocampo.demo.Domain.Model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class InputSupplier {
    private Long inputSupplierId;
    private Supplier supplier;
    private Input input;
    private BigDecimal price;
}
