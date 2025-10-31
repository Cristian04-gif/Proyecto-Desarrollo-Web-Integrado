package biocampo.demo.Web.APIs;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import biocampo.demo.Domain.Model.Product;
import biocampo.demo.Domain.Services.ProductService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAll() {
        List<Product> all = productService.getAllProducts();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/priceLess")
    public ResponseEntity<List<Product>> getPriceLess(@RequestParam("price") BigDecimal price){
        List<Product> priceLess = productService.getPriceLess(price);
        return new ResponseEntity<>(priceLess, HttpStatus.OK);
    }    

    @GetMapping("/active")
    public ResponseEntity<List<Product>> getActives(@RequestParam("active") Boolean active){
        List<Product> allActives = productService.getActive(active);
        return new ResponseEntity<>(allActives, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Product> registerProduct(@RequestBody Product product){
        try {
            Product register = productService.registerProduct(product);
            return new ResponseEntity<>(register, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Error: "+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }     

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product){
        try {
            Product update = productService.updateProduct(id, product);
            return new ResponseEntity<>(update, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            System.out.println("Error: "+e);
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
