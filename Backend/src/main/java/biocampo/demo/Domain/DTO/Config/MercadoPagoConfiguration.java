package biocampo.demo.Domain.DTO.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



@Component
public class MercadoPagoConfiguration {
    @Value("${mercadopago.access.token}")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    
}
