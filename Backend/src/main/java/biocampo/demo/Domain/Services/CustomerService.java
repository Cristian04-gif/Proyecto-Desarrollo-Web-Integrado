package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.Customer;
import biocampo.demo.Domain.Repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.getAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.getByIdCustomer(id);
    }

    public Customer registerCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer customer) {
        Optional<Customer> existing = customerRepository.getByIdCustomer(id);
        if (existing.isPresent()) {
            Customer toUpdate = existing.get();
            toUpdate.setAge(customer.getAge());
            toUpdate.setPhone(customer.getPhone());
            toUpdate.setAddress(customer.getAddress());
            toUpdate.setType(customer.getType());
            toUpdate.setUser(customer.getUser());
            return customerRepository.save(toUpdate);
        } else {
            return null;
        }
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public List<Customer> getCustomersByType(String type) {
        return customerRepository.findByType(type);
    }
}
