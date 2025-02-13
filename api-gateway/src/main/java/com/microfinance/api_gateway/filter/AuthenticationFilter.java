package com.microfinance.api_gateway.filter;

import com.microfinance.api_gateway.helpers.Helpers;
import com.microfinance.api_gateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Autowired
    private RouteValidator validator;

    @Autowired
    private RestTemplate template;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private Helpers helper;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String requestPath = exchange.getRequest().getPath().toString();
            logger.info("Incoming request to API Gateway: {}", requestPath); // Log full request info


            // Check if the request is for a secured endpoint
            if (validator.isSecured.test(exchange.getRequest())) {
                logger.info("Request requires authentication: {}", requestPath);

                // Log authorization header
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    logger.error("Missing Authorization header for request: {}", requestPath);
                    return helper.handleErrorResponse(exchange, "Missing Authorization header", HttpStatus.UNAUTHORIZED);
                }

                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                logger.info("Authorization header: {}", authHeader); // Log header details

                // Process JWT token
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    String token = authHeader.substring(7);
                    try {
                        logger.info("Validating token for request: {}", requestPath); // Log token validation attempt
                        jwtUtil.validateToken(token);
                        logger.info("JWT token validated successfully for request: {}", requestPath);
                    } catch (Exception e) {
                        logger.error("JWT validation failed for request {}: {}", requestPath, e.getMessage());
                        return helper.handleErrorResponse(exchange, "Invalid token: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
                    }
                } else {
                    logger.error("Invalid token format for request: {}", requestPath);
                    return helper.handleErrorResponse(exchange, "Invalid token format", HttpStatus.BAD_REQUEST);
                }
            } else {
                logger.info("Public endpoint, no authentication required for request: {}", requestPath);
            }

            // Log request routing details before continuing
            logger.info("Routing request to the next filter: {}", requestPath);
            return chain.filter(exchange); // Continue to the next filter
        };
    }

    public static class Config {
    }
}
