package com.microfinance.api_gateway.helpers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.microfinance.api_gateway.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
@Component
public class Helpers {


    public Mono<Void> handleErrorResponse(ServerWebExchange exchange, String errorMessage, HttpStatus status) {
        // Create a JSON error response
        ErrorResponse errorResponse = new ErrorResponse(
                ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                exchange.getRequest().getURI().toString(),
                status.value(),
                status.getReasonPhrase(),
                UUID.randomUUID().toString(),
                errorMessage
        );

        // Set the response status and write the error response as JSON
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(JsonUtils.toJsonBytes(errorResponse))));
    }


    public static class JsonUtils {
        private static final ObjectMapper objectMapper = new ObjectMapper();

        public static byte[] toJsonBytes(Object object) {
            try {
                return objectMapper.writeValueAsBytes(object);
            } catch (Exception e) {
                throw new RuntimeException("Failed to convert object to JSON", e);
            }
        }
    }

}
