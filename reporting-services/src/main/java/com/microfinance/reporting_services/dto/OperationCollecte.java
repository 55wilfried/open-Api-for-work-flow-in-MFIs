package com.microfinance.reporting_services.dto;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "OperationCollecte")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationCollecte {

    @Id
    private String num;
    private Date dateOp;
    private String numcli;
    private String numCol;
    private String senseOp;
    private Double montant;
    private String libelle;
    private String codage;
    private String nomcli;
    private String nomcol;
    private String cdedat;
    private Date sysDate;
    private Double montantMen;
    private String moisBouclage;
    private Integer isOpDistant;
    private Integer uId;
    private String numChq;
    private String wType;
    private String series;

    // Getters and setters (omitted for brevity)
}

