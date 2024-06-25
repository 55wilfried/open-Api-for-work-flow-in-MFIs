package com.microfinance.users_services.contoller;

import com.microfinance.users_services.models.CollectUser;
import com.microfinance.users_services.models.Collecteur;
import com.microfinance.users_services.userServices.UserServices;
import com.microfinance.users_services.utils.APIResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins= {"*"}, maxAge = 3600, allowCredentials = "false")
public class UserController {
    @Autowired
    private UserServices userServices;

    @PostMapping("/addCollector")
    public APIResponse addCollector(@RequestBody @Valid String collecteur) {
        return userServices.addCollector(collecteur);
    }

    @PostMapping("/addUserCollect")
    public CollectUser addUserCollect(@RequestBody @Valid CollectUser collecteur) {
        return userServices.addCollectUser(collecteur);
    }

    @PutMapping("/updateCollector/{num}")
    public APIResponse updateCollector(@PathVariable String num, @RequestBody @Valid String collecteur) {
        return userServices.updateCollector(num , collecteur);
    }

    @PutMapping("/updateUserCollect/{userName}")
    public APIResponse updateUserCollect(@PathVariable String num, @RequestBody @Valid String collectUser) {
        return userServices.updateUserCollect(num , collectUser);
    }

    @GetMapping("/userCollect/{userName}")
    public APIResponse findUserCollectById(@PathVariable String userName) {
        return userServices.getCollectUserById(userName);
    }

    @GetMapping("/collector/{num}")
    public APIResponse findCollectorById(@PathVariable String num) {
        return userServices.getCollectorById(num);
    }

    @GetMapping("/allCollectorByCodage/{codage}")
    public APIResponse getAllCollectorByCodage(@PathVariable String codage) {
        return userServices.getAllCollectorByCodage(codage);
    }

    @GetMapping("/allUserCollectByCodage/{codage}")
    public APIResponse getAllCollectUserByCodage(@PathVariable String codage) {
        return userServices.getAllCollectUserByCodage(codage);
    }

    @GetMapping("/allCollectUser")
    public APIResponse  getAllClients() {
        return userServices.getAllCollectUser();
    }

    @GetMapping("/allCollector")
    public APIResponse getAllCollector() {
        return userServices.getAllCollecteur();
    }

}
