package biocampo.demo.Web.APIs;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;

import biocampo.demo.Domain.DTO.Request.SaleRequest;
import biocampo.demo.Domain.Services.PagoService;
import biocampo.demo.Domain.Services.SaleService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class PagoController {

    @Autowired
    private PagoService pagoService;
    @Autowired
    private SaleService saleService;

    @PostMapping("/pago")
    public ResponseEntity<?> pago(@RequestBody SaleRequest request) {
        try {
            Preference preference = pagoService.crearPago(request.getSale(), request.getDetails());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("preferenceId", preference.getId()));

        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /*@PostMapping("/webhook/mercadopago")
    public ResponseEntity<String> webhook(@RequestBody Map<String, Object> payload) {
        try {
            // 1. Obtienes el payment_id enviado por Mercado Pago
            String paymentId = (String) payload.get("data_id");

            // 2. Consultas a Mercado Pago para verificar el pago
            Payment payment = pagoService.getPayment(paymentId); // llamas al SDK

            if (payment.getStatus().equals("approved")) {
                // 3. Llamas al método que registra la venta
                saleService.registerSale(saleRequest.getSale(), saleRequest.getDetails());
            }

            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }*/

}
