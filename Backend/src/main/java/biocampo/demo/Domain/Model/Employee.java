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
    @CreationTimestamp
    private LocalDate hireDate;
}
