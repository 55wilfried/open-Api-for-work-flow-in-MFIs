package com.microfinance.client_services.services;

import com.microfinance.client_services.clientRepository.AuthentificationRepository;
import com.microfinance.client_services.clientRepository.ClientRepository;
import com.microfinance.client_services.kafka.FailedRequestProducer;
import com.microfinance.client_services.models.ClientCollecte;
import com.microfinance.client_services.models.Collecteur;
import com.microfinance.client_services.models.FailedRequest;
import com.microfinance.client_services.utils.APIResponse;
import com.microfinance.client_services.utils.CrudOperationException;
import com.microfinance.client_services.utils.Encryption;
import com.microfinance.client_services.utils.Trame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowCredentials = "true")
public class ClientServices {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientServices.class);

    @Autowired
    private ClientRepository clientRepository;
    ClientCollecte clientCollecte = new ClientCollecte();
    @Autowired
    private FailedRequestProducer failedRequestProducer;


    private void logFailure(String methodName, List<String> payload, Exception e) {
        FailedRequest failedRequest = new FailedRequest(methodName.toLowerCase(), payload, e.getMessage());
        failedRequestProducer.sendFailedRequest(failedRequest);
    }
    @Autowired
    private AuthentificationRepository authentificationRepository;

    /**
     * Get all clients.
     *
     * @return APIResponse containing the list of all clients.
     */
    @Operation(
            summary = "Get all clients",
            description = "Fetches the list of all clients.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved clients list",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = APIResponse.class))),
                    @ApiResponse(responseCode = "404", description = "No clients found",
                            content = @Content(mediaType = "application/json"))
            }
    )
    public APIResponse getAllClients() {
        APIResponse resp = new APIResponse();
        LOGGER.info("Premier test du Logger avec Logstash");
        try{
            List<ClientCollecte> clientCollectes = clientRepository.findAll();
            if(clientCollectes != null){
                resp.setData(clientCollectes);
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                resp.setMessage("SUCCESS");
                return  resp;
            }else{
                throw new CrudOperationException("The List is Empty", Trame.ResponseCode.NOT_FOUND);
            }
        }catch (CrudOperationException e){
            logFailure("getAllClients", Collections.singletonList(""), e);
            resp.setStatus(e.getResponse().getStatus());
            resp.setMessage(e.getResponse().getMessage());
        }
        return resp;
    }

    /**
     * Get all clients by collector number.
     *
     * @param numcol The collector number.
     * @return APIResponse containing the list of clients assigned to the specified collector.
     */
    @Operation(
            summary = "Get all clients by collector number",
            description = "Fetches the list of clients assigned to the given collector number.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved clients by collector",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = APIResponse.class))),
                    @ApiResponse(responseCode = "404", description = "No clients found for the specified collector",
                            content = @Content(mediaType = "application/json"))
            }
    )
    public APIResponse getAllClientByCol(String numcol) {
        APIResponse resp = new APIResponse();
        try{
            List<ClientCollecte> clientCollectes =  clientRepository.findAllByCollector(numcol);
            if(clientCollectes != null){
                resp.setData(clientCollectes);
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                resp.setMessage("SUCCESS");
                return  resp;
            }else{
                throw new CrudOperationException("The List is Empty", Trame.ResponseCode.NOT_FOUND);
            }
        }catch (CrudOperationException e){
            logFailure("getAllClientByCol", Collections.singletonList(numcol), e);
            resp.setStatus(e.getResponse().getStatus());
            resp.setMessage(e.getResponse().getMessage());
        }
        return resp;
    }

    /**
     * Get all clients by codage.
     *
     * @param codage The codage (coding) identifier.
     * @return APIResponse containing the list of clients with the specified codage.
     */
    @Operation(
            summary = "Get all clients by codage",
            description = "Fetches the list of clients with the specified codage.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved clients by codage",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = APIResponse.class))),
                    @ApiResponse(responseCode = "404", description = "No clients found with the specified codage",
                            content = @Content(mediaType = "application/json"))
            }
    )
    public APIResponse getAllClientByCodage(String codage) {
        APIResponse resp = new APIResponse();
        try{
            List<ClientCollecte> clientCollectes =  clientRepository.findAllByCodage(codage);
            if(clientCollectes != null){
                resp.setData(clientCollectes);
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                resp.setMessage("SUCCESS");
                return  resp;
            }else{
                throw new CrudOperationException("The List is Empty", Trame.ResponseCode.NOT_FOUND);
            }
        }catch (CrudOperationException e){
            logFailure("getAllClientByCodage", Collections.singletonList(codage), e);
            resp.setStatus(e.getResponse().getStatus());
            resp.setMessage(e.getResponse().getMessage());
        }
        return resp;
    }

    /**
     * Get client by client number.
     *
     * @param num The client number.
     * @return APIResponse containing the details of the client with the specified number.
     */
    @Operation(
            summary = "Get client by client number",
            description = "Fetches the client details for the given client number.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved client",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = APIResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Client not found",
                            content = @Content(mediaType = "application/json"))
            }
    )
    public APIResponse getClientById(String num) {
        APIResponse resp = new APIResponse();
        try {
            // Assuming findByNum returns Optional<ClientCollecte>
            Optional<ClientCollecte> collecteOpt = clientRepository.findByNum(num);

            if (collecteOpt.isPresent()) {
                ClientCollecte collecte = collecteOpt.get();
                resp.setData(collecte);
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                resp.setMessage("SUCCESS");
            } else {
                // If the client is not found, throw a custom exception
                throw new CrudOperationException("The Client not found", Trame.ResponseCode.NOT_FOUND);
            }
        } catch (CrudOperationException e) {
            logFailure("getClientById", Collections.singletonList(num), e);
            resp.setStatus(e.getResponse().getStatus());
            resp.setMessage(e.getResponse().getMessage());
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            logFailure("getClientById", Collections.singletonList(num), e);
            resp.setStatus(Trame.ResponseCode.SERVER_ERROR);
            resp.setMessage("An unexpected error occurred");
        }
        return resp;
    }
    /**
     * Get client by client name.
     *
     * @param name The client name.
     * @return APIResponse containing the details of the client with the specified name.
     */
    @Operation(
            summary = "Get client by client name",
            description = "Fetches the client details for the given client name.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved client",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = APIResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Client not found",
                            content = @Content(mediaType = "application/json"))
            }
    )
    public APIResponse getClientByName(String name) {
        LOGGER.info("Request to find client by name: " + name);  // Adding more detailed logging
        APIResponse resp = new APIResponse();
        try {
            Optional<ClientCollecte> optionalCollecte = clientRepository.findByName(name);  // Returns Optional<ClientCollecte>
            LOGGER.info("Client found by name: " + optionalCollecte.orElse(null));  // Log the result

            if (optionalCollecte.isPresent()) {  // Check if Optional contains a value
                resp.setData(optionalCollecte.get());  // Get the client if present
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                resp.setMessage("Client found successfully");
            } else {
                resp.setStatus(Trame.ResponseCode.NOT_FOUND);
                resp.setMessage("Client not found");
                resp.setData(null);  // Set data to null instead of empty string
                throw new CrudOperationException("Client not found", Trame.ResponseCode.NOT_FOUND);
            }
        } catch (CrudOperationException e) {
            LOGGER.error("Error finding client by name: " + name, e);  // Logging the error
            logFailure("getclientbyname", Collections.singletonList(name), e);
            resp.setStatus(e.getResponse().getStatus());
            resp.setMessage(e.getResponse().getMessage());
        }
        return resp;
    }
    /**
     * Add a new client.
     *
     * @param client JSON string representing the client data to be added.
     * @return APIResponse containing the next client number if successful.
     */
    @Operation(
            summary = "Add new client",
            description = "Adds a new client based on the provided client data.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully added client",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = APIResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Collector number not found",
                            content = @Content(mediaType = "application/json"))
            }
    )
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

    /**
     * Update client password.
     *
     * @param num The client number.
     * @param password The new password for the client.
     * @return APIResponse confirming the update.
     */
    @Operation(
            summary = "Update client password",
            description = "Updates the password for the specified client number.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated password",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = APIResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Client not found",
                            content = @Content(mediaType = "application/json"))
            }
    )
    public APIResponse updateClientPassword(String num, String password) {
        APIResponse resp = new APIResponse();
        try {
            Optional<ClientCollecte> optionalClient = this.clientRepository.findByNum(num);  // Returns Optional<ClientCollecte>

            if (optionalClient.isPresent()) {  // Check if Optional contains a value
                ClientCollecte client = optionalClient.get();  // Unwrap the client
                client.setPIN(password);  // Set the new PIN

                resp.setData(clientRepository.save(client));  // Save the updated client
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                resp.setMessage("Password updated successfully");
            } else {
                throw new CrudOperationException("The collector number given was not found", Trame.ResponseCode.NOT_FOUND);
            }
        } catch (CrudOperationException e) {
            logFailure("updateClientPassword", Arrays.asList(num, password), e);
            resp.setStatus(e.getResponse().getStatus());
            resp.setMessage(e.getResponse().getMessage());
        } catch (Exception e) {
            LOGGER.error("Unexpected error", e);  // Logging for unexpected errors
            resp.setStatus(Trame.ResponseCode.SERVER_ERROR);
            resp.setMessage("An unexpected error occurred while updating the password");
        }
        return resp;
    }


    public APIResponse loginCollector(String username, String password) {
        LOGGER.info("Starting login process for collector: {}", username);
        APIResponse response = new APIResponse();

        try {
            LOGGER.info("Fetching collector from database for username: {}", username);
            Collecteur collecteur = authentificationRepository.findCollectorByNum(username);

            if (collecteur == null) {
                LOGGER.warn("Collector not found: {}", username);
                throw new CrudOperationException("Collector not found", Trame.ResponseCode.NOT_FOUND);
            }

            LOGGER.info("Validating password for collector: {}", username);
            String hashedPassword = Encryption.hashPwd(password).toUpperCase();
            LOGGER.debug("Hashed password: {}", hashedPassword);
            LOGGER.debug("Stored password: {}", collecteur.getPassword());

            if (!hashedPassword.equals(collecteur.getPassword())) {
                LOGGER.warn("Invalid password for collector: {}", username);
                throw new CrudOperationException("Invalid credentials, please try again", Trame.ResponseCode.ACCESS_DENIED);
            }

            if (collecteur.getIsLocked() == 1) {
                LOGGER.warn("Collector account is locked: {}", username);
                throw new CrudOperationException("User has been blocked", Trame.ResponseCode.CONSTRAINT_ERROR);
            }

            LOGGER.info("Collector login successful for user: {}", username);
            response.setData(collecteur);
            response.setStatus(Trame.ResponseCode.SUCCESS);
            response.setMessage("Login successful");

        } catch (CrudOperationException e) {
            logFailure("logincollector", Arrays.asList(username, password), e);
            LOGGER.error("Login failed for collector {}: {}", username, e.getMessage());
            response.setStatus(e.getResponse().getStatus());
            response.setMessage(e.getResponse().getMessage());
        }

        return response;
    }
}
