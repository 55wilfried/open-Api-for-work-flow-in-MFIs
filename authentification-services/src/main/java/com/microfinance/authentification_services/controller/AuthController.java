package com.microfinance.authentification_services.controller;

import com.microfinance.authentification_services.dto.LoginRequest;
import com.microfinance.authentification_services.service.AuthService;
import com.microfinance.authentification_services.utils.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"*"}, maxAge = 3600, allowCredentials = "false")
@Tag(name = "Authentication Services", description = "APIs for managing access and authentication to the system")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Handles login requests for collectors.
     *
     * @param client A JSON object containing login credentials.
     * @return A standardized API response containing the authentication status and user details.
     */
    @Operation(
            summary = "Login collector",
            description = "Authenticates a collector using their credentials and provides access tokens upon successful login."
    )
    @PostMapping("/login")
    public APIResponse login(@RequestBody @Valid String client) {
        System.out.println("Login method called");
        return this.service.loginCollector(client);
    }

    /**
     * Handles login requests for general users.
     *
     * @param client A JSON object containing login credentials.
     * @return A standardized API response containing the authentication status and user details.
     */
    @Operation(
            summary = "Login User",
            description = "Authenticates a user using their credentials and provides access tokens upon successful login."
    )
    @PostMapping("/loginUser")
    public APIResponse loginUser(@RequestBody  String client) {
        System.out.println("LoginUser method called");
        return this.service.loginUser(client);
    }
}
