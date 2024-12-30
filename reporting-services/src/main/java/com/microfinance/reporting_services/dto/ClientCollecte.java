package com.microfinance.reporting_services.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
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
)
@ToString
@Schema(description = "ClientCollecte represents a client in the collection system.")
public class ClientCollecte implements Serializable {

    @Id
    @Column(name = "num", nullable = false, length = 8)
    @Schema(description = "Unique client identifier", example = "12345678")
    private String num;

    @Column(name = "nom", length = 50)
    @Schema(description = "Client's full name", example = "John Doe")
    private String nom;

    @Column(name = "cni", length = 30)
    @Schema(description = "Client's national identification number", example = "CNI123456789")
    private String cni;

    @Column(name = "codage", length = 3)
    @Schema(description = "Client's coding or classification", example = "COD")
    private String codage;

    @Column(name = "numCol", nullable = false, length = 12)
    @Schema(description = "Collector's unique identifier", example = "COL123456789")
    private String numCol;

    @Column(name = "nomCol", length = 150)
    @Schema(description = "Collector's full name", example = "Jane Smith")
    private String nomCol;

    @Column(name = "username", length = 150)
    @Schema(description = "Username for the client's account", example = "john.doe123")
    private String username;

    @Column(name = "pwd", length = 150)
    @Schema(description = "Password associated with the client's account", example = "password123")
    private String pwd;

    @Column(name = "tel", nullable = false, length = 25)
    @Schema(description = "Client's phone number", example = "+237677899988")
    private String tel;

    @Column(name = "dateCrea", nullable = false)
    @Schema(description = "Account creation date", example = "2024-12-30T15:30:00Z")
    private Date dateCrea;

    @Column(name = "commissionres")
    @Schema(description = "Commission rate for the client", example = "0.05")
    private Float commissionres;

    @Column(name = "taux")
    @Schema(description = "Interest rate for the client", example = "0.03")
    private Float taux;

    @Column(name = "soldeInitial")
    @Schema(description = "Initial balance of the client's account", example = "1000.00")
    private Float soldeInitial;

    @Column(name = "montantDebit")
    @Schema(description = "Total debit amount", example = "200.00")
    private Float montantDebit;

    @Column(name = "montantCredit")
    @Schema(description = "Total credit amount", example = "500.00")
    private Float montantCredit;

    @Column(name = "sms")
    @Schema(description = "Indicates whether the client receives SMS notifications", example = "true")
    private Boolean sms;

    @Column(name = "ViewBalanceInSms")
    @Schema(description = "Indicates whether the client can view balance in SMS notifications", example = "false")
    private Boolean viewBalanceInSms;

    @Column(name = "isActivated")
    @Schema(description = "Indicates whether the client's account is activated", example = "true")
    private Boolean isActivated;

    @Column(name = "numCarte", length = 16)
    @Schema(description = "Client's card number", example = "1234567890123456")
    private String numCarte;

    @Column(name = "comParam", length = 150)
    @Schema(description = "Additional comments or parameters related to the client", example = "VIP Client")
    private String comParam;

    @Column(name = "isCard")
    @Schema(description = "Indicates whether the client has a physical card", example = "true")
    private Boolean isCard;

    @Column(name = "OldCpt", length = 20)
    @Schema(description = "Old account number associated with the client", example = "1234567890")
    private String oldCpt;

    @Column(name = "numcpt", length = 15)
    @Schema(description = "Account number of the client", example = "987654321098765")
    private String numcpt;

    @Column(name = "HasPIN")
    @Schema(description = "Indicates whether the client's account has a PIN", example = "true")
    private Boolean hasPIN;

    @Column(name = "PIN", length = 55)
    @Schema(description = "PIN code for the client", example = "1234")
    private String PIN;

    @Column(name = "IsNew")
    @Schema(description = "Indicates whether the client is new", example = "true")
    private Boolean isNew;

    @Column(name = "adresse", length = 50)
    @Schema(description = "Client's address", example = "123 Main Street")
    private String adresse;

    @Column(name = "localisation", length = 50)
    @Schema(description = "Client's geographical location", example = "Douala, Cameroon")
    private String localisation;

    @Column(name = "mig", length = 11)
    @Schema(description = "Migration status or related code", example = "MIG123456")
    private String mig;

    @Column(name = "Language", length = 5)
    @Schema(description = "Language preference of the client", example = "EN")
    private String language;
}
