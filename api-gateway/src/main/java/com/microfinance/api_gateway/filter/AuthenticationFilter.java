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

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

       @Autowired
   private RestTemplate template;
    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }
 @Autowired
    private Helpers helper;

    @Autowired
    private JwtUtil jwtService;

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                // Authentication logic for secured endpoints
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return helper.handleErrorResponse(exchange, "Missing Authorization header", HttpStatus.UNAUTHORIZED);
                }

                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                    try {
                        jwtUtil.validateToken(authHeader);
                    } catch (Exception e) {
                        return helper.handleErrorResponse(exchange, generateToken("C010 ") , HttpStatus.UNAUTHORIZED);
                    }
                } else {
                    return helper.handleErrorResponse(exchange, "Invalid token format", HttpStatus.BAD_REQUEST);
                }
            }
            return chain.filter(exchange);  // Allow request to continue for non-secure endpoints like Swagger UI
        };
    }


    public static class Config {

    }
}
