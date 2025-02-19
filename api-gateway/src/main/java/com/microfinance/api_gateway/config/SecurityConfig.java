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
                    .pathMatchers("/auth/**").permitAll()  // Allow authentication requests
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


