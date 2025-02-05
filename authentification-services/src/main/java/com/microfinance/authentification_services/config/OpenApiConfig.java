package com.microfinance.authentification_services.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "authentification-services",
                version = "1.0",
                description = "API for Authenticating Users"
        ),
        servers = @Server(url = "http://localhost:8081", description = "Authentification services ")
)
public class OpenApiConfig {
}
