package com.microfinance.auth_services.service;


import com.microfinance.auth_services.dto.CollectUser;
import com.microfinance.auth_services.dto.Collecteur;
import com.microfinance.auth_services.dto.LoginRequest;
import com.microfinance.auth_services.repository.AuthentificationRepository;
import com.microfinance.auth_services.repository.AuthentificationRepositoryUSer;
import com.microfinance.auth_services.utils.APIResponse;
import com.microfinance.auth_services.utils.CrudOperationException;
import com.microfinance.auth_services.utils.Encryption;
import com.microfinance.auth_services.utils.Trame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthentificationRepository authentificationRepository;

    @Autowired
    private AuthentificationRepositoryUSer authentificationRepositoryUSer;

    @Autowired
    private JwtService jwtService;

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

    public APIResponse loginCollector(LoginRequest loginRequest) {
        APIResponse response = new APIResponse();
        try {
            LOGGER.info("Attempting login for collector: {}", loginRequest.getUserName());

            Collecteur collecteur = authentificationRepository.findCollectorByNum(loginRequest.getUserName());
            if (collecteur == null) {
                throw new CrudOperationException("Collector not found", Trame.ResponseCode.NOT_FOUND);
            }

            String hashedPassword = Encryption.hashPwd(loginRequest.getPassword()).toUpperCase();
            if (!hashedPassword.equals(collecteur.getPassword())) {
                throw new CrudOperationException("Invalid credentials you see please try again", Trame.ResponseCode.ACCESS_DENIED);
            }

            if (collecteur.getIsLocked() == 1) {
                throw new CrudOperationException("User has been blocked", Trame.ResponseCode.CONSTRAINT_ERROR);
            }

            collecteur.setKey(generateToken(loginRequest.getUserName()));
            response.setData(collecteur);
            response.setStatus(Trame.ResponseCode.SUCCESS);
            response.setMessage("Login successful");
        } catch (CrudOperationException e) {
            LOGGER.error("Error during login: {}", e.getMessage());
            response.setStatus(e.getResponse().getStatus());
            response.setMessage(e.getResponse().getMessage());
        }
        return response;
    }

    public APIResponse loginUser(LoginRequest loginRequest) {
        APIResponse response = new APIResponse();
        try {
            LOGGER.info("Attempting login for user: {}", loginRequest.getUserName());

            CollectUser user = authentificationRepositoryUSer.findByUserName(loginRequest.getUserName());
            if (user == null) {
                throw new CrudOperationException("User not found", Trame.ResponseCode.NOT_FOUND);
            }

            String hashedPassword = Encryption.hashPwd(loginRequest.getPassword());
            if (!hashedPassword.equals(user.getPassword())) {
                throw new CrudOperationException("Invalid credentials", Trame.ResponseCode.ACCESS_DENIED);
            }

            if (Boolean.TRUE.equals(user.isActive())) {
                throw new CrudOperationException("User has been blocked", Trame.ResponseCode.CONSTRAINT_ERROR);
            }

            user.setKey(generateToken(loginRequest.getUserName()));
            response.setData(user);
            response.setStatus(Trame.ResponseCode.SUCCESS);
            response.setMessage("Login successful");
        } catch (CrudOperationException e) {
            LOGGER.error("Error during login: {}", e.getMessage());
            response.setStatus(e.getResponse().getStatus());
            response.setMessage(e.getResponse().getMessage());
        }
        return response;
    }
}
