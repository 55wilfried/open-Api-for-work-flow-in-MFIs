package com.microfinance.reporting_services.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestTransactionByParams {

    private Date startDate;
    private Date EndDate;
    private String numCol;
    private String codage;
    private String numcli;
}
