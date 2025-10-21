package biocampo.demo.Domain.DTO.Request;

import java.util.List;

import biocampo.demo.Domain.Model.Order;
import biocampo.demo.Domain.Model.OrderDetail;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderRequest {
    private Order order;
    private List<OrderDetail> details;
}
