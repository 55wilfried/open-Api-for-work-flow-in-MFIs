package com.microfinance.client_services.services;

import com.microfinance.client_services.models.ClientCollecte;
import com.microfinance.client_services.clientRepository.ClientRepository;
import com.microfinance.client_services.utils.APIResponse;
import com.microfinance.client_services.utils.CrudOperationException;
import com.microfinance.client_services.utils.Trame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServices {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientServices.class);
    @Autowired
    private ClientRepository clientRepository;
    ClientCollecte clientCollecte = new ClientCollecte();

    public APIResponse getAllClients() {
        APIResponse resp = new APIResponse();
        LOGGER.info("Premier test du Logger avec Logstash");
        try{
            List<ClientCollecte> clientCollectes = clientRepository.findAll();
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

    public APIResponse getAllClientByCol(String numcol) {
        APIResponse resp = new APIResponse();
        try{
            List<ClientCollecte> clientCollectes =  clientRepository.findAllByCollector(numcol);
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

    public APIResponse getAllClientByCodage(String codage) {
        APIResponse resp = new APIResponse();
        System.out.println("getAllClientByCodage");

        try{
            List<ClientCollecte> clientCollectes =  clientRepository.findAllByCodage(codage);
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

    public APIResponse getClientById(String num) {
        APIResponse resp = new APIResponse();
        try {
            System.out.println("num parameter: " + num);
            ClientCollecte collecte =  clientRepository.findByNum(num);

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


    public APIResponse getClientByName(String name) {
        APIResponse resp = new APIResponse();
        try {
            ClientCollecte collecte = clientRepository.findByName(name);

            if (collecte != null){
                resp.setData(collecte);
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                return  resp;
            }else{
                throw new CrudOperationException("Client not found ", Trame.ResponseCode.NOT_FOUND);
            }

        } catch (CrudOperationException e){
            resp.setStatus(e.getResponse().getStatus());
            resp.setMessage(e.getResponse().getMessage());
        }
        return  resp;
    }

    public APIResponse addClient(String client) {
        ClientCollecte clientCollecte = Trame.getRequestData(client, ClientCollecte.class);
        APIResponse resp = new APIResponse();
        try {
            String nextClientNumber = clientRepository.getNextClientNumber(clientCollecte.getNumCol());
            if (nextClientNumber != null){

                clientCollecte.setNum(nextClientNumber);
                clientRepository.save(clientCollecte);
                resp.setData(nextClientNumber);
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                return  resp;
            }else{
                throw new CrudOperationException("The collector number given was not found ", Trame.ResponseCode.NOT_FOUND);
            }

        } catch (CrudOperationException e){
            resp.setStatus(e.getResponse().getStatus());
            resp.setMessage(e.getResponse().getMessage());
        }
        return  resp;
    }

    public APIResponse updateClientPassword(String num, String password) {
        APIResponse resp = new APIResponse();
        try {
            ClientCollecte client = this.clientRepository.findByNum(num);
            if (client != null){

                clientCollecte.setPIN(password);
                resp.setData( clientRepository.save(clientCollecte));
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                return  resp;
            }else{
                throw new CrudOperationException("The collector number given was not found ", Trame.ResponseCode.NOT_FOUND);
            }

        } catch (CrudOperationException e){
            resp.setStatus(e.getResponse().getStatus());
            resp.setMessage(e.getResponse().getMessage());
        }
        return  resp;
    }

}