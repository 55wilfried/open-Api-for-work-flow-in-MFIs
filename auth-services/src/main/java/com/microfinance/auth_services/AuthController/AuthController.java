package com.microfinance.auth_services.AuthController;


import com.microfinance.auth_services.dto.LoginRequest;
import com.microfinance.auth_services.service.AuthService;
import com.microfinance.auth_services.utils.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowCredentials = "true")
@Tag(name = "Authentication Services", description = "APIs for managing access and authentication to the system")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Handles login requests for collectors.
     *
     * @param loginRequest A JSON object containing login credentials.
     * @return A standardized API response containing the authentication status and user details.
     */
    @Operation(
            summary = "Login collector",
            description = "Authenticates a collector using their credentials and provides access tokens upon successful login."
    )
    @PostMapping("/login")
    public APIResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.loginCollector(loginRequest);
    }

    /**
     * Handles login requests for general users.
     *
     * @param loginRequest A JSON object containing login credentials.
     * @return A standardized API response containing the authentication status and user details.
     */
    @Operation(
            summary = "Login user",
            description = "Authenticates a user using their credentials and provides access tokens upon successful login."
    )
    @PostMapping("/loginUser")
    public APIResponse loginUser(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.loginUser(loginRequest);
    }
}
