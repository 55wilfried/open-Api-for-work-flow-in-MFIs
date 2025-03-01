package com.microfinance.auth_services.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microfinance.auth_services.models.LoginRequest;
import com.microfinance.auth_services.service.AuthService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.microfinance.auth_services.models.FailedRequest;


import java.util.List;

@Service
public class RequestRetryService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthService clientServices;

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

    @KafkaListener(topics = "failed-requests", groupId = "auth-service-group")
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

            LoginRequest loginRequest = new LoginRequest();
            // Increment the retry count before retrying
            failedRequest.incrementRetryCount();

            // Implement retry logic based on the method name.
            switch (failedRequest.getMethodName().toLowerCase()) {
                case "logincollectorall":
                    List<String> logincollectorallPayload = failedRequest.getPayload();
                    if (!logincollectorallPayload.isEmpty()) {
                        String num = logincollectorallPayload.get(0);
                        String password = logincollectorallPayload.get(1);
                        String request = logincollectorallPayload.get(2);
                        loginRequest.setUserName(num);
                        loginRequest.setPassword(password);
                        System.out.println("Retrying logincollectorall with payload: " + num + password +request);
                        clientServices.loginCollectorALL(loginRequest, Integer.parseInt(request));
                        retrySuccessCounter.increment();
                    }
                    break;
                case "logincollector":
                    List<String> logincollectorallPayload1 = failedRequest.getPayload();
                    if (logincollectorallPayload1.size() >= 2) {
                        String num = logincollectorallPayload1.get(0);
                        String password = logincollectorallPayload1.get(1);
                        String request = logincollectorallPayload1.get(2);
                        loginRequest.setUserName(num);
                        loginRequest.setPassword(password);
                        System.out.println("Retrying logincollector with payload: " + num + password +request);
                        clientServices.loginCollector(loginRequest);
                        retrySuccessCounter.increment();
                    }
                    break;
                case "loginuser":
                    List<String> logincollectorallPayload2 = failedRequest.getPayload();
                    if (!logincollectorallPayload2.isEmpty()) {
                        String num = logincollectorallPayload2.get(0);
                        String password = logincollectorallPayload2.get(1);
                        String request = logincollectorallPayload2.get(2);
                        loginRequest.setUserName(num);
                        loginRequest.setPassword(password);
                        System.out.println("Retrying logincollector with payload: " + num + password +request);
                        clientServices.loginUser(loginRequest);
                        retrySuccessCounter.increment();
                    }
                    break;
                default:
                    System.out.println("No retry logic defined for method see: " + failedRequest.getMethodName());
                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
