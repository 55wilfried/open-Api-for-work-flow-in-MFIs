package com.microfinance.api_gateway.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    private final ReactiveJwtDecoder jwtDecoder;

    public AuthenticationFilter(ReactiveJwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        logger.info("Incoming request: {} {}", request.getMethod(), request.getURI());

        if (request.getURI().getPath().startsWith("/auth/") || request.getURI().getPath().startsWith("/client/getToken") ||
                request.getURI().getPath().startsWith("/users/getToken") ||
                request.getURI().getPath().startsWith("/swagger-ui.html") ||
                request.getURI().getPath().startsWith("/v3/api-docs/") ||
                request.getURI().getPath().startsWith("/swagger-ui/") ||
                request.getURI().getPath().startsWith("/webjars/") ||  // <-- Add this line
                request.getURI().getPath().startsWith("/auth/v3/api-docs") ||
                request.getURI().getPath().startsWith("/client/v3/api-docs") ||
                request.getURI().getPath().startsWith("/users/v3/api-docs") ||
                request.getURI().getPath().startsWith("/transactions/v3/api-docs") ||
                request.getURI().getPath().startsWith("/reports/v3/api-docs") ||
                request.getURI().getPath().startsWith("/loan/v3/api-docs")) {
            logger.info("Request permitted without authentication: {}", request.getURI().getPath());
            return chain.filter(exchange);
        }

        // Validate JWT Token for all other requests
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("Unauthorized request - Missing or invalid Authorization header");
            return unauthorizedResponse(exchange);
        }

        String token = authHeader.substring(7);
        logger.info("Validating JWT token: {}", token);

        return jwtDecoder.decode(token)
                .flatMap(jwt -> {
                    logger.info("JWT validated successfully: {}", jwt.getSubject());
                    return chain.filter(exchange);
                })
                .onErrorResume(JwtException.class, e -> {
                    logger.error("JWT validation failed: {}", e.getMessage());
                    return unauthorizedResponse(exchange);
                });
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        logger.warn("Responding with 401 Unauthorized");
        return response.setComplete();
    }
}

