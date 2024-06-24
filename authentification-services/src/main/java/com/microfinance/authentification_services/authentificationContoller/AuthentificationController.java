package com.microfinance.authentification_services.authentificationContoller;


import com.microfinance.authentification_services.authentificationServices.AuthentificationServices;
import com.microfinance.authentification_services.repositoryModel.LoginRequest;
import com.microfinance.authentification_services.utils.APIResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins= {"*"}, maxAge = 3600, allowCredentials = "false")
@RequestMapping("/Auth")
public class AuthentificationController {
    @Autowired
    private AuthentificationServices authentificationServices;

    @PostMapping("/login")
    public APIResponse login(@RequestBody @Valid  String client) {
        System.out.println("Login method called");
        return this.authentificationServices.loginCollector(client);
    }

    @PostMapping("/loginUser")
    public APIResponse loginUser(@RequestBody  String client) {
        System.out.println("LoginUser method called");
        return this.authentificationServices.loginUser(client);
    }
}
