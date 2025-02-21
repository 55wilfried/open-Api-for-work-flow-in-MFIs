package com.microfinance.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.URI;

@Configuration
public class SwaggerUiRedirectConfiguration {

    @Bean
    public RouterFunction<ServerResponse> swaggerUiRedirect() {
        return RouterFunctions.route(
                RequestPredicates.GET("/swagger-ui/index.html"),
                request -> ServerResponse.status(HttpStatus.PERMANENT_REDIRECT)
                        .location(URI.create("/swagger-ui.html"))
                        .build());
    }
}
