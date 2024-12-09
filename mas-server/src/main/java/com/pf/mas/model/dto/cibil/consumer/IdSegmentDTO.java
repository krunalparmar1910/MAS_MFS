package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JacksonXmlRootElement(localName = "IDSegment")
@AllArgsConstructor
@NoArgsConstructor
public class IdSegmentDTO {

    @JacksonXmlProperty(localName = "Length")
    private String length;

    @JacksonXmlProperty(localName = "IDNumberFieldLength")
    private String idNumberFieldLength;

    @JacksonXmlProperty(localName = "SegmentTag")
    private String segmentTag;

    @JacksonXmlProperty(localName = "IDType")
    private String idType;

    @JacksonXmlProperty(localName = "IDNumber")
    private String idNumber;

    @JacksonXmlProperty(localName = "IssueDate")
    private String issueDate;

    @JacksonXmlProperty(localName = "EnrichedThroughEnquiry")
    private String enrichedThroughEnquiry;

    @JacksonXmlProperty(localName = "FID")
    private String fid;
    @JacksonXmlProperty(localName = "SNo")
    private String sNo;
    @JacksonXmlProperty(localName = "SuppressFlag")
    private String suppressFlag;
    @JacksonXmlProperty(localName = "DateOfSuppression")
    private String dateOfSuppression;

}