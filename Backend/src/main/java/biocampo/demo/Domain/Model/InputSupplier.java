package biocampo.demo.Domain.Model;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InputSupplier {
    private Long inputSupplierId;
    private Supplier supplier;
    private Input input;
    private BigDecimal price;
}
