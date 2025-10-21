package biocampo.demo.Domain.Model;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Order {
    private Long orderId;
    @CreationTimestamp
    private LocalDate date;
    private double total;
    private Supplier supplier;
    //private List<OrderDetail> orderDetails;
}
