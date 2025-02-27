package com.microfinance.client_services.token;

import com.microfinance.client_services.models.TokenResponse;
import com.microfinance.client_services.services.ClientServices;
import com.microfinance.client_services.utils.APIResponse;
import com.microfinance.client_services.utils.CrudOperationException;
import com.microfinance.client_services.utils.Trame;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class LocalTokenService {

    private static final Logger logger = LoggerFactory.getLogger(LocalTokenService.class);
    private static final String FALLBACK_SECRET = "fallback-secret-key-which-is-very-secure";

    @Autowired
    private ClientServices clientService;

    public TokenResponse generateToken(String username, String password) {
        APIResponse response = clientService.loginCollector(username, password);

        if (response.getStatus() == 200) {
            logger.info("loginCollector succeeded for user: {}", username);
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
}
