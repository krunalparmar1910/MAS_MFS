package com.pf.perfios.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountsDTO {

    private List<String> accountNumberList;

    @NotBlank(message = "field is mandatory")
    private String customerTransactionId;
}
