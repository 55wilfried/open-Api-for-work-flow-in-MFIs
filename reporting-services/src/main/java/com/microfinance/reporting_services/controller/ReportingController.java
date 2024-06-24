package com.microfinance.reporting_services.controller;

import com.microfinance.reporting_services.models.ClientCollecte;
import com.microfinance.reporting_services.models.OperationCollecte;
import com.microfinance.reporting_services.models.RequestTransactionByParams;
import com.microfinance.reporting_services.services.ReportingServices;
import com.microfinance.reporting_services.utils.APIResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins= {"*"}, maxAge = 3600, allowCredentials = "false")
@RequestMapping("/reporting")
@RestController
public class ReportingController {
  @Autowired
    ReportingServices reportingServices;
    @GetMapping("/number/{num}")
    public APIResponse findClientByNum(@PathVariable String num) {
        return reportingServices.getOperationByNum(num);
    }

    @GetMapping("/getOperationByParams/{params}")
    public APIResponse getOperationByParamsOperation(@RequestBody @Valid String request) {
        return reportingServices.getOperationByParamsOperation(request);
    }

    @GetMapping("/numClient/{numClient}")
    public APIResponse findClientByNumClient(@PathVariable String numClient) {
        return reportingServices.getClientById(numClient);
    }

    @GetMapping("/getClientByParams/{params}")
    public APIResponse getOperationByParams(@RequestBody @Valid String request) {
        return reportingServices.getOperationByParams(request);
    }
}
