package com.microfinance.auth_services.AuthController;

import com.microfinance.auth_services.dto.LoginRequest;
import com.microfinance.auth_services.dto.UserApi;
import com.microfinance.auth_services.repository.UserApiRepository;
import com.microfinance.auth_services.service.AuthService;
import com.microfinance.auth_services.utils.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowCredentials = "true")
@Tag(name = "Authentication Services", description = "APIs for managing access and authentication to the system")
@RequestMapping("/auth")
public class AuthController {

    private final UserApiRepository userApiRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserApiRepository userApiRepository, PasswordEncoder passwordEncoder) {
        this.userApiRepository = userApiRepository;
        this.passwordEncoder = passwordEncoder;
    }

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
        return authService.loginCollector(loginRequest);
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

    @Operation(
            summary = "Register a new user",
            description = "Register a new user with encrypted password",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User registered successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request")
            }
    )
    @PostMapping("/register")
    public String registerUser(@RequestBody UserApi user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        userApiRepository.save(user);
        return "User registered successfully!";
    }

    @Operation(
            summary = "Login with username and password",
            description = "Authenticate a user by validating their credentials",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login successful"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials")
            }
    )
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        Optional<UserApi> userOpt = userApiRepository.findByUsername(username);

        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
            return "Login successful!";
        } else {
            return "Invalid credentials!";
        }
    }
}
