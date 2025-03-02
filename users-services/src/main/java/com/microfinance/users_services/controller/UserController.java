package com.microfinance.users_services.controller;

import com.microfinance.users_services.models.CollectUser;
import com.microfinance.users_services.models.LoginRequest;
import com.microfinance.users_services.models.LoginResponse;
import com.microfinance.users_services.service.UserServices;
import com.microfinance.users_services.token.KeycloakTokenClient;
import com.microfinance.users_services.utils.APIResponse;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import com.microfinance.users_services.token.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/users")
//@CrossOrigin(origins = {"*"}, maxAge = 3600, allowCredentials = "true")
//@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowCredentials = "true")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "User Management Services", description = "APIs for managing Collect Users and Collectors")
@SecurityRequirement(name = "keycloak83")
@SecurityRequirement(name = "local83")
public class UserController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private KeycloakTokenClient keycloakService;

    @Autowired
    private final TokenService tokenService;

    public UserController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

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

    /**
     * Add a new collector.
     *
     * @param collecteur JSON string representing the collector details.
     * @return APIResponse containing the result of the operation.
     */
    @PostMapping("/addCollector")
    public ResponseEntity<APIResponse> addCollector(@RequestBody @Valid String collecteur, HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;

        return ResponseEntity.ok(userServices.addCollector(collecteur));
    }

    /**
     * Add a new collect user.
     *
     * @param collecteur CollectUser object containing user details.
     * @return The created CollectUser object.
     */
    @Operation(summary = "Add User", description = "Add a new user for collection purposes.")
    @PostMapping("/addUserCollect")
    public ResponseEntity<APIResponse> addUserCollect(@RequestBody @Valid CollectUser collecteur, HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;

        return ResponseEntity.ok(new APIResponse(HttpStatus.OK.value(), "User added successfully", userServices.addCollectUser(collecteur)));
    }

    /**
     * Update an existing collector.
     *
     * @param num        The ID of the collector to update.
     * @param collecteur JSON string with updated collector details.
     * @return APIResponse containing the result of the update operation.
     */
    @Operation(summary = "Update Collector", description = "Update the details of an existing collector.")
    @PutMapping("/updateCollector/{num}")
    public ResponseEntity<APIResponse> updateCollector(@PathVariable String num, @RequestBody @Valid String collecteur, HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;

        return ResponseEntity.ok(userServices.updateCollector(num, collecteur));
    }

    /**
     * Update an existing user.
     *
     * @param userName   The username of the user to update.
     * @param collectUser JSON string with updated user details.
     * @return APIResponse containing the result of the update operation.
     */
    @Operation(summary = "Update User", description = "Update the details of an existing collect user.")
    @PutMapping("/updateUserCollect/{userName}")
    public ResponseEntity<APIResponse> updateUserCollect(@PathVariable String userName, @RequestBody @Valid String collectUser, HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;

        return ResponseEntity.ok(userServices.updateUserCollect(userName, collectUser));
    }


    /**
     * Find a user by username.
     *
     * @param userName The username of the user to retrieve.
     * @return APIResponse containing the user details.
     */
    @Operation(summary = "Find User by Username", description = "Retrieve user details by their username.")
    @GetMapping("/userCollect/{userName}")
    public ResponseEntity<APIResponse> findUserCollectById(@PathVariable String userName, HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;

        return ResponseEntity.ok(userServices.getCollectUserById(userName));
    }


    /**
     * Find a collector by ID.
     *
     * @param num The ID of the collector to retrieve.
     * @return APIResponse containing the collector details.
     */
    @Operation(summary = "Find Collector by ID", description = "Retrieve collector details by their ID.")
    @GetMapping("/collector/{num}")
    public ResponseEntity<APIResponse> findCollectorById(@PathVariable String num, HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;

        return ResponseEntity.ok(userServices.getCollectorById(num));
    }

    /**
     * Retrieve all collectors by codage.
     *
     * @param codage The codage to filter collectors by.
     * @return APIResponse containing the list of collectors matching the codage.
     */
    @Operation(summary = "Get Collectors by Codage", description = "Retrieve all collectors associated with a specific codage.")
    @GetMapping("/allCollectorByCodage/{codage}")
    public ResponseEntity<APIResponse> getAllCollectorByCodage(@PathVariable String codage, HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;
        return ResponseEntity.ok(userServices.getAllCollectorByCodage(codage));
    }

    /**
     * Retrieve all users by codage.
     *
     * @param codage The codage to filter users by.
     * @return APIResponse containing the list of users matching the codage.
     */
    @Operation(summary = "Get Users by Codage", description = "Retrieve all collect users associated with a specific codage.")
    @GetMapping("/allUserCollectByCodage/{codage}")
    public ResponseEntity<APIResponse> getAllCollectUserByCodage(@PathVariable String codage, HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;
        return ResponseEntity.ok(userServices.getAllCollectUserByCodage(codage));
    }

    /**
     * Retrieve all users.
     *
     * @return APIResponse containing the list of all users.
     */
    @Operation(summary = "Get All Users", description = "Retrieve the list of all collect users.")
    @GetMapping("/allCollectUser")
    public ResponseEntity<APIResponse> getAllClients( HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;
        return ResponseEntity.ok(userServices.getAllCollectUser());
    }

    /**
     * Retrieve all collectors.
     *
     * @return APIResponse containing the list of all collectors.
     */
    @Operation(summary = "Get All Collectors", description = "Retrieve the list of all collectors.")
    @GetMapping("/allCollector")
    public ResponseEntity<APIResponse> getAllCollector( HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;
        return ResponseEntity.ok(userServices.getAllCollecteur());
    }

   /* @PostMapping("/getToken")
    public LoginResponse loginTest(@RequestBody LoginRequest request) {
        return tokenService.generateToken(request);
    }*/

    /**
     * Token generation with rate limiting.
     */
    @PostMapping("/getToken")
    public ResponseEntity<APIResponse> loginTest(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;

        LoginResponse token = tokenService.generateToken(request);
        return ResponseEntity.ok(new APIResponse(HttpStatus.OK.value(), "Token generated successfully", token));
    }
}
