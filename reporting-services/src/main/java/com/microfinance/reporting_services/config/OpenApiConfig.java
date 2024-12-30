package com.microfinance.reporting_services.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Reporting Services",
                version = "1.0",
                description = "API documentation for reporting services"
        ),
        servers = {
                @Server(url = "http://localhost:8085", description = "Reporting Services")
        }
)
public class OpenApiConfig {
}
