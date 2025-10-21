package biocampo.demo.Domain.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {

    private Long detailId;
    private int amount;
    private double priceUnit;
    private Order order;
    private Input input;
}
