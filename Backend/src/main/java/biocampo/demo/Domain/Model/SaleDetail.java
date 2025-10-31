package biocampo.demo.Domain.Model;


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
    private Double subTotal;
    private Double taxes;
    private Double taxPercentage;
    //private String paymentMethod;
    /*public enum PaymentMethod {
        PAYPAL, CARD
    }*/
}
