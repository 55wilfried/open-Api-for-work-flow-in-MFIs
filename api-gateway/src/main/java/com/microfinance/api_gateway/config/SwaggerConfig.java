package com.microfinance.api_gateway.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


/*@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "API Gateway",
                version = "1.0",
                description = "API Gateway for Microservices"
        ),
        security = @SecurityRequirement(name = "keycloak80"),
        servers = {
                @io.swagger.v3.oas.annotations.servers.Server(url = "http://localhost:8080", description = "MFI Clients Services")
        }
)
@SecurityScheme(
        name = "keycloak",
        type = SecuritySchemeType.OAUTH2,
        scheme = "bearer",
        bearerFormat = "JWT",
       flows = @io.swagger.v3.oas.annotations.security.OAuthFlows(
                authorizationCode = @io.swagger.v3.oas.annotations.security.OAuthFlow(
                        authorizationUrl = "http://localhost:8180/realms/microfinance-realm/protocol/openid-connect/auth",
                        tokenUrl = "http://localhost:8180/realms/microfinance-realm/protocol/openid-connect/token"
                )
        )
)*/
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API Gateway")
                        .version("1.0")
                        .description("API Gateway for Microservices"))
                .servers(List.of(new Server().url("http://localhost:8080").description("API Gateway Server")));
    }

}


