package com.microfinance.client_services.controller;

import com.microfinance.client_services.models.ClientCollecte;
import com.microfinance.client_services.services.ClientServices;
import com.microfinance.client_services.utils.APIResponse;
import com.microfinance.client_services.utils.CrudOperationException;
import com.microfinance.client_services.utils.Trame;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins= {"*"}, maxAge = 3600, allowCredentials = "false")
@RestController
@RequestMapping("/ClientCollecte")
public class ClientController {
    @Autowired
    private ClientServices clientService;

    @PostMapping("/add")
    public APIResponse addClient(@RequestBody @Valid String client) {
        return clientService.addClient(client);
    }

    @PutMapping("/update/{num}")
    public APIResponse updateClient(@PathVariable String num, @RequestBody @Valid String password) {
        return clientService.updateClientPassword(num , password);
    }

    @GetMapping("/numClient/{numClient}")
    public APIResponse findClientByNum(@PathVariable String numClient) {
        return clientService.getClientById(numClient);
    }

    @GetMapping("/name/{name}")
    public APIResponse findClientByName(@PathVariable String name) {
        return clientService.getClientByName(name);
    }

    @GetMapping("/all")
    public APIResponse getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/allClientByCodage/{codage}")
    public APIResponse getAllClientsByCodage(@PathVariable String codage) {
        return clientService.getAllClientByCodage(codage);
    }

    @GetMapping("/allClientByCollector/{collector}")
    public APIResponse getAllClientsByCollector(@PathVariable String collector) {
        return clientService.getAllClientByCol(collector);
    }


}
