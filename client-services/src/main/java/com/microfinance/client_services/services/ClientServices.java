package com.microfinance.client_services.services;

import com.microfinance.client_services.clientRepository.ClientRepository;
import com.microfinance.client_services.models.ClientCollecte;
import com.microfinance.client_services.utils.APIResponse;
import com.microfinance.client_services.utils.CrudOperationException;
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

import java.util.List;

@Service
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowCredentials = "true")
public class ClientServices {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientServices.class);

    @Autowired
    private ClientRepository clientRepository;
    ClientCollecte clientCollecte = new ClientCollecte();

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
            ClientCollecte collecte =  clientRepository.findByNum(num);
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
        APIResponse resp = new APIResponse();
        try {
            ClientCollecte collecte = clientRepository.findByName(name);
            if (collecte != null){
                resp.setData(collecte);
                resp.setStatus(Trame.ResponseCode.SUCCESS);
                resp.setMessage("SUCCESS");
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
            ClientCollecte client = this.clientRepository.findByNum(num);
            if (client != null){
                clientCollecte.setPIN(password);
                resp.setData(clientRepository.save(clientCollecte));
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
