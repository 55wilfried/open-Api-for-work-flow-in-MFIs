package com.microfinance.auth_services.token;

import com.microfinance.auth_services.models.Collecteur;
import com.microfinance.auth_services.models.LoginRequest;
import com.microfinance.auth_services.models.TokenResponse;
import com.microfinance.auth_services.repository.AuthentificationRepository;
import com.microfinance.auth_services.service.AuthService;
import com.microfinance.auth_services.utils.APIResponse;
import com.microfinance.auth_services.utils.CrudOperationException;
import com.microfinance.auth_services.utils.Encryption;
import com.microfinance.auth_services.utils.Trame;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class LocalTokenService {

    private static final Logger logger = LoggerFactory.getLogger(LocalTokenService.class);
    private static final String FALLBACK_SECRET = "fallback-secret-key-which-is-very-secure";



    private AuthService clientService;

    @Autowired
    private AuthentificationRepository authentificationRepository;


    public TokenResponse generateToken(String username, String password) {
        APIResponse response;
        logger.warn("Start LOCAL TOKEN generation : {}", username);
        LoginRequest loginRequest  = new LoginRequest();
      loginRequest.setUserName(username);
      loginRequest.setPassword(password);
        logger.warn("Start LOCAL TOKEN generation : {}", loginRequest.getUserName());
        logger.warn("Start LOCAL TOKEN generation : {}", loginRequest.getPassword());
         response = loginCollector1(username,password);
        logger.warn("get back from client services  LOCAL TOKEN generation : {}", username);
        if (response.getStatus() == 200) {
            logger.warn("loginCollector succeeded for user: {}", username);
            Instant now = Instant.now();
            Instant expiry = now.plus(5, ChronoUnit.MINUTES); // Short-lived token

            String jwt = Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(Date.from(now))
                    .setExpiration(Date.from(expiry))
                    .signWith(SignatureAlgorithm.HS256, FALLBACK_SECRET.getBytes())
                    .compact();

            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setAccess_token(jwt);
            tokenResponse.setExpires_in(ChronoUnit.SECONDS.between(now, expiry));
            tokenResponse.setToken_type("Bearer");
            tokenResponse.setMessage("Successfully generated fallback token");
            tokenResponse.setStatus(200);

            logger.info("Successfully generated fallback token for user: {}", username);
            return tokenResponse;
        } else {
            logger.error("loginCollector failed for user: {} with message: {}", username, response.getMessage());
            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setAccess_token("");
            tokenResponse.setExpires_in(0);
            tokenResponse.setToken_type("Bearer");
            tokenResponse.setMessage(response.getMessage());
            tokenResponse.setStatus(response.getStatus());
            return tokenResponse;
        }
    }


    public APIResponse loginCollector1(String username, String password) {
        logger.info("Starting login process for collector: {}", username);
        APIResponse response = new APIResponse();

        try {
            logger.info("Fetching collector from database for username: {}", username);
            Collecteur collecteur = authentificationRepository.findCollectorByNum(username);

            if (collecteur == null) {
                logger.warn("Collector not found: {}", username);
                throw new CrudOperationException("Collector not found", Trame.ResponseCode.NOT_FOUND);
            }

            logger.info("Validating password for collector: {}", username);
            String hashedPassword = Encryption.hashPwd(password).toUpperCase();
            logger.debug("Hashed password: {}", hashedPassword);
            logger.debug("Stored password: {}", collecteur.getPassword());

            if (!hashedPassword.equals(collecteur.getPassword())) {
                logger.warn("Invalid password for collector: {}", username);
                throw new CrudOperationException("Invalid credentials, please try again", Trame.ResponseCode.ACCESS_DENIED);
            }

            if (collecteur.getIsLocked() == 1) {
                logger.warn("Collector account is locked: {}", username);
                throw new CrudOperationException("User has been blocked", Trame.ResponseCode.CONSTRAINT_ERROR);
            }

            logger.info("Collector login successful for user: {}", username);
            response.setData(collecteur);
            response.setStatus(Trame.ResponseCode.SUCCESS);
            response.setMessage("Login successful");

        } catch (CrudOperationException e) {
           //logFailure("logincollector", Arrays.asList(username, password), e);
            logger.error("Login failed for collector {}: {}", username, e.getMessage());
            response.setStatus(e.getResponse().getStatus());
            response.setMessage(e.getResponse().getMessage());
        }

        return response;
    }


}
