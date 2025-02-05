package com.microfinance.users_services.controller;

import com.microfinance.users_services.dto.CollectUser;
import com.microfinance.users_services.service.UserServices;
import com.microfinance.users_services.utils.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/users")
//@CrossOrigin(origins = {"*"}, maxAge = 3600, allowCredentials = "true")
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600, allowCredentials = "true")
@Tag(name = "User Management Services", description = "APIs for managing Collect Users and Collectors")
public class UserController {

    @Autowired
    private UserServices userServices;

    /**
     * Add a new collector.
     *
     * @param collecteur JSON string representing the collector details.
     * @return APIResponse containing the result of the operation.
     */
    @Operation(summary = "Add Collector", description = "Add a new collector to the system.")
    @PostMapping("/addCollector")
    public APIResponse addCollector(@RequestBody @Valid String collecteur) {
        return userServices.addCollector(collecteur);
    }

    /**
     * Add a new collect user.
     *
     * @param collecteur CollectUser object containing user details.
     * @return The created CollectUser object.
     */
    @Operation(summary = "Add User", description = "Add a new user for collection purposes.")
    @PostMapping("/addUserCollect")
    public CollectUser addUserCollect(@RequestBody @Valid CollectUser collecteur) {
        return userServices.addCollectUser(collecteur);
    }

    /**
     * Update an existing collector.
     *
     * @param num        The ID of the collector to update.
     * @param collecteur JSON string with updated collector details.
     * @return APIResponse containing the result of the update operation.
     */
    @Operation(summary = "Update Collector", description = "Update the details of an existing collector.")
    @PutMapping("/updateCollector/{num}")
    public APIResponse updateCollector(@PathVariable String num, @RequestBody @Valid String collecteur) {
        return userServices.updateCollector(num, collecteur);
    }

    /**
     * Update an existing user.
     *
     * @param userName   The username of the user to update.
     * @param collectUser JSON string with updated user details.
     * @return APIResponse containing the result of the update operation.
     */
    @Operation(summary = "Update User", description = "Update the details of an existing collect user.")
    @PutMapping("/updateUserCollect/{userName}")
    public APIResponse updateUserCollect(@PathVariable String userName, @RequestBody @Valid String collectUser) {
        return userServices.updateUserCollect(userName, collectUser);
    }

    /**
     * Find a user by username.
     *
     * @param userName The username of the user to retrieve.
     * @return APIResponse containing the user details.
     */
    @Operation(summary = "Find User by Username", description = "Retrieve user details by their username.")
    @GetMapping("/userCollect/{userName}")
    public APIResponse findUserCollectById(@PathVariable String userName) {
        return userServices.getCollectUserById(userName);
    }

    /**
     * Find a collector by ID.
     *
     * @param num The ID of the collector to retrieve.
     * @return APIResponse containing the collector details.
     */
    @Operation(summary = "Find Collector by ID", description = "Retrieve collector details by their ID.")
    @GetMapping("/collector/{num}")
    public APIResponse findCollectorById(@PathVariable String num) {
        return userServices.getCollectorById(num);
    }

    /**
     * Retrieve all collectors by codage.
     *
     * @param codage The codage to filter collectors by.
     * @return APIResponse containing the list of collectors matching the codage.
     */
    @Operation(summary = "Get Collectors by Codage", description = "Retrieve all collectors associated with a specific codage.")
    @GetMapping("/allCollectorByCodage/{codage}")
    public APIResponse getAllCollectorByCodage(@PathVariable String codage) {
        return userServices.getAllCollectorByCodage(codage);
    }

    /**
     * Retrieve all users by codage.
     *
     * @param codage The codage to filter users by.
     * @return APIResponse containing the list of users matching the codage.
     */
    @Operation(summary = "Get Users by Codage", description = "Retrieve all collect users associated with a specific codage.")
    @GetMapping("/allUserCollectByCodage/{codage}")
    public APIResponse getAllCollectUserByCodage(@PathVariable String codage) {
        return userServices.getAllCollectUserByCodage(codage);
    }

    /**
     * Retrieve all users.
     *
     * @return APIResponse containing the list of all users.
     */
    @Operation(summary = "Get All Users", description = "Retrieve the list of all collect users.")
    @GetMapping("/allCollectUser")
    public APIResponse getAllClients() {
        return userServices.getAllCollectUser();
    }

    /**
     * Retrieve all collectors.
     *
     * @return APIResponse containing the list of all collectors.
     */
    @Operation(summary = "Get All Collectors", description = "Retrieve the list of all collectors.")
    @GetMapping("/allCollector")
    public APIResponse getAllCollector() {
        return userServices.getAllCollecteur();
    }
}
