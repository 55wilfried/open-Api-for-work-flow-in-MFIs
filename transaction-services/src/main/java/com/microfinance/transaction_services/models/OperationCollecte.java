package com.microfinance.transaction_services.models;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Representing a collection operation in the system.")
public class OperationCollecte {

    @Id
    @Schema(description = "Unique identifier for the operation.", example = "OP123456")
    private String num;

    @Schema(description = "Date of the operation.",
            example = "2024-12-30T10:00:00Z",
            type = "string", format = "date-time")
    private Date dateOp;

    @Schema(description = "Client number associated with the operation.", example = "CLI789")
    private String numcli;

    @Schema(description = "Collector number associated with the operation.", example = "COL456")
    private String numCol;

    @Schema(description = "Operation type (e.g., 'debit' or 'credit').", example = "credit")
    private String senseOp;

    @Schema(description = "Amount involved in the operation.", example = "5000.00")
    private Double montant;

    @Schema(description = "Label or description of the operation.", example = "Deposit")
    private String libelle;

    @Schema(description = "Codage value for categorizing the operation.", example = "C123")
    private String codage;

    @Schema(description = "Name of the client.", example = "John Doe")
    private String nomcli;

    @Schema(description = "Name of the collector.", example = "Jane Smith")
    private String nomcol;

    @Schema(description = "Code for the data entry process.", example = "CD123")
    private String cdedat;

    @Schema(description = "System-generated date for the operation.",
            example = "2024-12-30T12:00:00Z",
            type = "string", format = "date-time")
    private Date sysDate;

    @Schema(description = "Monthly amount related to the operation.", example = "1000.00")
    private Double montantMen;

    @Schema(description = "Month for closing the operation.", example = "2024-12")
    private String moisBouclage;

    @Schema(description = "Flag indicating if the operation is distant (1 = yes, 0 = no).", example = "1")
    private Integer isOpDistant;

    @Schema(description = "User ID associated with the operation.", example = "42")
    private Integer uId;

    @Schema(description = "Check number if applicable.", example = "CHK123456")
    private String numChq;

    @Schema(description = "Work type or classification.", example = "regular")
    private String wType;

    @Schema(description = "Series information related to the operation.", example = "SER123")
    private String series;

    // Getters and setters (omitted for brevity)
}
