package biocampo.demo.Domain.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Employee {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private int age;
    private String phone;
    private String personalEmail;
    private String workEmail;
    private String dni;
    private String country;
    private String address;
    private JobPosition jobPosition;
    private double salary;
    private String hireDate;
}
