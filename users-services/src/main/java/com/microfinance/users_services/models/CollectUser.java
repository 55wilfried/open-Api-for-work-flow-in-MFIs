package com.microfinance.users_services.models;

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
    public class CollectUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

        @Column(name = "name", nullable = false)
        private String name;

        @Column(name = "username", nullable = false)
        private String username;

        @Column(name = "codage")
        private String codage;

        @Column(name = "password", nullable = false)
        private String password;

        @Column(name = "isActive", nullable = false)
        private boolean isActive;

        @Column(name = "IsConnected", nullable = false)
        private boolean isConnected;

        @Column(name = "role", nullable = false)
        private String role;

        @Column(name = "menuCodes")
        private String menuCodes;

        @Column(name = "privLg")
        private String privLg;

        @Column(name = "defaultPwd")
        private String defaultPwd;

        @Column(name = "datecr")
        private Date datecr;

        @Column(name = "cni")
        private String cni;

        @Column(name = "telephone")
        private String telephone;

        @Column(name = "email")
        private String email;

        @Column(name = "datemd")
        private Date datemd;

        @Column(name = "usercr")
        private Integer usercr;

        @Column(name = "usermd")
        private Integer usermd;

        // Getters and setters (omitted for brevity)
}

