package com.microfinance.client_services.models;

import java.util.List;

public class FailedRequest {
    private String methodName;
    private List<String> payload;
    private String errorMessage;
    private int retryCount;  // New field

    public FailedRequest() {}

    public FailedRequest(String methodName, List<String> payload, String errorMessage) {
        this.methodName = methodName;
        this.payload = payload;
        this.errorMessage = errorMessage;
        this.retryCount = 3;  // Initialize retry count to 0
    }

    // Getters and setters
    public String getMethodName() {
        return methodName;
    }
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    public List<String> getPayload() {
        return payload;
    }
    public void setPayload(List<String> payload) {
        this.payload = payload;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public int getRetryCount() {
        return retryCount;
    }
    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }
    public void incrementRetryCount() {
        this.retryCount++;
    }
}
