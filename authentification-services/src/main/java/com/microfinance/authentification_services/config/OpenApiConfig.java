package com.microfinance.authentification_services.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Authentification Services",
                version = "1.0",
                description = "API For Authentificate User"
        ),
        servers = {
                @Server(url = "http://localhost:8081", description = "Authentification Service")
        }
)
public class OpenApiConfig {
}
