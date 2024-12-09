package com.pf.mas.model.dto.cibil.ui;

import lombok.Data;

@Data
public class AddressInfoDTO {
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    private String stateCode;
    private String pinCode;
    private String category;
    private String dateReported;

}
