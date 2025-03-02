package com.microfinance.api_gateway.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class FailedRequestProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public FailedRequestProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendFailedRequest(String service, String payload) {
        // Simple message format: "service|payload"
        String topic = "failed-services";
        String message = service + "|" + payload;
        kafkaTemplate.send(topic, message);
    }
}
