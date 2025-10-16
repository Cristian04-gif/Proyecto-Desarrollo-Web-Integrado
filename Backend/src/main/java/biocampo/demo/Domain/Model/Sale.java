package biocampo.demo.Domain.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
    @CreationTimestamp
    private LocalDateTime saleDate;
    private BigDecimal total;
    //private List<SaleDetail> details;
}
