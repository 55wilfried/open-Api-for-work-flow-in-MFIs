package com.microfinance.api_gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomRoutingFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(CustomRoutingFilter.class);

    // Map URL prefix to service IDs (registered in Eureka and referenced in your YAML)
    private static final Map<String, String> SERVICE_MAPPING = new HashMap<>();
    static {
        SERVICE_MAPPING.put("client", "CLIENT-SERVICE");
        SERVICE_MAPPING.put("users", "USERS-SERVICES");
        SERVICE_MAPPING.put("transactions", "TRANSACTION-SERVICES");
        SERVICE_MAPPING.put("reports", "REPORTING-SERVICES");
        SERVICE_MAPPING.put("loan", "LOAN-SERVICE");
        SERVICE_MAPPING.put("auth", "AUTH-SERVICE");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String originalPath = exchange.getRequest().getURI().getPath();
        log.info("CustomRoutingFilter processing request: {}", originalPath);

        // Bypass custom routing for Swagger UI and API docs endpoints
        if (originalPath.startsWith("/swagger-ui.html")
                || originalPath.startsWith("/swagger-ui")
                || originalPath.startsWith("/webjars")
                || originalPath.startsWith("/v3/api-docs")) {
            log.info("Swagger or API docs endpoint detected, bypassing custom routing filter");
            return chain.filter(exchange);
        }

        // Expecting a URI structure like: /{servicePrefix}/remaining/path/...
        String[] segments = originalPath.split("/");
        if (segments.length > 1) {
            String prefix = segments[1];
            String serviceId = SERVICE_MAPPING.get(prefix);

            if (serviceId != null) {
                // Remove the prefix from the path so that the internal service sees its expected endpoint.
                String newPath = originalPath.substring(prefix.length() + 1);
                if (newPath.isEmpty()) {
                    newPath = "/";
                }
                log.info("Routing to service '{}' with rewritten path: {}", serviceId, newPath);

                // Mutate the request with the new path.
                ServerHttpRequest newRequest = exchange.getRequest().mutate().path(newPath).build();
                exchange = exchange.mutate().request(newRequest).build();

                // Optionally, add a header to indicate the original prefix (useful for downstream logging)
                exchange.getRequest().mutate().header("X-Forwarded-Prefix", "/" + prefix);
                // Set an attribute with the target service if needed further downstream
                exchange.getAttributes().put("TARGET_SERVICE", serviceId);
            } else {
                log.warn("No service mapping found for prefix: {}", prefix);
            }
        }

        return chain.filter(exchange);
    }

    // Set order so that this filter runs after authentication and security filters.
    @Override
    public int getOrder() {
        return 1; // Adjust as needed relative to other filters.
    }
}
