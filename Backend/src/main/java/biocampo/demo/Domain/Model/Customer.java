package biocampo.demo.Domain.Model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
