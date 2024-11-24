package com.microfinance.authentification_services.controller;

import com.microfinance.authentification_services.service.AuthService;
import com.microfinance.authentification_services.utils.APIResponse;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins= {"*"}, maxAge = 3600, allowCredentials = "false")
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;



    @Operation(summary = "Login collector", description = "I take collector request model")
    @PostMapping("/login")
    public APIResponse login(@RequestBody @Valid String client) {
        System.out.println("Login method called");
        return this.service.loginCollector(client);
    }
    @Operation(summary = "Login User", description = "I take user request model")
    @PostMapping("/loginUser")
    public APIResponse loginUser(@RequestBody  String client) {
        System.out.println("LoginUser method called");
        return this.service.loginUser(client);
    }
}
