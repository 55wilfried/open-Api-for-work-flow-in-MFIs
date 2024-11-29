package com.microfinance.authentification_services.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO representing a login request containing user credentials")
public class LoginRequest {

   @Schema(description = "The username of the user attempting to log in", example = "johndoe123")
   public String userName;

   @Schema(description = "The password of the user attempting to log in", example = "securePassword123")
   public String password;
}
