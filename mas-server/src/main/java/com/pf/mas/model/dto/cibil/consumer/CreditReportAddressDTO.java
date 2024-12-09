package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Address")
public class CreditReportAddressDTO {
    @JacksonXmlProperty(localName = "AddressLine1")
    private String addressLine1;

    @JacksonXmlProperty(localName = "AddressLine2")
    private String addressLine2;

    @JacksonXmlProperty(localName = "AddressCategory")
    private String addressCategory;

    @JacksonXmlProperty(localName = "Length")
    private String length;

    @JacksonXmlProperty(localName = "DateReported")
    private String dateReported;

    @JacksonXmlProperty(localName = "AddressSegmentTag")
    private String addressSegmentTag;

    @JacksonXmlProperty(localName = "StateCode")
    private String stateCode;

    @JacksonXmlProperty(localName = "SegmentTag")
    private String segmentTag;

    @JacksonXmlProperty(localName = "AddressLine2FieldLength")
    private Long addressLine2FieldLength;

    @JacksonXmlProperty(localName = "PinCodeFieldLength")
    private String pinCodeFieldLength;

    @JacksonXmlProperty(localName = "AddressLine1FieldLength")
    private Long addressLine1FieldLength;

    @JacksonXmlProperty(localName = "PinCode")
    private String pinCode;

    @JacksonXmlProperty(localName = "AddressLine4")
    private String addressLine4;

    @JacksonXmlProperty(localName = "AddressLine4FieldLength")
    private String addressLine4FieldLength;

    @JacksonXmlProperty(localName = "AddressLine5")
    private String addressLine5;

    @JacksonXmlProperty(localName = "AddressLine5FieldLength")
    private String addressLine5FieldLength;

    @JacksonXmlProperty(localName = "AddressLine3")
    private String addressLine3;

    @JacksonXmlProperty(localName = "AddressLine3FieldLength")
    private Long addressLine3FieldLength;

    @JacksonXmlProperty(localName = "ResidenceCode")
    private String residenceCode;

    @JacksonXmlProperty(localName = "EnrichedThroughEnquiry")
    private String enrichedThroughEnquiry;

    @JacksonXmlProperty(localName = "MemberShortNameFieldLength")
    private String memberShortNameFieldLength;

    @JacksonXmlProperty(localName = "MemberShortName")
    private String memberShortName;

    @JacksonXmlProperty(localName = "FID")
    private String fid;

    @JacksonXmlProperty(localName = "SNo")
    private String sNo;

    @JacksonXmlProperty(localName = "SuppressFlag")
    private String suppressFlag;

    @JacksonXmlProperty(localName = "DateOfSuppression")
    private String dateOfSuppression;
}

