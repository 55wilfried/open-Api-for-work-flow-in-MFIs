package com.microfinance.auth_services.models;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCodage() {
        return codage;
    }

    public void setCodage(String codage) {
        this.codage = codage;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMenuCodes() {
        return menuCodes;
    }

    public void setMenuCodes(String menuCodes) {
        this.menuCodes = menuCodes;
    }

    public String getPrivLg() {
        return privLg;
    }

    public void setPrivLg(String privLg) {
        this.privLg = privLg;
    }

    public String getDefaultPwd() {
        return defaultPwd;
    }

    public void setDefaultPwd(String defaultPwd) {
        this.defaultPwd = defaultPwd;
    }

    public Date getDatecr() {
        return datecr;
    }

    public void setDatecr(Date datecr) {
        this.datecr = datecr;
    }

    public String getCni() {
        return cni;
    }

    public void setCni(String cni) {
        this.cni = cni;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDatemd() {
        return datemd;
    }

    public void setDatemd(Date datemd) {
        this.datemd = datemd;
    }

    public Integer getUsercr() {
        return usercr;
    }

    public void setUsercr(Integer usercr) {
        this.usercr = usercr;
    }

    public Integer getUsermd() {
        return usermd;
    }

    public void setUsermd(Integer usermd) {
        this.usermd = usermd;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    // Getters and setters (omitted for brevity)
}
