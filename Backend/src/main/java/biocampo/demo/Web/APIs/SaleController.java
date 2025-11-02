package biocampo.demo.Web.APIs;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import biocampo.demo.Domain.DTO.Request.SaleRequest;
import biocampo.demo.Domain.Model.Sale;
import biocampo.demo.Domain.Services.SaleService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @GetMapping("/all")
    public ResponseEntity<List<Sale>> getAll() {
        List<Sale> all = saleService.getAllSales();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/customer/{emailUser}")
    public ResponseEntity<List<Sale>> getSaleByCustomerId(@PathVariable String emailUser){
        List<Sale> sales = saleService.getSaleByCustomerId(emailUser);
        return new ResponseEntity<>(sales, HttpStatus.OK);
    }    

    @GetMapping("/id/{id}")
    public ResponseEntity<Sale> getSale(@PathVariable Long id) {
        Optional<Sale> optional = saleService.getSaleById(id);
        return optional.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/register")
    public ResponseEntity<Sale> registerSale(@RequestBody SaleRequest saleRequest) {
        try {
            Sale sale = saleService.registerSale(saleRequest.getSale(), saleRequest.getDetails());
            return new ResponseEntity<>(sale, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("error: "+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        System.out.println("controlador delete de venta");
        try {
            saleService.deleteSale(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            System.out.println("error: "+e);
            return ResponseEntity.notFound().build();
        }
    }
}
