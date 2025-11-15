package biocampo.demo.Domain.DTO.Config;

import org.springframework.context.annotation.Configuration;

/**
 * CORS está configurado centralmente en SecurityConfig.java
 * Esta clase está aquí por referencia histórica pero CORS se maneja desde
 * SecurityConfig
 */
@Configuration
@Deprecated
public class CorsConfig {
    // CORS configuration moved to SecurityConfig.java
}
