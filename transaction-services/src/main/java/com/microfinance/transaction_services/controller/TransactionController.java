package com.microfinance.transaction_services.controller;

import com.microfinance.transaction_services.dto.OperationCollecte;
import com.microfinance.transaction_services.service.TransactionServices;
import com.microfinance.transaction_services.utils.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/transactions")
@RestController
//@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowCredentials = "true")
@CrossOrigin(origins = "*", maxAge = 3600)
@SecurityRequirement(name = "keycloak84")
public class TransactionController {

    @Autowired
    private TransactionServices transactionServices;

    /**
     * Add a new operation.
     *
     * @param operationCollecte JSON string representing the operation details.
     * @return APIResponse containing the result of the operation.
     */
    @Operation(summary = "Add a new operation", description = "This endpoint allows adding a new operation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation added successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/makeOperation")
    public APIResponse addOperation(
            @RequestBody @Valid
            @Parameter(description = "Details of the operation to add", required = true) OperationCollecte operationCollecte) {
        return transactionServices.addOperation(String.valueOf(operationCollecte));
    }

    @Operation(summary = "Get all operations", description = "Retrieve all client operations.")
    @GetMapping("/allOperation")
    public APIResponse getAllClients() {
        return transactionServices.getAllClients();
    }

    @Operation(summary = "Find client by number", description = "Retrieve client information by their number.")
    @GetMapping("/number/{num}")
    public APIResponse findClientByNum(
            @PathVariable
            @Parameter(description = "Client number", required = true) String num) {
        return transactionServices.getOperationByNum(num);
    }

    @Operation(summary = "Get operations by codage", description = "Retrieve all operations for a specific codage.")
    @GetMapping("/allTransactionByCodage/{codage}")
    public APIResponse getAllClientsByCodage(
            @PathVariable
            @Parameter(description = "Codage value", required = true) String codage) {
        return transactionServices.getAllOperationByCodage(codage);
    }

    @Operation(summary = "Get operations by collector", description = "Retrieve all operations for a specific collector.")
    @GetMapping("/allClientByCollector/{collector}")
    public APIResponse getAllClientsByCollector(
            @PathVariable
            @Parameter(description = "Collector name or ID", required = true) String collector) {
        return transactionServices.getAllOperationByCol(collector);
    }

    @Operation(summary = "Get operations by parameters", description = "Retrieve operations matching specific parameters.")
    @PostMapping("/getOperationByParams")
    public APIResponse getOperationByParams(
            @RequestBody @Valid
            @Parameter(description = "Request parameters in JSON format", required = true) String request) {
        return transactionServices.getOperationByParams(request);
    }
}
