package biocampo.demo.Domain.Model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Sale {
    private Long saleId;
    private Customer customer;
    private LocalDateTime saleDate;
    //private List<SaleDetail> details;
}
