package com.microfinance.auth_services.config;

import com.microfinance.auth_services.token.FallbackJwtDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authorization.AuthorizationDecision;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring Security for Auth services...");
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**","/actuator/**" ).permitAll() // Publicly accessible login endpoints
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**")
                        .access((authentication, context) -> {
                            String forwardedFromGateway = context.getRequest().getHeader("X-Forwarded-For");
                            return forwardedFromGateway != null
                                    ? new AuthorizationDecision(true) // Allow when coming from API Gateway
                                    : new AuthorizationDecision(false); // Deny otherwise
                        })
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt());

        logger.info("Security Config Loaded Successfully auth !");

        return http.build();
    }

    /*@Bean
    public JwtDecoder jwtDecoder() {
        logger.info("Loading JWT Decoder...");
        return NimbusJwtDecoder.withJwkSetUri("http://keycloak:8080/realms/microfinance-realm/protocol/openid-connect/certs").build();
    }*/

    @Bean
    public JwtDecoder jwtDecoder() {
        logger.info("Configuring JWT  client services...");
        // Primary decoder using Keycloak's JWKS endpoint
        JwtDecoder keycloakDecoder = NimbusJwtDecoder
                .withJwkSetUri("http://keycloak:8080/realms/microfinance-realm/protocol/openid-connect/certs")
                .build();

        logger.info("Configuring JWT FAILED GO TO LOCAL  client services...");

        // Fallback decoder using a local secret key
        String fallbackSecret = "fallback-secret-key-which-is-very-secure";
        SecretKey fallbackKey = new SecretKeySpec(fallbackSecret.getBytes(), "HMACSHA256");
        JwtDecoder localDecoder = NimbusJwtDecoder.withSecretKey(fallbackKey).build();

        return new FallbackJwtDecoder(keycloakDecoder, localDecoder);
    }
}
