package com.microfinance.auth_services.token;


import com.microfinance.auth_services.models.LoginRequest;
import com.microfinance.auth_services.models.LoginResponse;
import com.microfinance.auth_services.models.TokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

@Service
public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    private final KeycloakTokenClient keycloakTokenClient;
    private final LocalTokenService localTokenService;

    public TokenService(KeycloakTokenClient keycloakTokenClient, LocalTokenService localTokenService) {
        this.keycloakTokenClient = keycloakTokenClient;
        this.localTokenService = localTokenService;
    }
    LoginResponse response;
    @CircuitBreaker(name = "tokenService", fallbackMethod = "fallbackGenerateToken")
    public LoginResponse generateToken(LoginRequest request) {
        logger.info("Attempting to generate token via Keycloak for user: {}", request.getUserName());

        try {
             response = keycloakTokenClient.generateToken(request.getUserName(), request.getPassword());
            logger.info("Reqest from online token back  successfully for user: {}", request.getUserName());
            logger.info("Reqest from online token back  600000: {}", response.getStatusCode());
            logger.info("Reqest from online token back  6000007777: {}", response.getMessage());

            if(response.getStatusCode() == "200"){
                return response;
            }else if(response.getStatusCode() == "401"){
                return response;
            }else{
                logger.info("Reqest from online token back  waiting 503: {}", response.getStatusCode());
                return  response = fallbackGenerateToken1(request);
            }
        } catch (HttpClientErrorException e) {
            // Continue for 401 Unauthorized
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                logger.warn("Unauthorized access for user {}. Proceeding with response.", request.getUserName());
                return new LoginResponse(null, "Bearer", 0, "401", "Unauthorized");
            }
            logger.info("Token throw exception all{}", request.getUserName());
            throw new RuntimeException("Triggering fallback due to unexpected response: " + e.getStatusCode());
        } catch (HttpServerErrorException e) {
            logger.info("Token throw Triggering fallbacK 303 all{}", request.getUserName());
            // Trigger fallback for 5xx errors
            return  response = fallbackGenerateToken1(request);

            //  throw new RuntimeException("Triggering fallback due to server error: " + e.getStatusCode());

        } catch (RestClientException e) {
            logger.info("Token throw Triggering fallbacK all{}", request.getUserName());
            // Any other network-related error triggers fallback
           // throw new RuntimeException("Triggering fallback due to connection failure.");
            return  response = fallbackGenerateToken1(request);

        }
    }



    public LoginResponse fallbackGenerateToken1(LoginRequest request) {
        logger.warn("Fallback invoked for user {} due to exception: {}", request.getUserName());
        logger.warn("WAIT LOCAL TOKEN due to exception: {}", request.getUserName());
        TokenResponse tokenResponse = localTokenService.generateToken(request.getUserName(), request.getPassword());
        logger.warn("WAIT LOCAL TOKEN due to exception: {}", request.getUserName());
        return new LoginResponse(
                tokenResponse.getAccess_token(),
                tokenResponse.getToken_type(),
                (int) tokenResponse.getExpires_in(),
                String.valueOf(tokenResponse.getStatus()),
                tokenResponse.getMessage()
        );
    }

    /*public LoginResponse fallbackGenerateToken(LoginRequest request, Throwable e) {
        logger.warn("Fallback invoked for user {} due to exception: {}", request.getUserName(), e.getMessage());
        TokenResponse tokenResponse = localTokenService.generateToken(request.getUserName(), request.getPassword());
        return new LoginResponse(
                tokenResponse.getAccess_token(),
                tokenResponse.getToken_type(),
                (int) tokenResponse.getExpires_in(),
                String.valueOf(tokenResponse.getStatus()),
                tokenResponse.getMessage()
        );
    }*/
}
