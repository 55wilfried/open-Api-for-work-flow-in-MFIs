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
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

/**
 * Service class for handling operations related to collectors and collect users.
 */
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowCredentials = "true")
@Service
public class UserServices {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServices.class);

    @Autowired
    private UserCollectRepository userCollectRepository;

    @Autowired
    private UserCollectorRepository userCollectorRepository;

    @Autowired
    private Helpers helpers;

    /**
     * Retrieves all collectors from the database.
     *
     * @return an APIResponse containing the list of all collectors.
     */
    public APIResponse getAllCollecteur() {
        APIResponse resp = new APIResponse();
        LOGGER.info("Fetching all collectors.");
        try {
            List<Collecteur> clientCollectes = userCollectorRepository.findAll();
            if (clientCollectes != null) {
                resp.setData(clientCollectes);
                resp.setMessage("SUCCESS");
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
     * Retrieves all collect users from the database.
     *
     * @return an APIResponse containing the list of all collect users.
     */
    public APIResponse getAllCollectUser() {
        APIResponse resp = new APIResponse();
        LOGGER.info("Fetching all collect users.");
        try {
            List<CollectUser> clientCollectes = userCollectRepository.findAll();
            if (clientCollectes != null) {
                resp.setData(clientCollectes);
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                resp.setMessage("SUCCESS");
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
     * Retrieves collectors by their codage.
     *
     * @param codage the codage of the collectors to fetch.
     * @return an APIResponse containing the list of collectors matching the codage.
     */
    public APIResponse getAllCollectorByCodage(String codage) {
        APIResponse resp = new APIResponse();
        LOGGER.info("Fetching collectors by codage: {}", codage);
        try {
            List<Collecteur> clientCollectes = userCollectorRepository.findAllByCodage(codage);
            if (clientCollectes != null) {
                resp.setData(clientCollectes);
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                resp.setMessage("SUCCESS");
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
     * Retrieves collect users by their codage.
     *
     * @param codage the codage of the collect users to fetch.
     * @return an APIResponse containing the list of collect users matching the codage.
     */
    public APIResponse getAllCollectUserByCodage(String codage) {
        APIResponse resp = new APIResponse();
        LOGGER.info("Fetching collect users by codage: {}", codage);
        try {
            List<CollectUser> clientCollectes = userCollectRepository.findAllByCodage(codage);
            if (clientCollectes != null) {
                resp.setData(clientCollectes);
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                resp.setMessage("SUCCESS");
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
     * Retrieves a collector by their unique identifier (num).
     *
     * @param num the unique identifier (num) of the collector to fetch.
     * @return an APIResponse containing the collector details.
     */
    public APIResponse getCollectorById(String num) {
        APIResponse resp = new APIResponse();
        try {
            LOGGER.info("Fetching collector with num: {}", num);
            Collecteur collecte = userCollectorRepository.findByNum(num);

            if (collecte != null) {
                resp.setData(collecte);
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                resp.setMessage("SUCCESS");
                return resp;
            } else {
                throw new CrudOperationException("The Client not found", Trame.ResponseCode.NOT_FOUND);
            }
        } catch (CrudOperationException e) {
            resp.setStatus(e.getResponse().getStatus());
            resp.setMessage(e.getResponse().getMessage());
        }
        return resp;
    }

    /**
     * Retrieves a collect user by their unique username.
     *
     * @param userName the username of the collect user to fetch.
     * @return an APIResponse containing the collect user details.
     */
    public APIResponse getCollectUserById(String userName) {
        APIResponse resp = new APIResponse();
        try {
            LOGGER.info("Fetching collect user with username: {}", userName);
            CollectUser collecte = userCollectRepository.findByUserName(userName);

            if (collecte != null) {
                resp.setData(collecte);
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                resp.setMessage("SUCCESS");
                return resp;
            } else {
                throw new CrudOperationException("The Client not found", Trame.ResponseCode.NOT_FOUND);
            }
        } catch (CrudOperationException e) {
            resp.setStatus(e.getResponse().getStatus());
            resp.setMessage(e.getResponse().getMessage());
        }
        return resp;
    }

    /**
     * Adds a new collector.
     *
     * @param collecteur JSON string representing the collector to be added.
     * @return an APIResponse containing the collector details.
     */
    public APIResponse addCollector(String collecteur) {
        Collecteur clientCollecte = Trame.getRequestData(collecteur, Collecteur.class);
        APIResponse resp = new APIResponse();
        try {
            String nextClientNumber = userCollectorRepository.findMaxNum();
            if (nextClientNumber != null) {
                String numCol = helpers.incrementCollectorNumber(nextClientNumber);
                clientCollecte.setNum(numCol);
                userCollectorRepository.save(clientCollecte);
                resp.setData(nextClientNumber);
                resp.setMessage("SUCCESS");
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                return resp;
            } else {
                throw new CrudOperationException("The collector number given was not found", Trame.ResponseCode.NOT_FOUND);
            }
        } catch (CrudOperationException e) {
            resp.setStatus(e.getResponse().getStatus());
            resp.setMessage(e.getResponse().getMessage());
        }
        return resp;
    }

    /**
     * Adds a new collect user.
     *
     * @param collectUser the collect user to be added.
     * @return the added collect user.
     */
    public CollectUser addCollectUser(CollectUser collectUser) {
        return userCollectRepository.save(collectUser);
    }

    /**
     * Updates an existing collector's information.
     *
     * @param num        the unique identifier (num) of the collector to be updated.
     * @param collecteur JSON string representing the updated collector details.
     * @return an APIResponse containing the updated collector details.
     */
    public APIResponse updateCollector(String num, String collecteur) {
        APIResponse resp = new APIResponse();
        Collecteur clientCollecte = Trame.getRequestData(collecteur, Collecteur.class);
        try {
            Collecteur client = this.userCollectorRepository.findByNum(num);
            if (client != null) {
                resp.setData(userCollectorRepository.save(client));
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                resp.setMessage("SUCCESS");
                return resp;
            } else {
                throw new CrudOperationException("The collector number given was not found", Trame.ResponseCode.NOT_FOUND);
            }
        } catch (CrudOperationException e) {
            resp.setStatus(e.getResponse().getStatus());
            resp.setMessage(e.getResponse().getMessage());
        }
        return resp;
    }

    /**
     * Updates an existing collect user's information.
     *
     * @param userName   the username of the collect user to be updated.
     * @param collectUser JSON string representing the updated collect user details.
     * @return an APIResponse containing the updated collect user details.
     */
    public APIResponse updateUserCollect(String userName, String collectUser) {
        APIResponse resp = new APIResponse();
        CollectUser clientCollecte = Trame.getRequestData(collectUser, CollectUser.class);
        try {
            CollectUser client = this.userCollectRepository.findByUserName(userName);
            if (client != null) {
                resp.setData(userCollectRepository.save(clientCollecte));
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                resp.setMessage("SUCCESS");
                return resp;
            } else {
                throw new CrudOperationException("The collector number given was not found", Trame.ResponseCode.NOT_FOUND);
            }
        } catch (CrudOperationException e) {
            resp.setStatus(e.getResponse().getStatus());
            resp.setMessage(e.getResponse().getMessage());
        }
        return resp;
    }
}
