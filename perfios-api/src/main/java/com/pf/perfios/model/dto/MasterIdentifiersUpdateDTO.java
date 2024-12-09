package com.pf.perfios.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pf.perfios.model.entity.DebitCredit;
import com.pf.perfios.model.entity.IdentifierType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MasterIdentifiersUpdateDTO {

    @NotNull
    private Long id;

    @NotNull
    private IdentifierType identifierType;

    @NotBlank(message = "field is mandatory")
    private String identifierName;

    @NotBlank(message = "field is mandatory")
    private String identificationValue;

    @NotNull
    private DebitCredit debitCredit;

}
