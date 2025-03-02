package com.microfinance.loan_services.AuthController;

import com.microfinance.loan_services.utils.APIResponse;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/loan")
//@CrossOrigin(origins= {"*"}, maxAge = 3600, allowCredentials = "false")
//@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowCredentials = "true")
@CrossOrigin(origins = "*", maxAge = 3600)
@SecurityRequirement(name = "keycloak86")
public class LoanController {


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

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to Loan Micro finance!";
    }

}
