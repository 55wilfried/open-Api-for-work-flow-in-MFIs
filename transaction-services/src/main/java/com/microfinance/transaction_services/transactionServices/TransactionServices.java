package com.microfinance.transaction_services.transactionServices;

import com.microfinance.transaction_services.models.OperationCollecte;
import com.microfinance.transaction_services.models.RequestTransactionByParams;
import com.microfinance.transaction_services.transactionRepository.TransactionRepository;
import com.microfinance.transaction_services.utils.APIResponse;
import com.microfinance.transaction_services.utils.CrudOperationException;
import com.microfinance.transaction_services.utils.Helpers;
import com.microfinance.transaction_services.utils.Trame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServices {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServices.class);
    @Autowired
    private  TransactionRepository transactionRepository;
    @Autowired
public Helpers helpers;
    public APIResponse addOperation(String operationCollecte) {
        APIResponse resp = new APIResponse();
        try {
            String num;
            String finalNum;
            OperationCollecte loginRequest = Trame.getRequestData(operationCollecte, OperationCollecte.class);
          String  lastNumop = transactionRepository.findMaxNumByCollector(loginRequest.getNumCol());
            if (lastNumop != null){
                 num = lastNumop.substring(7);
            }else{
                num = "000000";
            }
            if(loginRequest.getSenseOp() == "C"){
                finalNum = loginRequest.getNumCol() + "OPC" + helpers.generateCode(num, "000000");
            }else if(loginRequest.getSenseOp() == "D") {
                finalNum = loginRequest.getNumCol() + "OPD" + helpers.generateCode(num, "000000");
            }else{
                throw new CrudOperationException("The collector number given was not found ", Trame.ResponseCode.NOT_FOUND);
            }
            loginRequest.setNum(finalNum);
            transactionRepository.save(loginRequest);
            resp.setData(finalNum);
            resp.setStatus(Trame.ResponseCode.SUCCESS);
            return resp;
        } catch (CrudOperationException e){
            resp.setStatus(e.getResponse().getStatus());
            resp.setMessage(e.getResponse().getMessage());
        }
        return  resp;
    }


    public APIResponse getAllClients() {

        APIResponse resp = new APIResponse();
        LOGGER.info("Premier test du Logger avec Logstash");
        try{
            List<OperationCollecte> clientCollectes = transactionRepository.findAll();
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

    public APIResponse getAllOperationByCodage(String codage) {
        System.out.println("getAllClientByCodage");
        APIResponse resp = new APIResponse();
        System.out.println("getAllClientByCodage");
        try{
            List<OperationCollecte> clientCollectes =  transactionRepository.findAllByCodage(codage);
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


    public APIResponse getAllOperationByCol(String numcol) {

        APIResponse resp = new APIResponse();
        try{
            List<OperationCollecte> clientCollectes = transactionRepository.findAllOperationByCollector(numcol);
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
            OperationCollecte collecte =  transactionRepository.findByNum(num);
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
            List<OperationCollecte> clientCollectes = transactionRepository.findEntitiesByParamsAndDateRange(byParams.getStartDate(), byParams.getEndDate(), byParams.getNumCol(), byParams.getCodage(), byParams.getNumcli());
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
