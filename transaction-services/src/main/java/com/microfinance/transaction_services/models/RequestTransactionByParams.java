package com.microfinance.transaction_services.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request parameters for filtering transactions by various criteria.")
public class RequestTransactionByParams {

    @Schema(description = "Transaction number or unique identifier.", example = "TX123456")
    private String num;

    @Schema(description = "Start date for filtering transactions.",
            example = "2024-12-30T00:00:00Z",
            type = "string", format = "date-time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private String startDate;

    @Schema(description = "End date for filtering transactions.",
            example = "2024-12-31T23:59:59Z",
            type = "string", format = "date-time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private String endDate;

    @Schema(description = "Collector number or ID.", example = "COL123")
    private String numCol;

    @Schema(description = "Codage value used for filtering.", example = "C123")
    private String codage;

    @Schema(description = "Client number or identifier.", example = "CLI456")
    private String numcli;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
