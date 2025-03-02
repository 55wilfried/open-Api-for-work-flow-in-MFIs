package com.microfinance.reporting_services.controller;

import com.microfinance.reporting_services.service.ReportingServices;
import com.microfinance.reporting_services.utils.APIResponse;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowCredentials = "true")
@CrossOrigin(origins = "*", maxAge = 3600)
@SecurityRequirement(name = "keycloak85")
@RequestMapping("/reports")
@RestController
public class ReportingController {

    @Autowired
    ReportingServices reportingServices;

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
     * Get operation by client number.
     *
     * @param num The client number.
     * @return The operation data if found.
     */
    @Operation(summary = "Get operation by client number",
            description = "Fetches the operation details based on the provided client number.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Operation not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            })
    @GetMapping("/number/{num}")
    public ResponseEntity<APIResponse> findClientByNum(@PathVariable String num, HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;

        return ResponseEntity.ok(reportingServices.getOperationByNum(num));
    }

    /**
     * Get operations by provided parameters for operations.
     *
     * @param request The request body containing parameters.
     * @return A list of operations matching the parameters.
     */
    @Operation(summary = "Get operations by parameters",
            description = "Fetches operations based on parameters provided in the request body.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operations found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "No operations found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            })
    @GetMapping("/getOperationByParams/{params}")
    public ResponseEntity<APIResponse> getOperationByParamsOperation(@RequestBody @Valid String request, HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;

        return ResponseEntity.ok(reportingServices.getOperationByParamsOperation(request));
    }

    /**
     * Get client by client number.
     *
     * @param numClient The client number.
     * @return The client data if found.
     */
    @Operation(summary = "Get client by client number",
            description = "Fetches the client details based on the provided client number.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Client not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            })
    @GetMapping("/numClient/{numClient}")
    public ResponseEntity<APIResponse> findClientByNumClient(@PathVariable String numClient, HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;

        return ResponseEntity.ok(reportingServices.getClientById(numClient));
    }

    /**
     * Get operations by provided parameters for clients.
     *
     * @param request The request body containing parameters.
     * @return A list of operations matching the parameters.
     */
    @Operation(summary = "Get operations by parameters",
            description = "Fetches operations based on parameters provided in the request body.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operations found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "No operations found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            })
    @GetMapping("/getClientByParams/{params}")
    public  ResponseEntity<APIResponse> getOperationByParams(@RequestBody @Valid String request, HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;

        return ResponseEntity.ok(reportingServices.getOperationByParams(request));
    }
}
