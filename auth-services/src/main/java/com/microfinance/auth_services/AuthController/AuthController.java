package com.microfinance.auth_services.AuthController;

import com.microfinance.auth_services.dto.LoginRequest;
import com.microfinance.auth_services.dto.LoginResponse;
import com.microfinance.auth_services.service.AuthService;
import com.microfinance.auth_services.service.KeycloakService;
import com.microfinance.auth_services.utils.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowCredentials = "true")
@Tag(name = "Authentication Services", description = "APIs for managing access and authentication to the system")
@RequestMapping("/auth")
@SecurityRequirement(name = "keycloak81")
public class AuthController {



    @Autowired
    private AuthService authService;

    @Operation(
            summary = "Login as a collector",
            description = "Authenticate a collector using their username and password",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login successful",
                            content = @Content(schema = @Schema(implementation = APIResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid credentials")
            }
    )
    @PostMapping("/loginCollector")
    public APIResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.loginCollectorALL(loginRequest, 1);
    }

    @Operation(
            summary = "Login as a user",
            description = "Authenticate a regular user using their username and password",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login successful",
                            content = @Content(schema = @Schema(implementation = APIResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid credentials")
            }
    )
    @PostMapping("/loginUser")
    public APIResponse loginUser(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.loginUser(loginRequest);
    }




    @Autowired
    private KeycloakService keycloakService;
    @PostMapping("/loginCollector1")
    public LoginResponse loginTest(@RequestBody LoginRequest request) {
        return keycloakService.getToken(request);
    }




}
