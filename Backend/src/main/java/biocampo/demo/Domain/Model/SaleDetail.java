package biocampo.demo.Domain.Model;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaleDetail {
    private Long saleDetailId;
    private Sale sale;
    private Product product;
    private int quantity;
    private BigDecimal total;
    private PaymentMethod paymentMethod;
    public enum PaymentMethod {
        PAYPAL, CARD
    }
}
