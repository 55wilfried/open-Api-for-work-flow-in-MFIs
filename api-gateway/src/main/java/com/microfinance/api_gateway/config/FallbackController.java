/*
package com.microfinance.api_gateway.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FallbackController {



        @GetMapping(value = "/fallback/{segment}")
        public ResponseEntity<Object> fallback(@PathVariable String segment) {
            Map<String, String> mapResponse = new HashMap<>();
            mapResponse.put("status", "SERVICE "+ StringUtils.upperCase(segment) +" IS UNAVAILABLE");
            return new ResponseEntity<>(mapResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/


package com.microfinance.api_gateway.config;

import com.microfinance.api_gateway.kafka.FailedRequestProducer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FallbackController {

    @Autowired
    private FailedRequestProducer failedRequestProducer;

    @GetMapping("/fallback/{segment}")
    public ResponseEntity<Object> fallback(@PathVariable String segment, ServerHttpRequest request) {
        // Create a message with details of the failed request
        String requestDetails = "URI: " + request.getURI() + ", Method: " + request.getMethod();
        failedRequestProducer.sendFailedRequest(segment, requestDetails);

        Map<String, String> response = new HashMap<>();
        response.put("status", "SERVICE " + StringUtils.upperCase(segment) + " IS UNAVAILABLE");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

