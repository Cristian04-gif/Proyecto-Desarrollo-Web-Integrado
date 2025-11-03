package biocampo.demo.Web.Export;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import biocampo.demo.Domain.Services.SaleService;
import net.sf.jasperreports.engine.JRException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class ExportPDF {

    @Autowired
    private SaleService saleService;

    @GetMapping("/sale/customer/{email}")
    public ResponseEntity<byte[]> exportSaleByCustomer(@PathVariable String email) throws JRException {
        try {
            byte[] pdf = saleService.generarComprobanteVenta(email);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=comprobante.pdf")
                    .contentType(MediaType.APPLICATION_PDF).body(pdf);

        } catch (Exception e) {
            System.out.println("Error al generar el PDF: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
