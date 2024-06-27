package com.microfinance.transaction_services.controller;

import com.microfinance.transaction_services.service.TransactionServices;
import com.microfinance.transaction_services.utils.APIResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/transactions")
@RestController
@CrossOrigin(origins= {"*"}, maxAge = 3600, allowCredentials = "false")
public class TransactionController {
    @Autowired
    private TransactionServices transactionServices;


    @PostMapping("/makeOperation")
    public APIResponse addOperation(@RequestBody @Valid String operationCollecte) {
        return transactionServices.addOperation(operationCollecte);
    }

    @GetMapping("/allOperation")
    public APIResponse getAllClients() {
        return transactionServices.getAllClients();
    }

    @GetMapping("/number/{num}")
    public APIResponse findClientByNum(@PathVariable String num) {
        return transactionServices.getOperationByNum(num);
    }

    @GetMapping("/allTransactionByCodage/{codage}")
    public APIResponse getAllClientsByCodage(@PathVariable String codage) {
        return transactionServices. getAllOperationByCodage(codage);
    }

    @GetMapping("/allClientByCollector/{collector}")
    public APIResponse getAllClientsByCollector(@PathVariable String collector) {
        return transactionServices.getAllOperationByCol(collector);
    }

    @GetMapping("/getOperationByParams")
    public APIResponse getOperationByParams(@RequestBody @Valid String request) {
        return transactionServices.getOperationByParams(request);
    }
}
