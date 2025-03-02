package com.microfinance.auth_services.AuthController;

import com.microfinance.auth_services.models.LoginRequest;
import com.microfinance.auth_services.models.LoginResponse;
import com.microfinance.auth_services.token.TokenService;
import com.microfinance.auth_services.service.AuthService;
import com.microfinance.auth_services.service.KeycloakService;
import com.microfinance.auth_services.utils.APIResponse;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowCredentials = "true")
@Tag(name = "Authentication Services", description = "APIs for managing access and authentication to the system")
@RequestMapping("/auth")
@SecurityRequirement(name = "keycloak81")
@SecurityRequirement(name = "local81")
public class AuthController {



    @Autowired
    private AuthService authService;
    @Autowired
    private  TokenService tokenService;


    // Rate limiter storage (per user IP)
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();



    /**
     * Extracts the real client IP address considering proxy headers.
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        } else {
            ip = ip.split(",")[0].trim();  // Extract the first IP from X-Forwarded-For
        }
        return ip;
    }


    /**
     * Creates a bucket for rate limiting per client IP (5 requests per minute).
     */
    private Bucket getBucket(String ip) {
        return cache.computeIfAbsent(ip, k ->
                Bucket.builder()
                        .addLimit(Bandwidth.classic(5, Refill.intervally(5, Duration.ofMinutes(1))))
                        .build()
        );
    }

    private ResponseEntity<APIResponse> handleRateLimit(HttpServletRequest httpRequest) {
        String clientIp = getClientIp(httpRequest);
        Bucket bucket = getBucket(clientIp);

        if (bucket.tryConsume(1)) {
            return null; // Allow request
        } else {
            long waitTime = bucket.getAvailableTokens();
            APIResponse response = new APIResponse(HttpStatus.TOO_MANY_REQUESTS.value(),
                    "Too many requests. Please wait before retrying.",
                    waitTime);
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
        }
    }
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
    public ResponseEntity<APIResponse> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;

        return ResponseEntity.ok(authService.loginCollectorALL(loginRequest, 1));
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
    public ResponseEntity<APIResponse> loginUser(@RequestBody @Valid LoginRequest loginRequest, HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;

        return ResponseEntity.ok(authService.loginUser(loginRequest));
    }




    @Autowired
    private KeycloakService keycloakService;
    @PostMapping("/loginCollector1")
    public ResponseEntity<APIResponse>  loginTest(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;

        LoginResponse token = keycloakService.getToken(request);
        return ResponseEntity.ok(new APIResponse(HttpStatus.OK.value(), "Token generated successfully", token));
    }

    @PostMapping("/getToken")
    public ResponseEntity<APIResponse> getToken(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;

        LoginResponse token = tokenService.generateToken(request);
        return ResponseEntity.ok(new APIResponse(HttpStatus.OK.value(), "Token generated successfully", token));
    }




}
