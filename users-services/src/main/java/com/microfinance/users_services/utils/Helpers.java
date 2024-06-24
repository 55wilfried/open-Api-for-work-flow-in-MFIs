package com.microfinance.users_services.utils;

import org.springframework.stereotype.Component;

@Component
public class Helpers {


    public String incrementCollectorNumber(String collectorNumber) {
        // Extract the numeric part of the collector number
        String numericPart = collectorNumber.substring(1);
        int numericValue = Integer.parseInt(numericPart);

        // Increment the numeric part by one
        numericValue++;

        // Format the new numeric value back into the collector number format
        String newCollectorNumber = collectorNumber.substring(0, 1) + String.format("%03d", numericValue);

        return newCollectorNumber;
    }
}
