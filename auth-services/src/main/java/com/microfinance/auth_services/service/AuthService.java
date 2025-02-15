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
        LOGGER.info("Generating token for user: {}", username);
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        LOGGER.info("Validating token: {}", token);
        jwtService.validateToken(token);
    }

    public APIResponse loginCollector(LoginRequest loginRequest) {
        LOGGER.info("Starting login process for collector: {}", loginRequest.getUserName());

        APIResponse response = new APIResponse();
        try {
            LOGGER.info("Fetching collector from database for username: {}", loginRequest.getUserName());
            Collecteur collecteur = authentificationRepository.findCollectorByNum(loginRequest.getUserName());

            if (collecteur == null) {
                LOGGER.warn("Collector not found: {}", loginRequest.getUserName());
                throw new CrudOperationException("Collector not found", Trame.ResponseCode.NOT_FOUND);
            }

            LOGGER.info("Validating password for collector: {}", loginRequest.getUserName());
            String hashedPassword = Encryption.hashPwd(loginRequest.getPassword()).toUpperCase();
            LOGGER.debug("Hashed password (input): {}", hashedPassword);
            LOGGER.debug("Stored password: {}", collecteur.getPassword());

            if (!hashedPassword.equals(collecteur.getPassword())) {
                LOGGER.warn("Invalid password for collector: {}", loginRequest.getUserName());
                throw new CrudOperationException("Invalid credentials, please try again", Trame.ResponseCode.ACCESS_DENIED);
            }

            if (collecteur.getIsLocked() == 1) {
                LOGGER.warn("Collector account is locked: {}", loginRequest.getUserName());
                throw new CrudOperationException("User has been blocked", Trame.ResponseCode.CONSTRAINT_ERROR);
            }

            LOGGER.info("Generating authentication token for collector: {}", loginRequest.getUserName());
            collecteur.setKey(generateToken(loginRequest.getUserName()));

            LOGGER.info("Collector login successful: {}", loginRequest.getUserName());
            response.setData(collecteur);
            response.setStatus(Trame.ResponseCode.SUCCESS);
            response.setMessage("Login successful");

        } catch (CrudOperationException e) {
            LOGGER.error("Login failed for collector {}: {}", loginRequest.getUserName(), e.getMessage());
            response.setStatus(e.getResponse().getStatus());
            response.setMessage(e.getResponse().getMessage());
        } catch (Exception e) {
            LOGGER.error("Unexpected error during collector login: {}", e.getMessage(), e);
            response.setStatus(Trame.ResponseCode.SERVER_ERROR);
            response.setMessage(e.getMessage());
        }

        return response;
    }

    public APIResponse loginUser(LoginRequest loginRequest) {
        LOGGER.info("Starting login process for user: {}", loginRequest.getUserName());

        APIResponse response = new APIResponse();
        try {
            LOGGER.info("Fetching user from database for username: {}", loginRequest.getUserName());
            CollectUser user = authentificationRepositoryUSer.findByUserName(loginRequest.getUserName());

            if (user == null) {
                LOGGER.warn("User not found: {}", loginRequest.getUserName());
                throw new CrudOperationException("User not found", Trame.ResponseCode.NOT_FOUND);
            }

            LOGGER.info("Validating password for user: {}", loginRequest.getUserName());
            String hashedPassword = Encryption.hashPwd(loginRequest.getPassword());
            LOGGER.debug("Hashed password (input): {}", hashedPassword);
            LOGGER.debug("Stored password: {}", user.getPassword());

            if (!hashedPassword.equals(user.getPassword())) {
                LOGGER.warn("Invalid password for user: {}", loginRequest.getUserName());
                throw new CrudOperationException("Invalid credentials", Trame.ResponseCode.ACCESS_DENIED);
            }

            if (!user.isActive()) {
                LOGGER.warn("User account is inactive/blocked: {}", loginRequest.getUserName());
                throw new CrudOperationException("User has been blocked", Trame.ResponseCode.CONSTRAINT_ERROR);
            }

            LOGGER.info("Generating authentication token for user: {}", loginRequest.getUserName());
            user.setKey(generateToken(loginRequest.getUserName()));

            LOGGER.info("User login successful: {}", loginRequest.getUserName());
            response.setData(user);
            response.setStatus(Trame.ResponseCode.SUCCESS);
            response.setMessage("Login successful");

        } catch (CrudOperationException e) {
            LOGGER.error("Login failed for user {}: {}", loginRequest.getUserName(), e.getMessage());
            response.setStatus(e.getResponse().getStatus());
            response.setMessage(e.getResponse().getMessage());
        } catch (Exception e) {
            LOGGER.error("Unexpected error during user login: {}", e.getMessage(), e);
            response.setStatus(Trame.ResponseCode.SERVER_ERROR);
            response.setMessage("An unexpected error occurred");
        }

        return response;
    }
}
