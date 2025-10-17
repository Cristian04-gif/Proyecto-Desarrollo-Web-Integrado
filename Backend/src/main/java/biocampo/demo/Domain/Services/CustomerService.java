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
        Optional<User> existUser = userRepository.getByEmail(customer.getUser().getEmail());
        if (existUser.isPresent()) {
            customer.setUser(existUser.get());
        } else {
            throw new IllegalArgumentException("El usuario relacionado no existe");
        }

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
        Optional<Customer> existing = customerRepository.getByIdCustomer(id);
        if (existing.isPresent()) {
            Customer toUpdate = existing.get();
            toUpdate.setAge(customer.getAge());
            toUpdate.setPhone(customer.getPhone());
            toUpdate.setAddress(customer.getAddress());

            // tipo
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
            toUpdate.setType(customer.getType());

            // user
            Optional<User> existUser = userRepository.getByEmail(customer.getUser().getEmail());
            if (existUser.isPresent()) {
                customer.setUser(existUser.get());
            } else {
                throw new IllegalArgumentException("El usuario relacionado no existe");
            }
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
