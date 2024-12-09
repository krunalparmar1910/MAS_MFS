package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "EmploymentSegment")
public class EmploymentSegmentDTO {
    @JacksonXmlProperty(localName = "DateReportedCertified")
    private String dateReportedCertified;

    @JacksonXmlProperty(localName = "Length")
    private String length;

    @JacksonXmlProperty(localName = "SegmentTag")
    private String segmentTag;

    @JacksonXmlProperty(localName = "OccupationCode")
    private String occupationCode;

    @JacksonXmlProperty(localName = "AccountType")
    private String accountType;

    @JacksonXmlProperty(localName = "IncomeFieldLength")
    private String incomeFieldLength;
    @JacksonXmlProperty(localName = "Income")
    private String income;
    @JacksonXmlProperty(localName = "NetGrossIndicator")
    private String netGrossIndicator;
    @JacksonXmlProperty(localName = "MonthlyAnnualIndicator")
    private String monthlyAnnualIndicator;
    @JacksonXmlProperty(localName = "DateOfEntryForErrorCode")
    private String dateOfEntryForErrorCode;
    @JacksonXmlProperty(localName = "ErrorCode")
    private String errorCode;
    @JacksonXmlProperty(localName = "DateOfEntryForCIBILRemarksCode")
    private String dateOfEntryForCibilRemarksCode;
    @JacksonXmlProperty(localName = "CIBILRemarksCode")
    private String cibilRemarksCode;
    @JacksonXmlProperty(localName = "DateOfEntryForErrorDisputeRemarksCode")
    private String dateOfEntryForErrorDisputeRemarksCode;
    @JacksonXmlProperty(localName = "ErrorDisputeRemarksCode1")
    private String errorDisputeRemarksCode1;
    @JacksonXmlProperty(localName = "ErrorDisputeRemarksCode2")
    private String errorDisputeRemarksCode2;
    @JacksonXmlProperty(localName = "FID")
    private String fid;
    @JacksonXmlProperty(localName = "SNo")
    private String sNo;
    @JacksonXmlProperty(localName = "SuppressFlag")
    private String suppressFlag;
    @JacksonXmlProperty(localName = "DateOfSuppression")
    private String dateOfSuppression;
}