package com.microfinance.auth_services.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * API Response model for standardizing API responses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Standard API response model")
public class APIResponse<T> {

    @Schema(description = "HTTP status code", example = "200")
    private int status;

    @Schema(description = "Response message", example = "Operation successful")
    private String message;

    @Schema(description = "Response data")
    private T data;
}

