package com.microfinance.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeExchange()
                .pathMatchers("/auth/**", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/webjars/**")
                .permitAll() // Allow swagger UI and documentation access without JWT
                .pathMatchers("/client/v3/api-docs/**", "/users/v3/api-docs/**", "/transactions/v3/api-docs/**",
                        "/reports/v3/api-docs/**", "/loan/v3/api-docs/**")
                .permitAll() // Allow Swagger docs for other services
                .anyExchange().authenticated()  // Secure all other routes
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }



    //yes
    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoder() {
        return NimbusReactiveJwtDecoder.withJwkSetUri("http://localhost:8180/realms/microfinance-realm/protocol/openid-connect/certs").build();
    }
}


