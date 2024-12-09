package com.pf.perfios.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BankResDTO {
    private String accountNo;
    private String bankName;
}
