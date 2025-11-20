package biocampo.demo.Web.APIs;

import java.util.List;
import java.util.Optional;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import biocampo.demo.Domain.Model.Customer;
import biocampo.demo.Domain.Services.CustomerService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/customer")
public class CustomerController {


    @Autowired
    private CustomerService customerService;

    

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAll() {
        List<Customer> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/type")
    public ResponseEntity<List<Customer>> getCustomerByType(@RequestParam("type") String type) {
        List<Customer> customers = customerService.getCustomersByType(type);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<Customer> getCustomerByEmailUser(@PathVariable String email){
        Customer customer = customerService.getCustomerByEmailUser(email).orElseThrow();
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }    

    @GetMapping("/id/{id}")
    public ResponseEntity<Customer> getCusomer(@PathVariable Long id) {
        Optional<Customer> optional = customerService.getCustomerById(id);
        return optional.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer){
        try {
            Customer customer2 = customerService.registerCustomer(customer);
            return new ResponseEntity<>(customer2, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer){
        try {
            Customer customer2 = customerService.updateCustomer(id, customer);
            return new ResponseEntity<>(customer2, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
