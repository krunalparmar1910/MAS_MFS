package com.pf.perfios.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateReportResDTO {

    @NotBlank(message = "field is mandatory")
    private String masFinancialId;

    @NotBlank(message = "field is mandatory")
    private String customerTransactionId;

    private String perfiosTransactionId;
}
