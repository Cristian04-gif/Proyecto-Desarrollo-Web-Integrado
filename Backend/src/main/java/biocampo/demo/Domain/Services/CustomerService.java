package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.Customer;
import biocampo.demo.Domain.Model.User;
import biocampo.demo.Domain.Repository.CustomerRepository;
import biocampo.demo.Domain.Repository.UserRepository;
import biocampo.demo.Persistance.Entity.Cliente.Tipo;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.getAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.getByIdCustomer(id);
    }

    public Customer registerCustomer(Customer customer) {
        User user = userRepository.getByEmail(customer.getUser().getEmail()).orElseThrow();
        customer.setUser(user);

        boolean existType = false;
        for (Tipo tipo : Tipo.values()) {
            if (tipo.toString().equalsIgnoreCase(customer.getType())) {
                customer.setType(tipo.name());
                existType = true;
                break;
            }
        }
        if (!existType) {
            throw new IllegalArgumentException("El tipo no fue identificado");
        }
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer customer) {
        Customer toUpdate = customerRepository.getByIdCustomer(id)
                .orElseThrow(() -> new IllegalArgumentException("El cliente no existe"));
        User user = userRepository.getByEmail(customer.getUser().getEmail()).orElseThrow();

        if (customer.getUser() != null && customer.getUser().getEmail() != null) {
            toUpdate.setUser(user);
        }
        if (customer.getAge() > 0) {
            toUpdate.setAge(customer.getAge());
        }
        if (customer.getPhone() != null) {
            toUpdate.setPhone(customer.getPhone());
        }
        if (customer.getType() != null) {
            boolean existType = false;
            for (Tipo tipo : Tipo.values()) {
                if (tipo.toString().equalsIgnoreCase(customer.getType())) {
                    customer.setType(tipo.name());
                    existType = true;
                    break;
                }
            }
            if (!existType) {
                throw new IllegalArgumentException("El tipo no fue identificado");
            }
        }
        return customerRepository.save(toUpdate);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public List<Customer> getCustomersByType(String type) {
        return customerRepository.findByType(type);
    }
}
