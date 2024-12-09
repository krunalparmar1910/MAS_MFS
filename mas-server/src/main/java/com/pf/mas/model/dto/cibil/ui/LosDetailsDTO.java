package com.pf.mas.model.dto.cibil.ui;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pf.mas.model.constant.Constants;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class LosDetailsDTO {

    private String name;
    @JsonFormat(pattern = Constants.SHORT_DATE_FORMAT)
    private Date dateOfBirth;
    private String gender;
    private List<IdentificationDTO> losIdentificationDTOList;
    private List<Long> telephoneNumbers;
    private List<String> emails;
    private List<AddressInfoDTO> losAddressInfoDTOList;
}
