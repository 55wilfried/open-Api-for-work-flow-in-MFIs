package com.microfinance.users_services.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "users-services",
                version = "1.0",
                description = "API for User "
        ),
        security = {
                @SecurityRequirement(name = "keycloak83"),
                @SecurityRequirement(name = "local83")
        },
        servers = {
                @Server(url = "/", description = "User Services")
        }
)
@SecurityScheme(
        name = "keycloak83",
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
@SecurityScheme(
        name = "local83",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        paramName = "Authorization"
)
@Configuration
public class SwaggerConfig {
}
