package biocampo.demo.Domain.DTO.Config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import biocampo.demo.Domain.DTO.JWT.JwtAutenticationFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAutenticationFilter jwtAutenticationFilter;
        private final AuthenticationProvider authProvider;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
<<<<<<< HEAD
                                                // Permitir preflight CORS
                                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                                // Permitir login/register
                                                .requestMatchers("/auth/**").permitAll()
                                                // Requerir autenticación para APIs
=======
                                                // Permitir TODOS los OPTIONS sin autenticación (CORS preflight)
                                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                                // Permitir /auth/** sin autenticación
                                                .requestMatchers("/auth/**").permitAll()
                                                // Requerir autenticación para /api/**
>>>>>>> 820fd712dfbb147e9f07f536cad78d77574f028b
                                                .requestMatchers("/api/**").authenticated()
                                                // Denegar todo lo demás
                                                .anyRequest().denyAll())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authProvider)
                                .addFilterBefore(jwtAutenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration config = new CorsConfiguration();
<<<<<<< HEAD
                config.setAllowedOrigins(List.of(
                                "http://localhost:3000",
                                "http://127.0.0.1:3000",
                                "http://localhost:5173",
                                "http://127.0.0.1:5173"));
                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD"));
                config.setAllowedHeaders(List.of("*"));
                config.setExposedHeaders(List.of(
                                "Authorization",
                                "Content-Type",
                                "X-Requested-With",
                                "Access-Control-Allow-Origin",
                                "Access-Control-Allow-Credentials"));
=======
                config.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:3000",
                                "http://127.0.0.1:5173", "http://127.0.0.1:3000"));
                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD"));
                config.setAllowedHeaders(List.of("*"));
                config.setExposedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
>>>>>>> 820fd712dfbb147e9f07f536cad78d77574f028b
                config.setAllowCredentials(true);
                config.setMaxAge(3600L);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", config);
                return source;
        }
}
