package com.microfinance.users_services.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "users-services",
                version = "1.0",
                description = "API for User "
        ),
        servers = {
                @Server(url = "http://localhost:8083", description = "User Services")
        }
)
public class OpenApiConfig {
}
