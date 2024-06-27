package com.microfinance.loans_services.loansController;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loan")
@CrossOrigin(origins= {"*"}, maxAge = 3600, allowCredentials = "false")
public class LoanController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to Loan Micro finance!";
    }

}
