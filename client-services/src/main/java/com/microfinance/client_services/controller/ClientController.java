package com.microfinance.client_services.controller;

import com.microfinance.client_services.models.LoginRequest;
import com.microfinance.client_services.models.LoginResponse;
import com.microfinance.client_services.services.ClientServices;
import com.microfinance.client_services.token.KeycloakTokenClient;
import com.microfinance.client_services.token.TokenService;
import com.microfinance.client_services.utils.APIResponse;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Controller class to handle client-related requests.
 * Handles client creation, update, and retrieval.
 */
//@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowCredentials = "true")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/client")
@SecurityRequirement(name = "keycloak")
@SecurityRequirement(name = "local")
public class ClientController {


    @Autowired
    private KeycloakTokenClient keycloakService;

    @Autowired
    private final TokenService tokenService;

    public ClientController(TokenService tokenService) {
        this.tokenService = tokenService;
    }


    @Autowired
    private ClientServices clientService;


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
     * Add a new client.
     *
     * @param client the client data in JSON format.
     * @return API response with the result of the operation.
     */
    @PostMapping("/add")
    public APIResponse addClient(@RequestBody @Valid String client) {
        return clientService.addClient(client);
    }

    /**
     * Update an existing client's password.
     *
     * @param num     the client number.
     * @param password the new password for the client.
     * @return API response with the result of the operation.
     */
    @PutMapping("/update/{num}")
    public ResponseEntity<APIResponse> updateClient(@PathVariable String num, @RequestBody @Valid String password, HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;

        return ResponseEntity.ok(clientService.updateClientPassword(num , password));
    }

    /**
     * Retrieve a client by their client number.
     *
     * @param numClient the client number.
     * @return API response with client data.
     */
    @GetMapping("/numClient/{numClient}")
    public ResponseEntity<APIResponse> findClientByNum(@PathVariable String numClient, HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;

        return ResponseEntity.ok(clientService.getClientById(numClient));
    }

    /**
     * Retrieve a client by their name.
     *
     * @param name the name of the client.
     * @return API response with client data.
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<APIResponse> findClientByName(@PathVariable String name, HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;

        return ResponseEntity.ok(clientService.getClientByName(name));
    }

    /**
     * Retrieve all clients.
     *
     * @return API response with list of all clients.
     */
    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllClients(HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;

        return ResponseEntity.ok(clientService.getAllClients());
    }

    /**
     * Retrieve all clients by their codage.
     *
     * @param codage the codage value.
     * @return API response with list of clients filtered by codage.
     */
    @GetMapping("/allClientByCodage/{codage}")
    public ResponseEntity<APIResponse> getAllClientsByCodage(@PathVariable String codage, HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;

        return ResponseEntity.ok(clientService.getAllClientByCodage(codage));
    }

    /**
     * Retrieve all clients assigned to a specific collector.
     *
     * @param collector the collector number.
     * @return API response with list of clients assigned to the collector.
     */
    @GetMapping("/allClientByCollector/{collector}")
    public ResponseEntity<APIResponse> getAllClientsByCollector(@PathVariable String collector, HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;

        return ResponseEntity.ok(clientService.getAllClientByCol(collector));
    }


    @PostMapping("/getToken")
    public ResponseEntity<APIResponse> loginTest(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;

        LoginResponse token = tokenService.generateToken(request);
        return ResponseEntity.ok(new APIResponse(HttpStatus.OK.value(), "Token generated successfully", token));
    }

}
