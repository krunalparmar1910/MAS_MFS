package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Enquiry")
public class EnquiryDTO {
    @JacksonXmlProperty(localName = "EnquiryPurpose")
    private String enquiryPurpose;

    @JacksonXmlProperty(localName = "EnquiryAmountFieldLength")
    private String enquiryAmountFieldLength;

    @JacksonXmlProperty(localName = "Length")
    private String length;

    @JacksonXmlProperty(localName = "EnquiringMemberShortNameFieldLength")
    private Long enquiringMemberShortNameFieldLength;

    @JacksonXmlProperty(localName = "EnquiringMemberShortName")
    private String enquiringMemberShortName;

    @JacksonXmlProperty(localName = "DateOfEnquiryFields")
    private String dateOfEnquiryFields;

    @JacksonXmlProperty(localName = "EnquiryAmount")
    private Long enquiryAmount;

    @JacksonXmlProperty(localName = "SegmentTag")
    private String segmentTag;

    @JacksonXmlProperty(localName = "FID")
    private String fid;
    @JacksonXmlProperty(localName = "EnquiryControlNumber")
    private String enquiryControlNumber;
    @JacksonXmlProperty(localName = "SuppressFlag")
    private String suppressFlag;
    @JacksonXmlProperty(localName = "DateOfSuppression")
    private String dateOfSuppression;
}