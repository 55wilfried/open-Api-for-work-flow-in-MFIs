package com.microfinance.client_services.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microfinance.client_services.models.FailedRequest;
import com.microfinance.client_services.services.ClientServices;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestRetryService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientServices clientServices;

    @Autowired
    private MeterRegistry meterRegistry;

    private Counter failedRequestCounter;
    private Counter retrySuccessCounter;
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestRetryService.class);

    @PostConstruct
    public void init() {
        // Counter for the number of failed requests processed
        failedRequestCounter = meterRegistry.counter("failed.requests.processed");
        // Counter for successful retries
        retrySuccessCounter = meterRegistry.counter("retry.success.count");
    }

    @KafkaListener(topics = "failed-requests", groupId = "client-service-group")
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
                case "addclient":
                    List<String> addClientPayload = failedRequest.getPayload();
                    if (!addClientPayload.isEmpty()) {
                        System.out.println("Retrying addClient with payload: " + addClientPayload.get(0));
                        clientServices.addClient(addClientPayload.get(0));
                        retrySuccessCounter.increment();
                    }
                    break;
                case "updateclientpassword":
                    List<String> updatePayload = failedRequest.getPayload();
                    if (updatePayload.size() >= 2) {
                        String num = updatePayload.get(0);
                        String password = updatePayload.get(1);
                        System.out.println("Retrying updateClientPassword for client: " + num);
                        clientServices.updateClientPassword(num, password);
                        retrySuccessCounter.increment();
                    }
                    break;
                case "getallclients":
                    List<String> getAllClientPayload = failedRequest.getPayload();
                    if (!getAllClientPayload.isEmpty()) {
                        System.out.println("Retrying addClient with payload: " + getAllClientPayload.get(0));
                        clientServices.getAllClients();
                        retrySuccessCounter.increment();
                    }
                    break;
                case "getallclientbycol":
                    List<String> getAllClientByColPayload = failedRequest.getPayload();
                    if (!getAllClientByColPayload.isEmpty()) {
                        System.out.println("Retrying addClient with payload: " + getAllClientByColPayload.get(0));
                        clientServices.getAllClientByCol(getAllClientByColPayload.get(0));
                        retrySuccessCounter.increment();
                    }
                    break;
                case "getallclientbycodage":
                    List<String> getAllClientByCodagePayload = failedRequest.getPayload();
                    if (!getAllClientByCodagePayload.isEmpty()) {
                        System.out.println("Retrying addClient with payload: " + getAllClientByCodagePayload.get(0));
                        clientServices.getAllClientByCodage(getAllClientByCodagePayload.get(0));
                        retrySuccessCounter.increment();
                    }
                    break;
                case "getclientbyid":
                    List<String> getClientByIdPayload = failedRequest.getPayload();
                    if (!getClientByIdPayload.isEmpty()) {
                        System.out.println("Retrying addClient with payload: " + getClientByIdPayload.get(0));
                        clientServices.getClientById(getClientByIdPayload.get(0));
                        retrySuccessCounter.increment();
                    }
                    break;

                case "getclientbyname":
                    List<String> getClientByNamePayload = failedRequest.getPayload();
                    if (!getClientByNamePayload.isEmpty()) {
                        System.out.println("Retrying getClientByName with payload: " + getClientByNamePayload.get(0));
                        clientServices.getClientByName(getClientByNamePayload.get(0));
                        retrySuccessCounter.increment();
                    }
                    break;

                case "logincollector":
                    List<String> logCollectPayload = failedRequest.getPayload();
                    if (logCollectPayload.size() >= 2) {
                        String num = logCollectPayload.get(0);
                        String password = logCollectPayload.get(1);
                        System.out.println("Retrying updateClientPassword for client: " + num);
                        clientServices.loginCollector(num, password);
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
