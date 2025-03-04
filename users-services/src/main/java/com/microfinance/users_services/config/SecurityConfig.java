package com.microfinance.users_services.config;

import com.microfinance.users_services.token.FallbackJwtDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**","/actuator/**" , "/v3/api-docs/**", "/users/getToken").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())) // Enable JWT authentication
                );

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Collection<GrantedAuthority> authorities = extractAuthorities(jwt);
            // Block users with ROLE_TEST
            if (authorities.contains(new SimpleGrantedAuthority("ROLE_TEST"))) {
                throw new SecurityException("Access denied: Users with ROLE_TEST cannot access this service.");
            }
            return authorities;
        });
        return converter;
    }

    private Collection<GrantedAuthority> extractAuthorities(org.springframework.security.oauth2.jwt.Jwt jwt) {
        JwtGrantedAuthoritiesConverter defaultConverter = new JwtGrantedAuthoritiesConverter();
        Collection<GrantedAuthority> defaultAuthorities = defaultConverter.convert(jwt);
        List<String> roles = jwt.getClaimAsStringList("roles");
        if (roles != null) {
            List<GrantedAuthority> roleAuthorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                    .collect(Collectors.toList());
            defaultAuthorities.addAll(roleAuthorities);
        }
        return defaultAuthorities;
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        logger.info("Configuring JWT  client services...");
        // Primary decoder using Keycloak's JWKS endpoint
        JwtDecoder keycloakDecoder = NimbusJwtDecoder
                .withJwkSetUri("http://keycloak:8080/realms/microfinance-realm/protocol/openid-connect/certs")
                .build();

        logger.info("Configuring JWT FAILED GO TO LOCAL  client services...");

        // Fallback decoder using a local secret key
        String fallbackSecret = "fallback-secret-key-which-is-very-secure";
        SecretKey fallbackKey = new SecretKeySpec(fallbackSecret.getBytes(), "HMACSHA256");
        JwtDecoder localDecoder = NimbusJwtDecoder.withSecretKey(fallbackKey).build();

        return new FallbackJwtDecoder(keycloakDecoder, localDecoder);
    }

   /* @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri("http://keycloak:8080/realms/microfinance-realm/protocol/openid-connect/certs").build();
    }*/


}


