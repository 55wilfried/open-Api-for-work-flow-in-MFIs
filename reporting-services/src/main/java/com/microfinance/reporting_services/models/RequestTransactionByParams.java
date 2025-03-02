package com.microfinance.reporting_services.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request parameters for filtering transactions by date, collector, and client information.")
public class RequestTransactionByParams {

    @Schema(description = "The start date for the transaction search.", example = "2024-01-01T00:00:00", required = true)
    private Date startDate;

    @Schema(description = "The end date for the transaction search.", example = "2024-12-31T23:59:59", required = true)
    private Date EndDate;

    @Schema(description = "The collector's identifier for the transaction search.", example = "COL123", required = false)
    private String numCol;

    @Schema(description = "The codage or classification used to filter transactions.", example = "C01", required = false)
    private String codage;

    @Schema(description = "The client's identifier for filtering transactions.", example = "CLI12345", required = false)
    private String numcli;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }

    public String getNumCol() {
        return numCol;
    }

    public void setNumCol(String numCol) {
        this.numCol = numCol;
    }

    public String getCodage() {
        return codage;
    }

    public void setCodage(String codage) {
        this.codage = codage;
    }

    public String getNumcli() {
        return numcli;
    }

    public void setNumcli(String numcli) {
        this.numcli = numcli;
    }
}
