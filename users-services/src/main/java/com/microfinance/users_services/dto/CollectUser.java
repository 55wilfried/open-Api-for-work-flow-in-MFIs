package com.microfinance.users_services.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CollectUsers")
@Schema(description = "Represents a user within the collect system.")
public class CollectUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the user.", example = "1", required = true)
    private Long id;

    @Column(name = "name", nullable = false)
    @Schema(description = "Full name of the user.", example = "John Doe", required = true)
    private String name;

    @Column(name = "username", nullable = false)
    @Schema(description = "Username for the user to log in.", example = "john_doe", required = true)
    private String username;

    @Column(name = "codage")
    @Schema(description = "Code associated with the user, if any.", example = "USER123")
    private String codage;

    @Column(name = "password", nullable = false)
    @Schema(description = "Password for the user account.", example = "SecurePassword123", required = true)
    private String password;

    @Column(name = "isActive", nullable = false)
    @Schema(description = "Indicates whether the user account is active.", example = "true", required = true)
    private boolean isActive;

    @Column(name = "IsConnected", nullable = false)
    @Schema(description = "Indicates whether the user is currently connected.", example = "false", required = true)
    private boolean isConnected;

    @Column(name = "role", nullable = false)
    @Schema(description = "Role of the user within the system.", example = "Admin", required = true)
    private String role;

    @Column(name = "menuCodes")
    @Schema(description = "List of menu codes associated with the user.", example = "['MENU_1', 'MENU_2']")
    private String menuCodes;

    @Column(name = "privLg")
    @Schema(description = "Privileges or language settings for the user.", example = "ENGLISH")
    private String privLg;

    @Column(name = "defaultPwd")
    @Schema(description = "Indicates if the user is using the default password.", example = "Y")
    private String defaultPwd;

    @Column(name = "datecr")
    @Schema(description = "Date when the user account was created.", example = "2024-01-15T10:30:00Z")
    private Date datecr;

    @Column(name = "cni")
    @Schema(description = "National Identification number of the user.", example = "CNI1234567890")
    private String cni;

    @Column(name = "telephone")
    @Schema(description = "Phone number of the user.", example = "+1234567890")
    private String telephone;

    @Column(name = "email")
    @Schema(description = "Email address of the user.", example = "johndoe@example.com")
    private String email;

    @Column(name = "datemd")
    @Schema(description = "Date when the user's data was last modified.", example = "2024-02-10T14:45:00Z")
    private Date datemd;

    @Column(name = "usercr")
    @Schema(description = "ID of the user who created this account.", example = "101")
    private Integer usercr;

    @Column(name = "usermd")
    @Schema(description = "ID of the user who last modified this account.", example = "102")
    private Integer usermd;
}
