package com.microfinance.users_services.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.microfinance.users_services.models.FailedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class FailedRequestProducer {

    private static final String TOPIC = "failed-requests";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendFailedRequest(FailedRequest failedRequest) {
        try {
            String message = objectMapper.writeValueAsString(failedRequest);
            kafkaTemplate.send(TOPIC, message);
        } catch (JsonProcessingException e) {
            // Log serialization errors
            e.printStackTrace();
        }
    }
}
