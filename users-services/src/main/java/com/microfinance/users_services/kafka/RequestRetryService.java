package com.microfinance.users_services.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microfinance.users_services.models.FailedRequest;
import com.microfinance.users_services.service.UserServices;
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
    private UserServices userServices;

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

    @KafkaListener(topics = "failed-requests", groupId = "User-service-group")
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
            switch (failedRequest.getMethodName().toLowerCase()) {
                case "getallcollecteur":
                    List<String> addClientPayload = failedRequest.getPayload();
                    if (!addClientPayload.isEmpty()) {
                        System.out.println("Retrying getallcollecteur with payload: " + addClientPayload.get(0));
                        userServices.getAllCollecteur();
                        retrySuccessCounter.increment();
                    }
                    break;
                case "getallcollectuser":
                    List<String> updatePayload = failedRequest.getPayload();
                    if (updatePayload.size() >= 2) {
                        String num = updatePayload.get(0);
                        String password = updatePayload.get(1);
                        System.out.println("Retrying getallcollectuser for client: ");
                        userServices.getAllCollectUser();
                        retrySuccessCounter.increment();
                    }
                    break;
                case "getallcollectorbycodage":
                    List<String> getAllClientPayload = failedRequest.getPayload();
                    if (!getAllClientPayload.isEmpty()) {
                        System.out.println("Retrying getallcollectorbycodage with payload: " + getAllClientPayload.get(0));
                        userServices.getAllCollectorByCodage(getAllClientPayload.get(0));
                        retrySuccessCounter.increment();
                    }
                    break;
                case "getallcollectuserbycodage":
                    List<String> getAllClientByColPayload = failedRequest.getPayload();
                    if (!getAllClientByColPayload.isEmpty()) {
                        System.out.println("Retrying getallcollectuserbycodage with payload: " + getAllClientByColPayload.get(0));
                        userServices.getAllCollectUserByCodage(getAllClientByColPayload.get(0));
                        retrySuccessCounter.increment();
                    }
                    break;
                case "getcollectorbyid":
                    List<String> getAllClientByCodagePayload = failedRequest.getPayload();
                    if (!getAllClientByCodagePayload.isEmpty()) {
                        System.out.println("Retrying getcollectorbyid with collector: " + getAllClientByCodagePayload.get(0));
                        userServices.getCollectorById(getAllClientByCodagePayload.get(0));
                        retrySuccessCounter.increment();
                    }
                    break;
                case "getcollectuserbyid":
                    List<String> getClientByIdPayload = failedRequest.getPayload();
                    if (!getClientByIdPayload.isEmpty()) {
                        System.out.println("Retrying getcollectuserbyid with collectUser: " + getClientByIdPayload.get(0));
                        userServices.getCollectUserById(getClientByIdPayload.get(0));
                        retrySuccessCounter.increment();
                    }
                    break;

                case "addCollector":
                    List<String> getClientByNamePayload = failedRequest.getPayload();
                    if (!getClientByNamePayload.isEmpty()) {
                        System.out.println("Retrying addCollector with collector: " + getClientByNamePayload.get(0));
                        userServices.addCollector(getClientByNamePayload.get(0));
                        retrySuccessCounter.increment();
                    }
                    break;
                case "updatecollector":
                    List<String> update1Payload = failedRequest.getPayload();
                    if (update1Payload.size() >= 2) {
                        String num = update1Payload.get(0);
                        String password = update1Payload.get(1);
                        System.out.println("Retrying updatecollector for collector: " + num + password);
                        userServices.updateCollector(num, password);
                        retrySuccessCounter.increment();
                    }
                    break;
                case "updateUsercollect":
                    List<String> updateUsercollect = failedRequest.getPayload();
                    if (updateUsercollect.size() >= 2) {
                        String num = updateUsercollect.get(0);
                        String password = updateUsercollect.get(1);
                        System.out.println("Retrying updateUsercollect for user: " + num + password);
                        userServices.updateUserCollect(num, password);
                        retrySuccessCounter.increment();
                    }
                    break;

                case "logincollector":
                    List<String> logincollector = failedRequest.getPayload();
                    if (logincollector.size() >= 2) {
                        String num = logincollector.get(0);
                        String password = logincollector.get(1);
                        System.out.println("Retrying logincollector for collector: " + num + password);
                        userServices.updateUserCollect(num, password);
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

    @KafkaListener(topics = "success-requests", groupId = "User-service-group")
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
            switch (failedRequest.getMethodName().toLowerCase()) {
                case "getallcollecteur":
                    List<String> addClientPayload = failedRequest.getPayload();
                    if (!addClientPayload.isEmpty()) {
                        System.out.println("Retrying getallcollecteur with payload: " + addClientPayload.get(0));
                        userServices.getAllCollecteur();
                        retrySuccessCounter.increment();
                    }
                    break;
                case "getallcollectuser":
                    List<String> updatePayload = failedRequest.getPayload();
                    if (updatePayload.size() >= 2) {
                        String num = updatePayload.get(0);
                        String password = updatePayload.get(1);
                        System.out.println("Retrying getallcollectuser for client: ");
                        userServices.getAllCollectUser();
                        retrySuccessCounter.increment();
                    }
                    break;
                case "getallcollectorbycodage":
                    List<String> getAllClientPayload = failedRequest.getPayload();
                    if (!getAllClientPayload.isEmpty()) {
                        System.out.println("Retrying getallcollectorbycodage with payload: " + getAllClientPayload.get(0));
                        userServices.getAllCollectorByCodage(getAllClientPayload.get(0));
                        retrySuccessCounter.increment();
                    }
                    break;
                case "getallcollectuserbycodage":
                    List<String> getAllClientByColPayload = failedRequest.getPayload();
                    if (!getAllClientByColPayload.isEmpty()) {
                        System.out.println("Retrying getallcollectuserbycodage with payload: " + getAllClientByColPayload.get(0));
                        userServices.getAllCollectUserByCodage(getAllClientByColPayload.get(0));
                        retrySuccessCounter.increment();
                    }
                    break;
                case "getcollectorbyid":
                    List<String> getAllClientByCodagePayload = failedRequest.getPayload();
                    if (!getAllClientByCodagePayload.isEmpty()) {
                        System.out.println("Retrying getcollectorbyid with collector: " + getAllClientByCodagePayload.get(0));
                        userServices.getCollectorById(getAllClientByCodagePayload.get(0));
                        retrySuccessCounter.increment();
                    }
                    break;
                case "getcollectuserbyid":
                    List<String> getClientByIdPayload = failedRequest.getPayload();
                    if (!getClientByIdPayload.isEmpty()) {
                        System.out.println("Retrying getcollectuserbyid with collectUser: " + getClientByIdPayload.get(0));
                        userServices.getCollectUserById(getClientByIdPayload.get(0));
                        retrySuccessCounter.increment();
                    }
                    break;

                case "addCollector":
                    List<String> getClientByNamePayload = failedRequest.getPayload();
                    if (!getClientByNamePayload.isEmpty()) {
                        System.out.println("Retrying addCollector with collector: " + getClientByNamePayload.get(0));
                        userServices.addCollector(getClientByNamePayload.get(0));
                        retrySuccessCounter.increment();
                    }
                    break;
                case "updatecollector":
                    List<String> update1Payload = failedRequest.getPayload();
                    if (update1Payload.size() >= 2) {
                        String num = update1Payload.get(0);
                        String password = update1Payload.get(1);
                        System.out.println("Retrying updatecollector for collector: " + num + password);
                        userServices.updateCollector(num, password);
                        retrySuccessCounter.increment();
                    }
                    break;
                case "updateUsercollect":
                    List<String> updateUsercollect = failedRequest.getPayload();
                    if (updateUsercollect.size() >= 2) {
                        String num = updateUsercollect.get(0);
                        String password = updateUsercollect.get(1);
                        System.out.println("Retrying updateUsercollect for user: " + num + password);
                        userServices.updateUserCollect(num, password);
                        retrySuccessCounter.increment();
                    }
                    break;

                case "logincollector":
                    List<String> logincollector = failedRequest.getPayload();
                    if (logincollector.size() >= 2) {
                        String num = logincollector.get(0);
                        String password = logincollector.get(1);
                        System.out.println("Retrying logincollector for collector: " + num + password);
                        userServices.updateUserCollect(num, password);
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
