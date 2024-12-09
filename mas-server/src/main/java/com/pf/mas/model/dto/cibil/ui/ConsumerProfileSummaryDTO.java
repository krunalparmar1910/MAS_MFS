package com.pf.mas.model.dto.cibil.ui;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.dto.cibil.consumer.AuccountSummerySegmentDTO;
import com.pf.mas.model.dto.cibil.consumer.NameSegmentDTO;
import com.pf.mas.model.entity.cibil.NonCibilAccount;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ConsumerProfileSummaryDTO {
    private String name;
    @JsonFormat(pattern = Constants.SHORT_DATE_FORMAT)
    private Date dateOfBirth;
    private String gender;
    private Long age;
    private Long score;
    private Long controlNumber;
    private List<IdentificationDTO> identification;
    private List<String> telephoneNumbers;
    private List<String> emails;
    private List<AddressInfoDTO> addresses;
    private ConsumerAccountSummaryDTO accountSummary;
    private AccountInquiriesHistoricalDTO accountInquiriesHistoricalData;
    private List<AccountBorrowingDetailsDTO> accountBorrowingDetailsDTOList;
    private List<NonCibilAccount> nonCibilAccountList;
    private LosDetailsDTO losDetailsDTO;
    private List<AuccountSummerySegmentDTO> auccountSummerySegmentDTOS;
    private List<AddressInfoDTO> secondaryAddresses;
    private List<String> secondaryTelephoneNumbers;
    private List<IdentificationDTO> secondaryIdentification;
    private List<NameSegmentDTO> secondaryNameSegmentList;
}
