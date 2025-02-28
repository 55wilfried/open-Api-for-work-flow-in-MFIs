package com.microfinance.client_services.controller;

import com.microfinance.client_services.models.LoginRequest;
import com.microfinance.client_services.models.LoginResponse;
import com.microfinance.client_services.services.ClientServices;
import com.microfinance.client_services.token.KeycloakTokenClient;
import com.microfinance.client_services.token.TokenService;
import com.microfinance.client_services.utils.APIResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class to handle client-related requests.
 * Handles client creation, update, and retrieval.
 */
//@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowCredentials = "true")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/client")
@SecurityRequirement(name = "keycloak")
@SecurityRequirement(name = "local")
public class ClientController {


    @Autowired
    private KeycloakTokenClient keycloakService;

    @Autowired
    private final TokenService tokenService;

    public ClientController(TokenService tokenService) {
        this.tokenService = tokenService;
    }


    @Autowired
    private ClientServices clientService;

    /**
     * Add a new client.
     *
     * @param client the client data in JSON format.
     * @return API response with the result of the operation.
     */
    @PostMapping("/add")
    public APIResponse addClient(@RequestBody @Valid String client) {
        return clientService.addClient(client);
    }

    /**
     * Update an existing client's password.
     *
     * @param num     the client number.
     * @param password the new password for the client.
     * @return API response with the result of the operation.
     */
    @PutMapping("/update/{num}")
    public APIResponse updateClient(@PathVariable String num, @RequestBody @Valid String password) {
        return clientService.updateClientPassword(num , password);
    }

    /**
     * Retrieve a client by their client number.
     *
     * @param numClient the client number.
     * @return API response with client data.
     */
    @GetMapping("/numClient/{numClient}")
    public APIResponse findClientByNum(@PathVariable String numClient) {
        return clientService.getClientById(numClient);
    }

    /**
     * Retrieve a client by their name.
     *
     * @param name the name of the client.
     * @return API response with client data.
     */
    @GetMapping("/name/{name}")
    public APIResponse findClientByName(@PathVariable String name) {
        return clientService.getClientByName(name);
    }

    /**
     * Retrieve all clients.
     *
     * @return API response with list of all clients.
     */
    @GetMapping("/all")
    public APIResponse getAllClients() {
        return clientService.getAllClients();
    }

    /**
     * Retrieve all clients by their codage.
     *
     * @param codage the codage value.
     * @return API response with list of clients filtered by codage.
     */
    @GetMapping("/allClientByCodage/{codage}")
    public APIResponse getAllClientsByCodage(@PathVariable String codage) {
        return clientService.getAllClientByCodage(codage);
    }

    /**
     * Retrieve all clients assigned to a specific collector.
     *
     * @param collector the collector number.
     * @return API response with list of clients assigned to the collector.
     */
    @GetMapping("/allClientByCollector/{collector}")
    public APIResponse getAllClientsByCollector(@PathVariable String collector) {
        return clientService.getAllClientByCol(collector);
    }


    @PostMapping("/getToken")
    public LoginResponse loginTest(@RequestBody LoginRequest request) {
        return tokenService.generateToken(request);
    }

}
