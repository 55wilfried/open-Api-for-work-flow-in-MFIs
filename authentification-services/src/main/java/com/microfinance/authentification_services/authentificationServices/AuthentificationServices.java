package com.microfinance.authentification_services.authentificationServices;

import com.microfinance.authentification_services.authentificationRepository.AuthentificationRepository;
import com.microfinance.authentification_services.authentificationRepository.AuthentificationRepositoryUSer;
import com.microfinance.authentification_services.repositoryModel.CollectUser;
import com.microfinance.authentification_services.repositoryModel.Collecteur;
import com.microfinance.authentification_services.repositoryModel.LoginRequest;
import com.microfinance.authentification_services.utils.APIResponse;
import com.microfinance.authentification_services.utils.CrudOperationException;
import com.microfinance.authentification_services.utils.Encryption;
import com.microfinance.authentification_services.utils.Trame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthentificationServices {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthentificationServices.class);
    @Autowired
    private AuthentificationRepository authentificationRepository;
    @Autowired
    private AuthentificationRepositoryUSer authentificationRepositoryUSer;
    @Autowired
    private JwtServices jwtServices;


    public String generateToken(String username) {
        return jwtServices.generateToken(username);
    }

    public void validateToken(String token) {
        jwtServices.validateToken(token);
    }




    public APIResponse loginCollector(String login) {
        LOGGER.info("Premier test du Logger avec Logstash");
        System.out.println("getAllClientByCodage" );
        APIResponse resp = new APIResponse();
        try {
            LoginRequest loginRequest = Trame.getRequestData(login, LoginRequest.class);
            System.out.println("getAllClientByCodage1" );
            var password = loginRequest.getPassword();
            System.out.println("getAllClientByCodage1" + password);
            var userCode = loginRequest.getUserName();
            Collecteur collecteur = authentificationRepository.findCollectorByNum(userCode);
            System.out.println("getAllClientByCodage" + collecteur);
            String hashedPassword = Encryption.hashPwd(password);
            if (collecteur != null && hashedPassword.equals(collecteur.getPassword())) {
                if (collecteur.getIsLocked() == 1) {
                    throw new CrudOperationException("USER HAS BEEN BLOCKED", Trame.ResponseCode.CONSTRAINT_ERROR);
                }else {
                    collecteur.setKey(generateToken(userCode));
                   resp.setData(collecteur);
                   resp.setStatus(Trame.ResponseCode.SUCCESS);
                    return resp;
                }
            }else{
                throw new CrudOperationException("INVALID CREDENTIALS", Trame.ResponseCode.ACCESS_DENIED);
            }
        } catch (CrudOperationException e) {
            resp.setStatus(e.getResponse().getStatus());
            resp.setMessage(e.getResponse().getMessage());
        }
        return resp;
    }

    public APIResponse loginUser(String login) {
        APIResponse resp = new APIResponse();
        try {
            LoginRequest loginRequest = Trame.getRequestData(login, LoginRequest.class);
            var password = loginRequest.getPassword();
            var userCode = loginRequest.getUserName();
            CollectUser collecteur = authentificationRepositoryUSer.findByUserName(userCode);
            String hashedPassword = Encryption.hashPwd(password);
            if (collecteur != null && hashedPassword.equals(collecteur.getPassword())) {
                if (Boolean.TRUE.equals(collecteur.isActive())) {
                    throw new CrudOperationException("USER HAS BEEN BLOCKED", Trame.ResponseCode.CONSTRAINT_ERROR);
                }else {
                    collecteur.setKey(generateToken(userCode));
                    resp.setData(collecteur);
                    resp.setStatus(Trame.ResponseCode.SUCCESS);
                }
            }else{
                throw new CrudOperationException("INVALID CREDENTIALS", Trame.ResponseCode.ACCESS_DENIED);
            }
        } catch (CrudOperationException e) {
            resp.setStatus(e.getResponse().getStatus());
            resp.setMessage(e.getResponse().getMessage());
        }
        return resp;
    }




}
