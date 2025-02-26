package com.microfinance.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;

@Configuration
public class RateLimiterConfig {

    @Bean
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
    }
}

