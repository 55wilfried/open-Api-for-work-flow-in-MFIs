package com.microfinance.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;

@Configuration
public class RateLimiterConfig {



    @Bean
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
    }
}


 /* @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.just(getUserKey(exchange));
    }

    private String getUserKey(ServerWebExchange exchange) {
        // Extract API key or JWT Subject for rate limiting
        return exchange.getRequest()
                .getHeaders()
                .getFirst("Authorization") != null ?
                exchange.getRequest().getHeaders().getFirst("Authorization") :
                exchange.getRequest().getRemoteAddress().toString();
    }*/
