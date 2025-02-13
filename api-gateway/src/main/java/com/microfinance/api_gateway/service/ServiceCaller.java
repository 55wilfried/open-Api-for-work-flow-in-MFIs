package com.microfinance.api_gateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ServiceCaller {

    private static final Logger logger = LoggerFactory.getLogger(ServiceCaller.class);

    @Autowired
    private RestTemplate restTemplate;

    public String callService(String serviceUrl) {
        try {
            ResponseEntity<String> response = restTemplate.exchange(serviceUrl, HttpMethod.GET, null, String.class);
            logger.info("Received response from service: {} with status code: {}", serviceUrl, response.getStatusCode());
            return response.getBody();
        } catch (HttpClientErrorException | org.springframework.web.client.HttpServerErrorException e) {
            logger.error("Error while calling service {}: HTTP Error - {}", serviceUrl, e.getStatusCode());
        } catch (Exception e) {
            logger.error("Error while calling service {}: {}", serviceUrl, e.getMessage());
        }
        return null;
    }
}
