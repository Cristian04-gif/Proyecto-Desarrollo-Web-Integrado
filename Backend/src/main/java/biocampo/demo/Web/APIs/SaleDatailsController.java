package biocampo.demo.Web.APIs;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import biocampo.demo.Domain.Model.Sale;
import biocampo.demo.Domain.Model.SaleDetail;
import biocampo.demo.Domain.Services.SaleDetailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/saleDetails")
public class SaleDatailsController {
    @Autowired
    private SaleDetailService detailService;

    @GetMapping("/all")
    public ResponseEntity<List<SaleDetail>> getAll() {
        List<SaleDetail> details = detailService.getAllSaleDetails();
        return new ResponseEntity<>(details, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<SaleDetail> getSaleDetail(@PathVariable Long id) {
        Optional<SaleDetail> optional = detailService.getSaleDetailById(id);
        return optional.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/sale")
    public ResponseEntity<List<SaleDetail>> getDetailsBySale(@PathVariable Long idSale){
        List<SaleDetail> details = detailService.getSaleDetailBySale(idSale);
        return new ResponseEntity<>(details, HttpStatus.OK);
    }
}
