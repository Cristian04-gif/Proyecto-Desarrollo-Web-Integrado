package biocampo.demo.Web.APIs;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            System.out.println("error pago: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> recibirWebhook(@RequestBody String json) {
        System.out.println("📩 Webhook recibido: " + json);

        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();

        // 1. Si viene "topic"
        if (obj.has("topic")) {
            String topic = obj.get("topic").getAsString();

            // --- Webhook MERCHANT ORDER ---
            if (topic.equals("merchant_order")) {
                if (obj.has("resource")) {
                    String resourceUrl = obj.get("resource").getAsString();
                    System.out.println("🧾 Merchant Order URL: " + resourceUrl);
                } else {
                    System.out.println("⚠ 'merchant_order' sin resource");
                }
                return ResponseEntity.ok("OK");
            }

            // --- Webhook PAYMENT VÍA TOPIC ---
            if (topic.equals("payment")) {
                String paymentId = obj.get("resource").getAsString();
                System.out.println("💰 Payment ID: " + paymentId);
                return ResponseEntity.ok("OK");
            }
        }

        // 2. Webhook VÍA TYPE
        if (obj.has("type") && obj.get("type").getAsString().equals("payment")) {
            JsonObject dataObj = obj.getAsJsonObject("data");
            if (dataObj != null && dataObj.has("id")) {
                String paymentId = dataObj.get("id").getAsString();
                System.out.println("💳 Payment (type webhook) ID: " + paymentId);

                try {
                    PaymentClient client = new PaymentClient();
                    Payment payment = client.get(Long.parseLong(paymentId));

                    // 👍 Leer directamente los campos personalizados del mapa
                    Map<String, Object> metadata = payment.getMetadata();

                    Gson gson = new Gson();

                    // Extrae solo lo nuestro
                    String saleJson = gson.toJson(metadata.get("sale"));
                    String detailsJson = gson.toJson(metadata.get("details"));

                    // Convierte solo esas partes
                    Sale sale = gson.fromJson(saleJson, Sale.class);
                    List<SaleDetail> details = gson.fromJson(
                            detailsJson,
                            new TypeToken<List<SaleDetail>>() {
                            }.getType());

                    saleService.registerSale(sale, details);

                    return ResponseEntity.ok("Venta registrada");

                } catch (Exception e) {
                    System.out.println("Error en el registro de la venta: " + e.getMessage());
                    e.printStackTrace();
                    return ResponseEntity.status(500).body("Error: " + e.getMessage());
                }

            } else {
                System.out.println("⚠ Webhook payment sin data.id");
            }
            return ResponseEntity.ok("OK");
        }

        System.out.println("⚠ Webhook NO RECONOCIDO");
        return ResponseEntity.ok("IGNORED");
    }

}
