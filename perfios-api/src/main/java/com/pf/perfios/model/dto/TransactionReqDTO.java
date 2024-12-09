package com.pf.perfios.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pf.perfios.model.entity.DebitCredit;
import com.pf.perfios.utils.PerfiosUtils;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionReqDTO {
    @NotNull
    private DebitCredit debitOrCredit;

    private List<AccountsDTO> accountList;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PerfiosUtils.DATE_FORMAT)
    private LocalDate fromDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PerfiosUtils.DATE_FORMAT)
    private LocalDate toDate;

    private Long fromChqNo;

    private Long toChqNo;

    private Double fromCredit;

    private Double toCredit;

    private Double fromDebit;

    private Double toDebit;

    private Double fromBalance;

    private Double toBalance;

    private String searchText;

    private String description;

    private String comment;

    private String category;
}
