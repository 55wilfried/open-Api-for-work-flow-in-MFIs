package com.microfinance.transaction_services.controller;

import com.microfinance.transaction_services.models.OperationCollecte;
import com.microfinance.transaction_services.service.TransactionServices;
import com.microfinance.transaction_services.utils.APIResponse;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

@RequestMapping("/transactions")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@SecurityRequirement(name = "keycloak84")
@SecurityRequirement(name = "local84")
public class TransactionController {

    @Autowired
    private TransactionServices transactionServices;



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

    @Operation(summary = "Add a new operation", description = "This endpoint allows adding a new operation.")
    @ApiResponse(responseCode = "200", description = "Operation added successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = APIResponse.class)))
    @ApiResponse(responseCode = "429", description = "Too Many Requests")
    @PostMapping("/makeOperation")
    public ResponseEntity<APIResponse> addOperation(@RequestBody @Valid OperationCollecte operationCollecte, HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;

        return ResponseEntity.ok(transactionServices.addOperation(String.valueOf(operationCollecte)));
    }

    @Operation(summary = "Get all operations", description = "Retrieve all client operations.")
    @GetMapping("/allOperation")
    public ResponseEntity<APIResponse> getAllClients(HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;
        return ResponseEntity.ok(transactionServices.getAllClients());
    }

    @Operation(summary = "Find client by number", description = "Retrieve client information by their number.")
    @GetMapping("/number/{num}")
    public ResponseEntity<APIResponse> findClientByNum(@PathVariable String num,HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;
        return ResponseEntity.ok(transactionServices.getOperationByNum(num));
    }

    @Operation(summary = "Get operations by codage", description = "Retrieve all operations for a specific codage.")
    @GetMapping("/allTransactionByCodage/{codage}")
    public ResponseEntity<APIResponse> getAllClientsByCodage(@PathVariable String codage,HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;
        return ResponseEntity.ok(transactionServices.getAllOperationByCodage(codage));
    }

    @Operation(summary = "Get operations by collector", description = "Retrieve all operations for a specific collector.")
    @GetMapping("/allClientByCollector/{collector}")
    public ResponseEntity<APIResponse> getAllClientsByCollector(@PathVariable String collector,HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;
        return ResponseEntity.ok(transactionServices.getAllOperationByCol(collector));
    }

    @Operation(summary = "Get operations by parameters", description = "Retrieve operations matching specific parameters.")
    @PostMapping("/getOperationByParams")
    public ResponseEntity<APIResponse> getOperationByParams(@RequestBody @Valid String request,HttpServletRequest httpRequest) {
        ResponseEntity<APIResponse> rateLimitResponse = handleRateLimit(httpRequest);
        if (rateLimitResponse != null) return rateLimitResponse;
        return ResponseEntity.ok(transactionServices.getOperationByParams(request));
    }
}
