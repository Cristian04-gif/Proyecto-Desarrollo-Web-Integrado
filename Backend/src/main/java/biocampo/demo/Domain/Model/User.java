package biocampo.demo.Domain.Model;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String country;
    @CreationTimestamp
    private LocalDate dateRegistered;
    private String role;
    /*public enum Role {
        ADMIN, CUSTOMER, AGRONOMIST, BUYER, SUPERVISOR, WAREHOUSE, SALESPERSON
    }*/

}
