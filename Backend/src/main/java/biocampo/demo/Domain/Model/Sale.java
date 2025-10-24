package biocampo.demo.Domain.Model;

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
    private Double total;
    private String paymentMethod;
    //private List<SaleDetail> details;
}
