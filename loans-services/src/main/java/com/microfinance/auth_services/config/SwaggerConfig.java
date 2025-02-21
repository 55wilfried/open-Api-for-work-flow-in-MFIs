package com.microfinance.auth_services.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Loans Services",
                version = "1.0",
                description = "API documentation for Loan services"
        ),
        security = @SecurityRequirement(name = "keycloak86"),
        servers = {
                @Server(url = "/", description = "Loan Services")
        }
)
@SecurityScheme(
        name = "keycloak86",
        type = SecuritySchemeType.OAUTH2,
        scheme = "bearer",
        bearerFormat = "JWT",
        flows = @io.swagger.v3.oas.annotations.security.OAuthFlows(
                authorizationCode = @io.swagger.v3.oas.annotations.security.OAuthFlow(
                        authorizationUrl = "http://localhost:8180/realms/microfinance-realm/protocol/openid-connect/auth",
                        tokenUrl = "http://localhost:8180/realms/microfinance-realm/protocol/openid-connect/token"
                )
        )
)
@Configuration
public class SwaggerConfig {
}
