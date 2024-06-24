package com.microfinance.transaction_services.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data

public class RequestTransactionByParams {

    private Date startDate;
    private Date EndDate;
    private String numCol;
    private String codage;
    private String numcli;
}
