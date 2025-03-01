package com.microfinance.auth_services.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microfinance.auth_services.models.FailedRequest;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestRetryService {

    @Autowired
    private ObjectMapper objectMapper;



    @Autowired
    private MeterRegistry meterRegistry;

    private Counter failedRequestCounter;
    private Counter retrySuccessCounter;

    @PostConstruct
    public void init() {
        // Counter for the number of failed requests processed
        failedRequestCounter = meterRegistry.counter("failed.requests.processed");
        // Counter for successful retries
        retrySuccessCounter = meterRegistry.counter("retry.success.count");
    }

    @KafkaListener(topics = "failed-requests", groupId = "loan-service-group")
    public void processFailedRequest(String message) {
        try {
            FailedRequest failedRequest = objectMapper.readValue(message, FailedRequest.class);
            System.out.println("Processing failed request for method: " + failedRequest.getMethodName());
            failedRequestCounter.increment();

            // Check if the retry count has exceeded a threshold (e.g., 3)
            if (failedRequest.getRetryCount() >= 3) {
                System.out.println("Maximum retries reached for method: " + failedRequest.getMethodName() + ". Skipping further retries.");
                // Optionally, log to a dead-letter queue or alert someone.
                return;
            }

            // Increment the retry count before retrying
            failedRequest.incrementRetryCount();

            // Implement retry logic based on the method name.



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "success-requests", groupId = "loan-service-group")
    public void processFailedRequest1(String message) {
        try {
            FailedRequest failedRequest = objectMapper.readValue(message, FailedRequest.class);
            System.out.println("Processing failed request for method: " + failedRequest.getMethodName());
            failedRequestCounter.increment();

            // Check if the retry count has exceeded a threshold (e.g., 3)
            if (failedRequest.getRetryCount() >= 3) {
                System.out.println("Maximum retries reached for method: " + failedRequest.getMethodName() + ". Skipping further retries.");
                // Optionally, log to a dead-letter queue or alert someone.
                return;
            }

            // Increment the retry count before retrying
            failedRequest.incrementRetryCount();

            // Implement retry logic based on the method name.



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
