package biocampo.demo.Domain.Repository;

import java.util.List;
import java.util.Optional;

import biocampo.demo.Domain.Model.Customer;

public interface CustomerRepository {

    List<Customer> getAll();
    Optional<Customer> getByIdCustomer(Long id);
    Customer save(Customer customer);
    void deleteById(Long id);
}
