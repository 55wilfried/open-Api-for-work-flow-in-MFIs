package com.microfinance.api_gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    private static final Logger logger = LoggerFactory.getLogger(RouteValidator.class);

    private static final List<String> openApiEndpoints = List.of(
            "/auth/loginCollector",
            "/auth/loginUser",
            "/eureka",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/swagger-ui"
    );

    public Predicate<ServerHttpRequest> isSecured = request -> {
        String requestPath = request.getURI().getPath();
        boolean isPublic = openApiEndpoints.stream().anyMatch(requestPath::startsWith);
        if (isPublic) {
            logger.info("Public API access allowed: {}", requestPath); // Log if it's a public route
        } else {
            logger.info("Secured API, authentication required: {}", requestPath); // Log if it's a secured route
        }
        return !isPublic;
    };
}
