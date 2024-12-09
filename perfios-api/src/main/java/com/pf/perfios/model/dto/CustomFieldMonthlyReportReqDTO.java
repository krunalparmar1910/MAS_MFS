package com.pf.perfios.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomFieldMonthlyReportReqDTO {
    private String monthwiseDetailUuid;
    private BigDecimal customCcUtilizationPercentage;
}
