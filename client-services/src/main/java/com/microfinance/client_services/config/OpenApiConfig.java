package com.microfinance.client_services.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Authentication Services",
                version = "1.0",
                description = "API documentation for Service Name"
        )
)
public class OpenApiConfig {
}
