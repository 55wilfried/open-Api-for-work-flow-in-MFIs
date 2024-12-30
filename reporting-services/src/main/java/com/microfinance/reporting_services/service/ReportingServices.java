package com.microfinance.reporting_services.service;

import com.microfinance.reporting_services.dto.ClientCollecte;
import com.microfinance.reporting_services.dto.OperationCollecte;
import com.microfinance.reporting_services.dto.RequestTransactionByParams;
import com.microfinance.reporting_services.reportingRepository.ClientReportRepository;
import com.microfinance.reporting_services.reportingRepository.TransactionReportingRepository;
import com.microfinance.reporting_services.utils.APIResponse;
import com.microfinance.reporting_services.utils.CrudOperationException;
import com.microfinance.reporting_services.utils.Trame;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowCredentials = "true")
@Service
public class ReportingServices {

    @Autowired
    private ClientReportRepository clientReportRepository;

    @Autowired
    private TransactionReportingRepository transactionReportingRepository;

    /**
     * Get client by client ID.
     *
     * @param num The client number.
     * @return The client data if found.
     */
    @Operation(summary = "Get client by client ID",
            description = "Fetches the client details by their unique client number.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Client not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            })
    public APIResponse getClientById(String num) {
        APIResponse resp = new APIResponse();
        try {
            System.out.println("num parameter: " + num);
            ClientCollecte collecte = clientReportRepository.findByNum(num);

            if (collecte != null){
                resp.setData(collecte);
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                return  resp;
            } else {
                throw new CrudOperationException("The Client not found ", Trame.ResponseCode.NOT_FOUND);
            }

        } catch (CrudOperationException e){
            resp.setStatus(e.getResponse().getStatus());
            resp.setMessage(e.getResponse().getMessage());
        }
        return  resp;
    }

    /**
     * Get operations by provided parameters.
     *
     * @param byParam The parameter string for searching transactions.
     * @return A list of transactions matching the parameters.
     */
    @Operation(summary = "Get transactions by provided parameters",
            description = "Fetches transactions based on start date, end date, collector number, codage, and client number.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Transactions found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "No transactions found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            })
    public APIResponse getOperationByParams(String byParam) {
        APIResponse resp = new APIResponse();
        try {
            RequestTransactionByParams byParams = Trame.getRequestData(byParam, RequestTransactionByParams.class);
            List<ClientCollecte> clientCollectes = clientReportRepository.findEntitiesByParamsAndDateRange(
                    byParams.getStartDate(), byParams.getEndDate(), byParams.getNumCol(), byParams.getCodage(), byParams.getNumcli());
            if (clientCollectes != null) {
                resp.setData(clientCollectes);
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                return resp;
            } else {
                throw new CrudOperationException("The List is Empty", Trame.ResponseCode.NOT_FOUND);
            }
        } catch (CrudOperationException e) {
            resp.setStatus(e.getResponse().getStatus());
            resp.setMessage(e.getResponse().getMessage());
        }
        return resp;
    }

    /**
     * Get operation by operation number.
     *
     * @param num The operation number.
     * @return The operation details if found.
     */
    @Operation(summary = "Get operation details by operation number",
            description = "Fetches the operation details based on the operation number.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operation found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Operation not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            })
    public APIResponse getOperationByNum(String num) {
        APIResponse resp = new APIResponse();
        try {
            System.out.println("num parameter: " + num);
            OperationCollecte collecte = transactionReportingRepository.findByNum(num);
            if (collecte != null) {
                resp.setData(collecte);
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                return  resp;
            } else {
                throw new CrudOperationException("The Client not found ", Trame.ResponseCode.NOT_FOUND);
            }
        } catch (CrudOperationException e) {
            resp.setStatus(e.getResponse().getStatus());
            resp.setMessage(e.getResponse().getMessage());
        }
        return resp;
    }

    /**
     * Get operations by operation parameters.
     *
     * @param byParam The parameter string for searching operations.
     * @return A list of operations matching the parameters.
     */
    @Operation(summary = "Get operations by operation parameters",
            description = "Fetches operations based on parameters like start date, end date, collector number, codage, and client number.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operations found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "No operations found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
            })
    public APIResponse getOperationByParamsOperation(String byParam) {
        APIResponse resp = new APIResponse();
        try {
            RequestTransactionByParams byParams = Trame.getRequestData(byParam, RequestTransactionByParams.class);
            List<OperationCollecte> clientCollectes = transactionReportingRepository.findEntitiesByParamsAndDateRange(
                    byParams.getStartDate(), byParams.getEndDate(), byParams.getNumCol(), byParams.getCodage(), byParams.getNumcli());
            if (clientCollectes != null) {
                resp.setData(clientCollectes);
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                return resp;
            } else {
                throw new CrudOperationException("The List is Empty", Trame.ResponseCode.NOT_FOUND);
            }
        } catch (CrudOperationException e) {
            resp.setStatus(e.getResponse().getStatus());
            resp.setMessage(e.getResponse().getMessage());
        }
        return resp;
    }
}
