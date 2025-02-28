package com.microfinance.users_services.token;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

public class FallbackJwtDecoder implements JwtDecoder {

    private final JwtDecoder primaryDecoder;
    private final JwtDecoder fallbackDecoder;

    public FallbackJwtDecoder(JwtDecoder primaryDecoder, JwtDecoder fallbackDecoder) {
        this.primaryDecoder = primaryDecoder;
        this.fallbackDecoder = fallbackDecoder;
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            return primaryDecoder.decode(token);
        } catch (JwtException ex) {
            // Log the failure and fallback to the local decoder
            System.err.println("Primary JWT decoding failed: " + ex.getMessage() + ". Falling back to local decoder.");
            return fallbackDecoder.decode(token);
        }
    }
}
