package biocampo.demo.Domain.Model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Customer {
    private Long customerId;
    private User user;
    private int age;
    private String phone;
    private String address;
    private Type type;
    private List<Sale> purchases;
    public enum Type {
        WHOLESALER, RETAILER
    }
}
