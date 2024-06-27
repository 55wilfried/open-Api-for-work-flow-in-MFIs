package com.microfinance.users_services.service;

import com.microfinance.users_services.dto.CollectUser;
import com.microfinance.users_services.dto.Collecteur;
import com.microfinance.users_services.userRepository.UserCollectRepository;
import com.microfinance.users_services.userRepository.UserCollectorRepository;
import com.microfinance.users_services.utils.APIResponse;
import com.microfinance.users_services.utils.CrudOperationException;
import com.microfinance.users_services.utils.Helpers;
import com.microfinance.users_services.utils.Trame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServices.class);
    @Autowired
    private UserCollectRepository userCollectRepository;
    @Autowired
    private UserCollectorRepository userCollectorRepository;
    @Autowired
    public Helpers helpers;

    public APIResponse getAllCollecteur() {


        APIResponse resp = new APIResponse();
        LOGGER.info("Premier test du Logger avec Logstash");
        try{
            List<Collecteur> clientCollectes = userCollectorRepository.findAll();
            if(clientCollectes != null){
                resp.setData(clientCollectes);
                resp.setMessage("SUCCESS");
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

    public APIResponse getAllCollectUser() {
        APIResponse resp = new APIResponse();
        LOGGER.info("Premier test du Logger avec Logstash");
        try{
            List<CollectUser> clientCollectes =  userCollectRepository.findAll();
            if(clientCollectes != null){
                resp.setData(clientCollectes);
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                resp.setMessage("SUCCESS");
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

    public APIResponse getAllCollectorByCodage(String codage) {

        APIResponse resp = new APIResponse();
        System.out.println("getAllClientByCodage");

        try{
            List<Collecteur> clientCollectes =  userCollectorRepository.findAllByCodage(codage);
            if(clientCollectes != null){
                resp.setData(clientCollectes);
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                resp.setMessage("SUCCESS");
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

    public APIResponse getAllCollectUserByCodage(String codage) {
        APIResponse resp = new APIResponse();
        System.out.println("getAllClientByCodage");
        try{
            List<CollectUser> clientCollectes =  userCollectRepository.findAllByCodage(codage);
            if(clientCollectes != null){
                resp.setData(clientCollectes);
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                resp.setMessage("SUCCESS");
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

    public APIResponse getCollectorById(String num) {
        APIResponse resp = new APIResponse();
        try {
            System.out.println("num parameter: " + num);
            Collecteur collecte =  userCollectorRepository.findByNum(num);

            if (collecte != null){
                resp.setData(collecte);
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                resp.setMessage("SUCCESS");
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

    public APIResponse getCollectUserById(String userName) {
        APIResponse resp = new APIResponse();
        try {
            System.out.println("num parameter: " + userName);
            CollectUser collecte =  userCollectRepository.findByUserName(userName);

            if (collecte != null){
                resp.setData(collecte);
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                resp.setMessage("SUCCESS");
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

    public APIResponse addCollector(String collecteur) {
        Collecteur clientCollecte = Trame.getRequestData(collecteur, Collecteur.class);
        APIResponse resp = new APIResponse();
        try {
            String nextClientNumber = userCollectorRepository.findMaxNum();
            if (nextClientNumber != null){
                String numCol =  helpers.incrementCollectorNumber(nextClientNumber);
                clientCollecte.setNum(numCol);
                userCollectorRepository.save(clientCollecte);
                resp.setData(nextClientNumber);
                resp.setMessage("SUCCESS");
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                return  resp;
            }else{

            }
            throw new CrudOperationException("The collector number given was not found ", Trame.ResponseCode.NOT_FOUND);
        } catch (CrudOperationException e){
            resp.setStatus(e.getResponse().getStatus());
            resp.setMessage(e.getResponse().getMessage());
        }
        return  resp;
    }

    public CollectUser addCollectUser(CollectUser collectUser) {
        return userCollectRepository.save(collectUser);
    }



    public APIResponse updateCollector(String num, String collecteur) {
        APIResponse resp = new APIResponse();
        Collecteur clientCollecte = Trame.getRequestData(collecteur, Collecteur.class);
        try {
            Collecteur client = this.userCollectorRepository.findByNum(num);
            if (client != null){
                resp.setData(userCollectorRepository.save(client));
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                resp.setMessage("SUCCESS");
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

    public APIResponse updateUserCollect(String userName, String collectUser) {
        APIResponse resp = new APIResponse();
        CollectUser clientCollecte = Trame.getRequestData(collectUser, CollectUser.class);
        try {
            CollectUser client = this.userCollectRepository.findByUserName(userName);
            if (client != null){
                resp.setData(userCollectRepository.save(clientCollecte));
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                resp.setMessage("SUCCESS");
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
