package com.pf.mas.model.dto.cibil.consumer;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "ApplicationData")
public class ApplicationDataDTO {
    @JacksonXmlProperty(localName = "User")
    private String user;

    @JacksonXmlProperty(localName = "DTTrail")
    private DTTrailDTO dTTrailDTO;

    @JacksonXmlProperty(localName = "Start")
    private String start;

    @JacksonXmlProperty(localName = "Milestone")
    private MilestoneDTO milestoneDTO;

    @JacksonXmlProperty(localName = "Amount")
    private Long amount;

    @JacksonXmlProperty(localName = "BusinessUnitId")
    private Long businessUnitId;

    @JacksonXmlProperty(localName = "InputValReasonCodes")
    private String inputValReasonCodes;

    @JacksonXmlProperty(localName = "ReturnMessage")
    private String returnMessage;

    @JacksonXmlProperty(localName = "Purpose")
    private String purpose;

    @JacksonXmlProperty(localName = "EnvironmentTypeId")
    private Long environmentTypeId;

    @JacksonXmlProperty(localName = "CibilBureauFlag")
    private Boolean cibilBureauFlag;

    @JacksonXmlProperty(localName = "GSTStateCode")
    private String gstStateCode;

    @JacksonXmlProperty(localName = "SolutionSetId")
    private Long solutionSetId;

    @JacksonXmlProperty(localName = "EnvironmentType")
    private String environmentType;

    @JacksonXmlProperty(localName = "ScoreType")
    private String scoreType;

    @JacksonXmlProperty(localName = "ApplicationId")
    private Long applicationId;

    @JacksonXmlProperty(localName = "FormattedReport")
    private Boolean formattedReport;

    @JacksonXmlProperty(localName = "MemberCode")
    private String memberCode;

    @JacksonXmlProperty(localName = "Password")
    private String password;


    @JacksonXmlProperty(localName = "CallerApplicationID")
    private String callerApplicationId;

    @JacksonXmlProperty(localName = "MFIBranchReferenceNo")
    private String mfiBranchReferenceNo;

    @JacksonXmlProperty(localName = "MFICenterReferenceNo")
    private String mfiCenterReferenceNo;

    @JacksonXmlProperty(localName = "MFILoanPurpose")
    private Long mfiLoanPurpose;

    @JacksonXmlProperty(localName = "MFIEnquiryAmount")
    private Long mfiEnquiryAmount;

    @JacksonXmlProperty(localName = "MFIBureauFlag")
    private boolean mfiBureauFlag;

    @JacksonXmlProperty(localName = "IDVerificationFlag")
    private boolean idVerificationFlag;

    @JacksonXmlProperty(localName = "DSTuNtcFlag")
    private boolean dstuNtcFlag;

    @JacksonXmlProperty(localName = "ConsumerConsentForUIDAIAuthentication")
    private String consumerConsentForUidaiAuthentication;

    @JacksonXmlProperty(localName = "NTCProductType")
    private String ntcProductType;

    @JacksonXmlProperty(localName = "MFIDocRequest")
    private String mfiDocRequest;

    @JacksonXmlProperty(localName = "CAASTUEFVersionEmail")
    private Boolean caastUefVersionEmail;

    @JacksonXmlProperty(localName = "Version")
    private String version;
}