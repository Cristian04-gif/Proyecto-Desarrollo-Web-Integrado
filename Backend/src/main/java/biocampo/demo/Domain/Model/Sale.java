package biocampo.demo.Domain.Model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Sale {
    private Long saleId;
    private Customer customer;
    private LocalDateTime saleDate;
    private List<SaleDetail> details;
}
