package biocampo.demo.Domain.Model;


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
    private String type;
    //private List<Sale> purchases;
    
}
