package com.microfinance.reporting_services.service;

import com.microfinance.reporting_services.dto.ClientCollecte;
import com.microfinance.reporting_services.dto.OperationCollecte;
import com.microfinance.reporting_services.dto.RequestTransactionByParams;
import com.microfinance.reporting_services.reportingRepository.ClientReportRepository;
import com.microfinance.reporting_services.reportingRepository.TransactionReportingRepository;
import com.microfinance.reporting_services.utils.APIResponse;
import com.microfinance.reporting_services.utils.CrudOperationException;
import com.microfinance.reporting_services.utils.Trame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportingServices {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportingServices.class);
    @Autowired
    private ClientReportRepository clientReportRepository;
    @Autowired
    private TransactionReportingRepository transactionReportingRepository;


    public APIResponse getClientById(String num) {
        APIResponse resp = new APIResponse();
        try {
            System.out.println("num parameter: " + num);
            ClientCollecte collecte = clientReportRepository.findByNum(num);

            if (collecte != null){
                resp.setData(collecte);
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                return  resp;
            }else{
                throw new CrudOperationException("The Client not found ", Trame.ResponseCode.NOT_FOUND);
            }

        } catch (CrudOperationException e){
            resp.setStatus(e.getResponse().getStatus());
            resp.setMessage(e.getResponse().getMessage());
        }
        return  resp;
    }




    public APIResponse getOperationByParams(String byParam) {
        APIResponse resp = new APIResponse();
        try{
            RequestTransactionByParams byParams = Trame.getRequestData(byParam, RequestTransactionByParams.class);
            List<ClientCollecte> clientCollectes = clientReportRepository.findEntitiesByParamsAndDateRange(byParams.getStartDate(), byParams.getEndDate(), byParams.getNumCol(), byParams.getCodage(), byParams.getNumcli());
            if(clientCollectes != null){
                resp.setData(clientCollectes);
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                return  resp;
            }else{
                throw new CrudOperationException("The List is Empty", Trame.ResponseCode.NOT_FOUND);
            }
        }catch (CrudOperationException e){
            resp.setStatus(e.getResponse().getStatus());
            resp.setMessage(e.getResponse().getMessage());
        }
        return resp;
    }

    public APIResponse getOperationByNum(String num) {
        APIResponse resp = new APIResponse();
        try {
            System.out.println("num parameter: " + num);
            OperationCollecte collecte =  transactionReportingRepository.findByNum(num);
            if (collecte != null){
                resp.setData(collecte);
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                return  resp;
            }else{
                throw new CrudOperationException("The Client not found ", Trame.ResponseCode.NOT_FOUND);
            }

        } catch (CrudOperationException e){
            resp.setStatus(e.getResponse().getStatus());
            resp.setMessage(e.getResponse().getMessage());
        }
        return  resp;
    }

    public APIResponse getOperationByParamsOperation(String byParam) {

        APIResponse resp = new APIResponse();
        try{
            RequestTransactionByParams byParams = Trame.getRequestData(byParam, RequestTransactionByParams.class);
            List<OperationCollecte> clientCollectes = transactionReportingRepository.findEntitiesByParamsAndDateRange(byParams.getStartDate(), byParams.getEndDate(), byParams.getNumCol(), byParams.getCodage(), byParams.getNumcli());
            if(clientCollectes != null){
                resp.setData(clientCollectes);
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                return  resp;
            }else{
                throw new CrudOperationException("The List is Empty", Trame.ResponseCode.NOT_FOUND);
            }
        }catch (CrudOperationException e){
            resp.setStatus(e.getResponse().getStatus());
            resp.setMessage(e.getResponse().getMessage());
        }
        return resp;
    }

}
