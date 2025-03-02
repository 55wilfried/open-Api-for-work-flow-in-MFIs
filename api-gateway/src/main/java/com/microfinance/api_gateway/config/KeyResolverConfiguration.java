package com.microfinance.api_gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

    @Configuration
    @Component("KeyResolverConfiguration")
    public class KeyResolverConfiguration implements KeyResolver {

        @Override
        public Mono<String> resolve(ServerWebExchange exchange) {
            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                // Extract the token value (you might consider processing or hashing it if needed)
                String token = authHeader.substring(7);
                return Mono.just(token);
            }

            // Fallback to using the client's IP address if no Authorization header is provided
            return Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress())
                    .getAddress().getHostAddress());
        }
    }

   /* @Bean
    public KeyResolver userKeyResolver(ReactiveJwtDecoder jwtDecoder) {
        return exchange -> ReactiveSecurityContextHolder.getContext()
                .flatMap(securityContext -> {
                    if (securityContext.getAuthentication() != null &&
                            securityContext.getAuthentication().getPrincipal() instanceof org.springframework.security.oauth2.jwt.Jwt) {
                        org.springframework.security.oauth2.jwt.Jwt jwt =
                                (org.springframework.security.oauth2.jwt.Jwt) securityContext.getAuthentication().getPrincipal();
                        return Mono.just(jwt.getSubject()); // Key = User ID from JWT
                    }
                    return Mono.just("anonymous");
                });
    }*/