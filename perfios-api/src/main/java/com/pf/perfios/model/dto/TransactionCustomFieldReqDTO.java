package com.pf.perfios.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionCustomFieldReqDTO {
    @NotNull
    private String transactionUuid;

    private String comment;
}
