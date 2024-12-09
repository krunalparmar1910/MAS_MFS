package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "TelephoneSegment")
public class TelephoneSegmentDTO {
    @JacksonXmlProperty(localName = "TelephoneNumberFieldLength")
    private Long telephoneNumberFieldLength;

    @JacksonXmlProperty(localName = "TelephoneExtensionFieldLength")
    private Long telephoneExtensionFieldLength;

    @JacksonXmlProperty(localName = "Length")
    private String length;

    @JacksonXmlProperty(localName = "SegmentTag")
    private String segmentTag;

    @JacksonXmlProperty(localName = "TelephoneType")
    private String telephoneType;

    @JacksonXmlProperty(localName = "TelephoneNumber")
    private String telephoneNumber;

    @JacksonXmlProperty(localName = "EnrichedThroughEnquiry")
    private String enrichedThroughEnquiry;

    @JacksonXmlProperty(localName = "TelephoneExtension")
    private String telephoneExtension;

    @JacksonXmlProperty(localName = "FID")
    private String fid;
    @JacksonXmlProperty(localName = "SNo")
    private String sNo;
    @JacksonXmlProperty(localName = "SuppressFlag")
    private String suppressFlag;
    @JacksonXmlProperty(localName = "DateOfSuppression")
    private String dateOfSuppression;
}