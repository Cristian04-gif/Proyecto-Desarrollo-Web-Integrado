package biocampo.demo.Web.APIs;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import biocampo.demo.Domain.DTO.Request.OrderRequest;
import biocampo.demo.Domain.Model.Order;
import biocampo.demo.Domain.Services.OrderService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAll() {
        List<Order> inputSuppliers = orderService.getAll();
        return new ResponseEntity<>(inputSuppliers, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Order> getInputSupplier(@PathVariable Long id) {
        Optional<Order> optional = orderService.getInputSupplier(id);
        return optional.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/register")
    public ResponseEntity<Order> registerOrder(@RequestBody OrderRequest orderRequest){
        try {
            Order registerOrder = orderService.registerOrder(orderRequest.getOrder(), orderRequest.getDetails());
            return new ResponseEntity<>(registerOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /*@DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrderAndDetails(@PathVariable Long id){
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }*/

    /*@PostMapping("/register")
    public ResponseEntity<Order> registerInputSupplier(@RequestBody Order inputSupplier) {
        try {
            Order inputSupplier2 = inputSupplierService.registerInputSupplier(inputSupplier);
            return new ResponseEntity<>(inputSupplier2, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Order> updateInputSupplier(@PathVariable Long id, @RequestBody Order inputSupplier){
        try {
            Order inputSupplier2 = inputSupplierService.updataInputSupplier(id, inputSupplier);
            return new ResponseEntity<>(inputSupplier2, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            inputSupplierService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }*/
}
