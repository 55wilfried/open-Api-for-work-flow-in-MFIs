package com.microfinance.authentification_services;

import com.microfinance.authentification_services.dto.LoginRequest;
import com.microfinance.authentification_services.service.AuthService;
import com.microfinance.authentification_services.utils.APIResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Test
    void contextLoads() {
    }
/*
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService userService;

    @MockBean
    private AuthenticationManager authenticationManager;  // Mock AuthenticationManager

    @Test
    public void testRegister() throws Exception {
        // Mock the service method call
        LoginRequest user = new LoginRequest();
        user.setUserName("C010");
        user.setPassword("Test1234");
        when(userService.loginCollector("C010")).thenReturn(new APIResponse());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\": \"C010\", \"password\": \"Test1234\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("C010"));
    }

    @Test
    public void testLogin() throws Exception {
        // Mock the service method call
        when(userService.loginCollector("C010")).thenReturn(new APIResponse());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\": \"C010\", \"password\": \"Test1234\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("C010"));
    }*/
}
