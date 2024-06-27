package com.microfinance.api_gateway.model;

import lombok.Data;

@Data
public class ErrorResponse {

    private String timestamp;
    private String path;
    private int status;
    private String error;
    private String requestId;
    private String message;


    public ErrorResponse(String timestamp, String path, int status, String error, String requestId, String message) {
        this.timestamp = timestamp;
        this.path = path;
        this.status = status;
        this.error = error;
        this.requestId = requestId;
        this.message = message;
    }
}
