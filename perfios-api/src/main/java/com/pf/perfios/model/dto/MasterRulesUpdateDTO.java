package com.pf.perfios.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pf.perfios.model.entity.DebitCredit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MasterRulesUpdateDTO {

    @NotNull
    private Long id;
    @NotNull
    private List<Long> transactionTypeIdList;
    @NotNull
    private List<Long> categoryIdList;
    @NotNull
    private List<Long> partiesMerchantIdList;

    private String identificationValue;
    @NotNull
    private DebitCredit debitCredit;
    @NotBlank(message = "field is mandatory")
    private String transactionFlag;
    @NotNull
    private String ruleQuery;

}
