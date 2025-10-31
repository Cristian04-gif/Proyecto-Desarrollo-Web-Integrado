package biocampo.demo.Web.APIs;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import biocampo.demo.Domain.Model.OrderDetail;
import biocampo.demo.Domain.Services.OrderDetailService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/orderDetail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService detailService;

    @GetMapping("/all")
    public ResponseEntity<List<OrderDetail>> getAll() {
        List<OrderDetail> all = detailService.getAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<List<OrderDetail>> getAllByOrderId(@PathVariable Long id) {
        List<OrderDetail> byOrder = detailService.getAllByOrder(id);
        return new ResponseEntity<>(byOrder, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<OrderDetail> getById(@PathVariable Long id) {
        Optional<OrderDetail> detail = detailService.getOrderDetail(id);
        return detail.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrderDetail> updateDetail(@PathVariable Long id, @RequestBody OrderDetail detail){
        try {
            OrderDetail update = detailService.updateOrderDetail(id, detail);
            return new ResponseEntity<>(update, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            detailService.deteleOrderDetail(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
