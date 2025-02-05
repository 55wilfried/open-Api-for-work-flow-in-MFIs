package com.microfinance.auth_services.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CollectUsers")
@Schema(description = "Entity representing a user in the collection system")
public class CollectUser {

    @Schema(description = "The unique identifier of the user, generated automatically")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "The full name of the user (mandatory field)")
    @Column(name = "name", nullable = false)
    private String name;

    @Schema(description = "The unique username for user login (mandatory field)")
    @Column(name = "username", nullable = false)
    private String username;

    @Schema(description = "The code assigned to the user for identification")
    @Column(name = "codage")
    private String codage;

    @Schema(description = "The user's password for authentication (mandatory field)")
    @Column(name = "password", nullable = false)
    private String password;

    @Schema(description = "Indicates if the user account is active")
    @Column(name = "isActive", nullable = false)
    private boolean isActive;

    @Schema(description = "Indicates if the user is currently connected to the system")
    @Column(name = "IsConnected", nullable = false)
    private boolean isConnected;

    @Schema(description = "The role assigned to the user (e.g., ADMIN, COLLECTOR, etc.)")
    @Column(name = "role", nullable = false)
    private String role;

    @Schema(description = "Comma-separated codes representing the user's accessible menus")
    @Column(name = "menuCodes")
    private String menuCodes;

    @Schema(description = "The user's private language preference or code")
    @Column(name = "privLg")
    private String privLg;

    @Schema(description = "Indicates if the user is using the default password")
    @Column(name = "defaultPwd")
    private String defaultPwd;

    @Schema(description = "The date when the user account was created")
    @Column(name = "datecr")
    private Date datecr;

    @Schema(description = "The national identification number (CNI) of the user")
    @Column(name = "cni")
    private String cni;

    @Schema(description = "The user's phone number")
    @Column(name = "telephone")
    private String telephone;

    @Schema(description = "The user's email address")
    @Column(name = "email")
    private String email;

    @Schema(description = "The date when the user account was last modified")
    @Column(name = "datemd")
    private Date datemd;

    @Schema(description = "The ID of the user who created this record")
    @Column(name = "usercr")
    private Integer usercr;

    @Schema(description = "The ID of the user who last modified this record")
    @Column(name = "usermd")
    private Integer usermd;

    @Schema(description = "A temporary key for encryption or authentication purposes (not persisted in the database)")
    @Transient
    private String Key;

    // Getters and setters (omitted for brevity)
}
