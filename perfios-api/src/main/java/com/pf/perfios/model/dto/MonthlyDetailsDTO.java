package com.pf.perfios.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MonthlyDetailsDTO {
    private String masFinancialId;
    private List<BankDetailDTO> bankDetailList;
    private CustomEditableFieldDTO customEditableFieldDTO;
}
