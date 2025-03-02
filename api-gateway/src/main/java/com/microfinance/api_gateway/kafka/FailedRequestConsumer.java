package com.microfinance.api_gateway.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import java.time.Duration;

@Component
public class FailedRequestConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FailedRequestConsumer.class);
    private final WebClient webClient = WebClient.create();

    @KafkaListener(topics = "failed-services", groupId = "api-gateway-group")
    public void listenFailedRequests(String message) {
        // Expected message format: "service|requestDetails"
        String[] parts = message.split("\\|", 2);
        if (parts.length < 2) {
            LOGGER.error("Invalid message format: {}", message);
            return;
        }
        String service = parts[0];
        String requestDetails = parts[1];
        LOGGER.info("Received failed request for service {}: {}", service, requestDetails);

        // Determine target URL based on the service identifier.
        String targetUrl = resolveServiceUrl(service);
        if (targetUrl == null) {
            LOGGER.error("Unknown service: {}", service);
            return;
        }

        // Simulate retrying the original request.
        // Here we assume a GET request; for other HTTP methods, you'll need to adjust the WebClient call.
        webClient.get()
                .uri(targetUrl)
                .retrieve()
                .bodyToMono(String.class)
                // Retry up to 3 times with a fixed delay of 2 seconds if the error is transient (e.g., server errors)
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2))
                        .filter(throwable -> isTransientError(throwable)))
                .subscribe(
                        response -> LOGGER.info("Retried request succeeded for service {}: {}", service, response),
                        error -> LOGGER.error("Retried request failed for service {}: {}", service, error.getMessage())
                );
    }

    /**
     * Determine the URL for the given service.
     * In a real-world scenario, you might use service discovery or configuration to resolve this.
     */
    private String resolveServiceUrl(String service) {
        switch (service.toLowerCase()) {
            case "client":
                return "http://client-service/endpoint";
            case "users":
                return "http://users-services/endpoint";
            case "transactions":
                return "http://transaction-service/endpoint";
            case "reports":
                return "http://reporting-services/endpoint";
            case "loan":
                return "http://loans-services/endpoint";
            case "auth":
                return "http://auth-services/endpoint";
            default:
                return null;
        }
    }

    /**
     * Determines if the throwable represents a transient error.
     * You can customize the logic to check for specific exceptions or HTTP status codes.
     */
    private boolean isTransientError(Throwable throwable) {
        // For demonstration purposes, we check if the error message contains "5"
        // indicating a 5xx server error. In a real application, use more robust error handling.
        return throwable.getMessage() != null && throwable.getMessage().contains("5");
    }
}
