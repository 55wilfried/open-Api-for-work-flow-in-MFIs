package com.microfinance.authentification_services;


import com.microfinance.authentification_services.dto.LoginRequest;
import com.microfinance.authentification_services.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthService userService;

    @Test
    public void testRegister() throws Exception {

        LoginRequest user = new LoginRequest();
        user.setUserName("C010");
        user.setPassword("@1T3CH#");
        userService.loginCollector(String.valueOf(user));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\": \"C010\", \"password\": \"@1T3CH#\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("testuser"));
    }

    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/login")
                        .param("userName", "C010")
                        .param("password", "@1T3CH#"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("C010"));
    }
}

