package com.microfinance.transaction_services.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Transaction Service",
                version = "v1",
                description = "API for transaction services"
        ),
        servers = {
                @Server(url = "http://localhost:8084", description = "Transaction Service")
        }
)
public class OpenApiConfig {
}
