/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microfinance.reporting_services.utils;


/**
 *
 * @author Henry
 */
//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CrudOperationException extends Exception {
    private APIResponse response;

    public CrudOperationException(String message, int responseCode) {
        super(message.toUpperCase());
        this.response = new APIResponse(responseCode, message.toUpperCase(), null);
    }

    public APIResponse getResponse() {
        return this.response;
    }
}
