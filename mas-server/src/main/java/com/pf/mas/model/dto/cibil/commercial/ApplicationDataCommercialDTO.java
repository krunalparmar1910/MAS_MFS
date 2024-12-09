package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "ApplicationData")
public class ApplicationDataCommercialDTO {
    @JacksonXmlProperty(localName = "Amount")
    private Long amount;

    @JacksonXmlProperty(localName = "LitigationReport")
    private String litigationReport;

    @JacksonXmlProperty(localName = "FormattedReport")
    private String formattedReport;

    @JacksonXmlProperty(localName = "MSMERank")
    private String msmeRank;

    @JacksonXmlProperty(localName = "CommercialCIR")
    private String commercialCir;

    @JacksonXmlProperty(localName = "ConsumerCIR")
    private String consumerCir;

    @JacksonXmlProperty(localName = "GSTStateCode")
    private String gstStateCode;

    @JacksonXmlProperty(localName = "ExecutionType")
    private String executionType;

    @JacksonXmlProperty(localName = "InputValReasonCodes")
    private String inputValReasonCodes;

    @JacksonXmlProperty(localName = "TestCountApplist")
    private Long testCountAppList;

    @JacksonXmlProperty(localName = "ExecuteParallel")
    private String executeParallel;

    @JacksonXmlProperty(localName = "Purpose")
    private Long purpose;

    @JacksonXmlProperty(localName = "customerConsent")
    private String customerConsent;

    @JacksonXmlProperty(localName = "companyPANCheck")
    private String companyPanCheck;

    @JacksonXmlProperty(localName = "noOfDirector")
    private Long noOfDirector;

    @JacksonXmlProperty(localName = "COMBStartTime")
    private String combStartTime;

    @JacksonXmlProperty(localName = "COMBEndTime")
    private String combEndTime;
}