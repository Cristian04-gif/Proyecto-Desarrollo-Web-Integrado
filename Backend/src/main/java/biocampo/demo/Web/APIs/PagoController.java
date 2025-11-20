package biocampo.demo.Web.APIs;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.mercadopago.client.payment.PaymentClient;

import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;

import biocampo.demo.Domain.DTO.Request.SaleRequest;

import biocampo.demo.Domain.Model.SaleDetail;
import biocampo.demo.Domain.Services.PagoService;
import biocampo.demo.Domain.Services.SaleService;

import java.util.List;
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
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/pago")
    public ResponseEntity<?> pago(@RequestBody SaleRequest request) {
        try {
            Preference preference = pagoService.crearPago(request.getEmail(), request.getDetails());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("preferenceId", preference.getId()));

        } catch (Exception e) {
            System.out.println("error pago: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> recibirWebhook(@RequestBody String jsonBody) {

        try {
            JsonNode root = objectMapper.readTree(jsonBody);

            if (!"payment".equals(root.path("type").asText())) {
                return ResponseEntity.ok("IGNORED");
            }

            long paymentId = root.path("data").path("id").asLong();

            Payment payment = new PaymentClient().get(paymentId);
            System.out.println("📦 Metadata recibido: " + payment.getMetadata());

            if (!"approved".equalsIgnoreCase(payment.getStatus())) {
                return ResponseEntity.ok("PAGO NO APROBADO");
            }

            Map<String, Object> metadata = payment.getMetadata();
            String emailUser = (String) metadata.get("email");
            String jsonDetails = (String) metadata.get("details");

            List<SaleDetail> details = objectMapper.readValue(
                    jsonDetails,
                    new TypeReference<List<SaleDetail>>() {
                    });

            saleService.registerSale(emailUser, details);

            return ResponseEntity.ok("VENTA_REGISTRADA");

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(500).body("ERROR: " + ex.getMessage());
        }
    }

}
