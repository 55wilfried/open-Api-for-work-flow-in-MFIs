package com.microfinance.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/*@Configuration
@EnableWebSecurity*/
public class SecurityConfig {
  /*  @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers("/login", "/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll() // Allow login and Swagger UI without authentication
                .anyRequest().authenticated() // Secure other endpoints
                .and()
                .formLogin() // Enable form-based login
                .loginPage("/login") // Custom login page URL
                .permitAll()
                .and()
                .csrf().disable(); // Disable CSRF if necessary

        return http.build();
    }*/
}
