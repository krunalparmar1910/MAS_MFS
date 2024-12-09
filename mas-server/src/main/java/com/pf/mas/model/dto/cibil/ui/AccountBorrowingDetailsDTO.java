package com.pf.mas.model.dto.cibil.ui;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.dto.cibil.consumer.AuccountSummerySegmentDTO;
import com.pf.mas.model.entity.consumer.PaymentHistoryRecord;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties({"id", "isNew", "isEdited"})
public class AccountBorrowingDetailsDTO {
    private String typeOfLoan;
    private Double sanctionedAmount;
    private Double outstandingAmount;
    @JsonFormat(pattern = Constants.SHORT_DATE_FORMAT)
    private Date sanctionedDate;
    private String cibilStatus;
    private String bankNbfc;
    private String ownership;
    private Double overdue;
    private String suitFiledOrWilfulDefault;
    private String creditFacilityStatus;
    private String writtenOffAmountPrincipal;
    private Long tenure;
    private Long last12MonthsDPD;
    @JsonFormat(pattern = Constants.SHORT_DATE_FORMAT)
    private Date last12MonthsDPDDate;
    private Long delayInMonthsLast12Months;
    private Long overallDPD;
    @JsonFormat(pattern = Constants.SHORT_DATE_FORMAT)
    private Date overallDPDDate;
    private Long delayInMonthsOverall;
    @JsonFormat(pattern = Constants.SHORT_DATE_FORMAT)
    private Date reportedDate;
    private Double emi;
    private Double emiSystem;
    private Double interestRate;
    private String comment;
    private boolean duplicate;
    private boolean addInTotal;
    private long accountId;
    private List<PaymentHistoryRecord> paymentHistoryRecordList;
    private AuccountSummerySegmentDTO auccountSummerySegmentDTO;
}
