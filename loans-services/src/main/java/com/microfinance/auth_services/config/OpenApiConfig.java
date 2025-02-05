package com.microfinance.auth_services.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Loans Services",
                version = "1.0",
                description = "API documentation for Loan services"
        ),
        servers = {
                @Server(url = "http://localhost:8086", description = "Loan Services")
        }
)
public class OpenApiConfig {
}
