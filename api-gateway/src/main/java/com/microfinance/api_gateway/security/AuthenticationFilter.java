package com.microfinance.api_gateway.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements WebFilter {

    private final ReactiveJwtDecoder jwtDecoder;

    public AuthenticationFilter(ReactiveJwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // Allow API documentation paths for all services (swagger-ui, v3/api-docs)
        if (request.getURI().getPath().startsWith("/auth/") ||
                request.getURI().getPath().startsWith("/swagger-ui.html") ||
                request.getURI().getPath().startsWith("/v3/api-docs/") ||
                request.getURI().getPath().startsWith("/swagger-ui/**") ||
                request.getURI().getPath().startsWith("/client/v3/api-docs") ||
                request.getURI().getPath().startsWith("/users/v3/api-docs") ||
                request.getURI().getPath().startsWith("/transactions/v3/api-docs") ||
                request.getURI().getPath().startsWith("/reports/v3/api-docs") ||
                request.getURI().getPath().startsWith("/loan/v3/api-docs")) {
            return chain.filter(exchange);  // Allow all the swagger doc requests
        }


        // Validate JWT Token for all other requests
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorizedResponse(exchange);
        }

        String token = authHeader.substring(7);
        return jwtDecoder.decode(token)
                .flatMap(jwt -> chain.filter(exchange))
                .onErrorResume(JwtException.class, e -> unauthorizedResponse(exchange));
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }
}
