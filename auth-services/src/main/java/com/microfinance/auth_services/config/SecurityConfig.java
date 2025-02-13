package com.microfinance.auth_services.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/loginCollector",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/configuration/ui",
                                "/configuration/security",
                                "/webjars/**"
                        ).permitAll() // ✅ Allow public access to Swagger and login
                        .anyRequest().authenticated() // 🔒 Other requests need authentication
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Ensure stateless API
                )
                .formLogin(login -> login.disable()) // ❌ Disable default login form
                .httpBasic(basic -> basic.disable()) // ❌ Disable basic authentication
                .build();
    }



   /* @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/register").permitAll() // Allow login & registration
                        .requestMatchers("/swagger-ui/**","/auth/loginCollector/**","/v3/api-docs/**", "/swagger-resources/**","/webjars/**","/configuration/ui" ).authenticated() // Require login for Swagger
                        .anyRequest().authenticated() // Protect all other endpoints
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Use stateless sessions
                .formLogin(form -> form
                        .loginPage("/login") // Specify login form URL
                        .permitAll()
                )
                .logout(logout -> logout.logoutUrl("/logout").permitAll()) // Enable logout
                .build();
    }*/


}
