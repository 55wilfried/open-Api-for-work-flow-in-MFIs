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

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                // Header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return helper.handleErrorResponse(exchange, "Missing Authorization header", HttpStatus.UNAUTHORIZED);
                }

                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);

                    try {
                        jwtUtil.validateToken(authHeader);
                    } catch (Exception e) {
                        return helper.handleErrorResponse(exchange, "Unauthorized access to application", HttpStatus.UNAUTHORIZED);
                    }
                } else {
                    return helper.handleErrorResponse(exchange, "Invalid token format", HttpStatus.BAD_REQUEST);
                }
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {

    }
}
