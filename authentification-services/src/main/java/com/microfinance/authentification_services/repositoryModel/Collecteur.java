package com.microfinance.authentification_services.repositoryModel;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "COLLECTEUR")
public class Collecteur {

    @Id
    @Column(name = "num", nullable = false, length = 12)
    private String num;

    @Column(name = "nom", nullable = false, length = 30)
    private String nom;

    @Column(name = "adresse", length = 50)
    private String adresse;

    @Column(name = "commission", nullable = false)
    private float commission;

    @Column(name = "tauxcom")
    private Float tauxcom;

    @Column(name = "tauxPrime")
    private Float tauxPrime;

    @Column(name = "cni", nullable = false, length = 50)
    private String cni;

    @Column(name = "numcpt", length = 25)
    private String numcpt;

    @Column(name = "prime")
    private Float prime;

    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Column(name = "localisation", nullable = false, length = 50)
    private String localisation;

    @Column(name = "codage", nullable = false, length = 12)
    private String codage;

    @Column(name = "idFront", length = 255)
    private String idFront;

    @Column(name = "idBack", length = 255)
    private String idBack;

    @Column(name = "chiffreAffaire")
    private Float chiffreAffaire;

    @Column(name = "tel", length = 15)
    private String tel;

    @Column(name = "picture", length = 255)
    private String picture;

    @Column(name = "isActivated")
    private Boolean isActivated;

    @Column(name = "Isdebit")
    private Integer isDebit;

    @Column(name = "Iscredit")
    private Integer isCredit;

    @Column(name = "Isconnect")
    private Integer isConnect;

    @Column(name = "Islocked")
    private Integer isLocked;

    @Column(name = "dateCrea")
    private Date dateCrea;

    @Column(name = "MntBlk")
    private Float mntBlk;

    @Column(name = "TelBlkSMS", length = 15)
    private String telBlkSMS;

    @Column(name = "MacAdd", length = 50)
    private String macAdd;

    @Column(name = "ISOFFLINE")
    private Boolean isOffline;

    @Column(name = "LastNumOp", length = 20)
    private String lastNumOp;

    @Column(name = "NUMCPTMBR", length = 50)
    private String numCptMbr;

    @Column(name = "defaultPwd", length = 1)
    private String defaultPwd;

    @Column(name = "soldeActuel", precision = 18, scale = 0)
    private Float soldeActuel;

    @Transient
    private String Key;

    // Getters and setters (omitted for brevity)
}

