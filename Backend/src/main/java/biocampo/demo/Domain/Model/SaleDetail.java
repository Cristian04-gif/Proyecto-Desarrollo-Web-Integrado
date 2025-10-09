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
public class SaleDetail {
    private Long saleDetailId;
    private Sale sale;
    private Product product;
    private int quantity;
    private BigDecimal subTotal;
    private String paymentMethod;
    /*public enum PaymentMethod {
        PAYPAL, CARD
    }*/
}
