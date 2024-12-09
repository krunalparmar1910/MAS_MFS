package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "EmailContactSegment")
public class EmailContactSegmentDTO {
    @JacksonXmlProperty(localName = "EmailID")
    private String emailId;

    @JacksonXmlProperty(localName = "Length")
    private String length;

    @JacksonXmlProperty(localName = "EmailIDFieldLength")
    private Long emailIdFieldLength;

    @JacksonXmlProperty(localName = "SegmentTag")
    private String segmentTag;

    @JacksonXmlProperty(localName = "FID")
    private String fid;
    @JacksonXmlProperty(localName = "SNo")
    private String sNo;
    @JacksonXmlProperty(localName = "SuppressFlag")
    private String suppressFlag;
    @JacksonXmlProperty(localName = "DateOfSuppression")
    private String dateOfSuppression;
}