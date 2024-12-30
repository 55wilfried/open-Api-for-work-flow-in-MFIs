package com.microfinance.reporting_services.dto;

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
}
