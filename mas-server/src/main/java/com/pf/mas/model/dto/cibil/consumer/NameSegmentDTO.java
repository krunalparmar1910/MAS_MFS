package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "NameSegment")
public class NameSegmentDTO {

    @JacksonXmlProperty(localName = "DateOfBirth")
    private String dateOfBirth;

    @JacksonXmlProperty(localName = "ConsumerName1")
    private String consumerName1;

    @JacksonXmlProperty(localName = "ConsumerName2")
    private String consumerName2;

    @JacksonXmlProperty(localName = "ConsumerName3")
    private String consumerName3;

    @JacksonXmlProperty(localName = "ConsumerName4")
    private String consumerName4;

    @JacksonXmlProperty(localName = "ConsumerName5")
    private String consumerName5;

    @JacksonXmlProperty(localName = "ConsumerName1FieldLength")
    private Long consumerName1FieldLength;

    @JacksonXmlProperty(localName = "ConsumerName2FieldLength")
    private Long consumerName2FieldLength;

    @JacksonXmlProperty(localName = "ConsumerName3FieldLength")
    private Long consumerName3FieldLength;

    @JacksonXmlProperty(localName = "ConsumerName4FieldLength")
    private Long consumerName4FieldLength;

    @JacksonXmlProperty(localName = "ConsumerName5FieldLength")
    private Long consumerName5FieldLength;

    @JacksonXmlProperty(localName = "Length")
    private String length;

    @JacksonXmlProperty(localName = "GenderFieldLength")
    private String genderFieldLength;

    @JacksonXmlProperty(localName = "SegmentTag")
    private String segmentTag;

    @JacksonXmlProperty(localName = "DateOfBirthFieldLength")
    private String dateOfBirthFieldLength;



    @JacksonXmlProperty(localName = "Gender")
    private String gender;

    @JacksonXmlProperty(localName = "DateOfEntryForErrorCode")
    private String dateOfEntryForErrorCode;
    @JacksonXmlProperty(localName = "ErrorSegmentTag")
    private String errorSegmentTag;
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
    @JacksonXmlProperty(localName = "EnrichThroughEnquiryForName")
    private String enrichThroughEnquiryForName;
    @JacksonXmlProperty(localName = "FID")
    private String fid;
    @JacksonXmlProperty(localName = "EnrichThroughEnquiryForDateOfBirth")
    private String enrichThroughEnquiryForDateOfBirth;
    @JacksonXmlProperty(localName = "EnrichThroughEnquiryForGender")
    private String enrichThroughEnquiryForGender;
    @JacksonXmlProperty(localName = "DateOfSuppression")
    private String dateOfSuppression;
}