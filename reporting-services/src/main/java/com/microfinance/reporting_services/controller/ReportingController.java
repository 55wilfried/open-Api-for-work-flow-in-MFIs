package com.microfinance.reporting_services.controller;

import com.microfinance.reporting_services.service.ReportingServices;
import com.microfinance.reporting_services.utils.APIResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/reports")
@RestController
public class ReportingController {

    @Autowired
    ReportingServices reportingServices;

    /**
     * Get operation by client number.
     *
     * @param num The client number.
     * @return The operation data if found.
     */
    @Operation(summary = "Get operation by client number",
            description = "Fetches the operation details based on the provided client number.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Operation not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            })
    @GetMapping("/number/{num}")
    public APIResponse findClientByNum(@PathVariable String num) {
        return reportingServices.getOperationByNum(num);
    }

    /**
     * Get operations by provided parameters for operations.
     *
     * @param request The request body containing parameters.
     * @return A list of operations matching the parameters.
     */
    @Operation(summary = "Get operations by parameters",
            description = "Fetches operations based on parameters provided in the request body.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operations found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "No operations found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            })
    @GetMapping("/getOperationByParams/{params}")
    public APIResponse getOperationByParamsOperation(@RequestBody @Valid String request) {
        return reportingServices.getOperationByParamsOperation(request);
    }

    /**
     * Get client by client number.
     *
     * @param numClient The client number.
     * @return The client data if found.
     */
    @Operation(summary = "Get client by client number",
            description = "Fetches the client details based on the provided client number.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Client not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            })
    @GetMapping("/numClient/{numClient}")
    public APIResponse findClientByNumClient(@PathVariable String numClient) {
        return reportingServices.getClientById(numClient);
    }

    /**
     * Get operations by provided parameters for clients.
     *
     * @param request The request body containing parameters.
     * @return A list of operations matching the parameters.
     */
    @Operation(summary = "Get operations by parameters",
            description = "Fetches operations based on parameters provided in the request body.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operations found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "No operations found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            })
    @GetMapping("/getClientByParams/{params}")
    public APIResponse getOperationByParams(@RequestBody @Valid String request) {
        return reportingServices.getOperationByParams(request);
    }
}
