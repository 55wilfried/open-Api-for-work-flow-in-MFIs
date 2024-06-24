package com.microfinance.client_services.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "ClientCollecte"
)

@EntityListeners({AuditingEntityListener.class})
@JsonIgnoreProperties(
        value = {"createdAt"},
        allowGetters = true
)@ToString
public class ClientCollecte implements Serializable {

    @Id
    @Column(name = "num", nullable = false, length = 8)
    private String num;

    @Column(name = "nom", length = 50)
    private String nom;

    @Column(name = "cni", length = 30)
    private String cni;

    @Column(name = "codage", length = 3)
    private String codage;

    @Column(name = "numCol", nullable = false, length = 12)
    private String numCol;

    @Column(name = "nomCol", length = 150)
    private String nomCol;

    @Column(name = "username", length = 150)
    private String username;

    @Column(name = "pwd", length = 150)
    private String pwd;

    @Column(name = "tel", nullable = false, length = 25)
    private String tel;

    @Column(name = "dateCrea", nullable = false)
    private Date dateCrea;

    @Column(name = "commissionres")
    private Float commissionres;

    @Column(name = "taux")
    private Float taux;

    @Column(name = "soldeInitial")
    private Float soldeInitial;

    @Column(name = "montantDebit")
    private Float montantDebit;

    @Column(name = "montantCredit")
    private Float montantCredit;

    @Column(name = "sms")
    private Boolean sms;

    @Column(name = "ViewBalanceInSms")
    private Boolean viewBalanceInSms;

    @Column(name = "isActivated")
    private Boolean isActivated;

    @Column(name = "numCarte", length = 16)
    private String numCarte;

    @Column(name = "comParam", length = 150)
    private String comParam;

    @Column(name = "isCard")
    private Boolean isCard;

    @Column(name = "OldCpt", length = 20)
    private String oldCpt;

    @Column(name = "numcpt", length = 15)
    private String numcpt;

    @Column(name = "HasPIN")
    private Boolean hasPIN;

    @Column(name = "PIN", length = 55)
    private String PIN;

    @Column(name = "IsNew")
    private Boolean isNew;

    @Column(name = "adresse", length = 50)
    private String adresse;

    @Column(name = "localisation", length = 50)
    private String localisation;

    @Column(name = "mig", length = 11)
    private String mig;

    @Column(name = "Language", length = 5)
    private String language;




}