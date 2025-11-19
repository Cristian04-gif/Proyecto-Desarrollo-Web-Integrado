package biocampo.demo.Web.APIs;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;

import biocampo.demo.Domain.DTO.Request.SaleRequest;
import biocampo.demo.Domain.Model.Sale;
import biocampo.demo.Domain.Model.SaleDetail;
import biocampo.demo.Domain.Services.PagoService;
import biocampo.demo.Domain.Services.SaleService;
import biocampo.demo.Persistance.Entity.DetalleVenta;
import biocampo.demo.Persistance.Entity.Venta;

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

    @PostMapping("/webhook")
public ResponseEntity<String> recibirWebhook(@RequestBody String body) {
    try {
        JsonObject json = JsonParser.parseString(body).getAsJsonObject();

        String type = json.get("type").getAsString();
        Long paymentId = json.getAsJsonObject("data").get("id").getAsLong();

        if (!type.equals("payment")) {
            return ResponseEntity.ok("Ignored");
        }

        // 1. Obtener pago
        PaymentClient client = new PaymentClient()
        Payment payment = client.get(paymentId);

        if (!payment.getStatus().equals("approved")) {
            return ResponseEntity.ok("Payment not approved");
        }

        // 2. Obtener additional_info como JSON crudo
        JsonObject raw = payment.getJsonObject();
        JsonObject additionalInfo = raw.getAsJsonObject("additional_info");

        // 3. Convertir adicionales
        Gson gson = new Gson();

        Sale sale = gson.fromJson(additionalInfo.get("sale"), Sale.class);
        List<SaleDetail> details = gson.fromJson(
                additionalInfo.get("details").getAsJsonArray(),
                new TypeToken<List<SaleDetail>>(){}.getType()
        );

        // 4. Registrar la venta automáticamente
        saleService.registerSale(sale, details);
        return ResponseEntity.ok("Venta registrada");

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body("Error: " + e.getMessage());
    }
}

}
