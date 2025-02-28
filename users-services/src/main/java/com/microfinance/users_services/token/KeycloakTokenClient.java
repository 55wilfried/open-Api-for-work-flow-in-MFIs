package com.microfinance.users_services.token;


import com.microfinance.users_services.models.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class KeycloakTokenClient {

    private static final Logger logger = LoggerFactory.getLogger(KeycloakTokenClient.class);
    private final String keycloakUrl;
    private final String realm;
    private final String clientId;
    private final String clientSecret;
    private final RestTemplate restTemplate;

    public KeycloakTokenClient(
            @Value("${keycloak.auth-server-url}") String keycloakUrl,
            @Value("${keycloak.realm}") String realm,
            @Value("${keycloak.client-id}") String clientId,
            @Value("${keycloak.client-secret}") String clientSecret
    ) {
        this.keycloakUrl = keycloakUrl;
        this.realm = realm;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.restTemplate = new RestTemplate();
    }

    public LoginResponse generateToken(String username, String password) {
        String tokenUrl = keycloakUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "password");
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("username", username);
        requestBody.add("password", password);

        logger.info("Sending token request to Keycloak for user: {}", username);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);
            Map<String, Object> body = response.getBody();

            if (response.getStatusCode() == HttpStatus.OK && body != null && body.containsKey("access_token")) {
                logger.info("Successfully retrieved token from Keycloak for user: {}", username);
                return new LoginResponse(
                        (String) body.get("access_token"),
                        (String) body.get("token_type"),
                        ((Number) body.get("expires_in")).intValue(),
                        "200",
                        "Successfully generated token"
                );
            }

            logger.error("Unexpected response from Keycloak. Status Code: {}", response.getStatusCode());
            return new LoginResponse(null, null, 0, String.valueOf(response.getStatusCodeValue()), "Unexpected error occurred");

        } catch (HttpClientErrorException.Unauthorized e) {
            logger.warn("Invalid credentials for user: {}", username);
            return new LoginResponse(null, null, 0, "401", "Invalid credentials. Please check your username and password.");
        } catch (HttpClientErrorException.NotFound e) {
            logger.error("Keycloak token endpoint not found: {}", tokenUrl);
            return new LoginResponse(null, null, 0, "404", "Keycloak token endpoint not found. Please check your configuration.");
        } catch (ResourceAccessException e) {
            logger.error("Keycloak server is unreachable: {}", e.getMessage());
            return new LoginResponse(null, null, 0, "503", "Keycloak server is unreachable. Please try again later.");
        } catch (Exception e) {
            logger.error("Exception occurred during token retrieval: {}", e.getMessage());
            return new LoginResponse(null, null, 0, "500", "Internal server error occurred.");
        }
    }
}
