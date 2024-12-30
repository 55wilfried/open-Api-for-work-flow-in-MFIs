package com.microfinance.client_services.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Clients Services",
                version = "1.0",
                description = "API documentation for the MFI Clients Service "
        ),
        servers = {
                @Server(url = "http://localhost:8082", description = "MFI Clients Services")
        }
)
public class OpenApiConfig {
}
