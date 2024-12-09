package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.Column;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "CreditReportHeader")
public class CreditReportHeaderDTO {
    @JacksonXmlProperty(localName = "EnquiryControlNumber")
    private Long enquiryControlNumber;

    @JacksonXmlProperty(localName = "Version")
    private Long version;

    @JacksonXmlProperty(localName = "ReferenceNumber")
    private Long referenceNumber;

    @JacksonXmlProperty(localName = "FutureUse1")
    private String futureUse1;

    @JacksonXmlProperty(localName = "FutureUse2")
    private String futureUse2;

    @JacksonXmlProperty(localName = "TimeProcessed")
    private Long timeProcessed;

    @JacksonXmlProperty(localName = "DateProcessed")
    private String dateProcessed;

    @JacksonXmlProperty(localName = "SegmentTag")
    private String segmentTag;

    @JacksonXmlProperty(localName = "SubjectReturnCode")
    private Long subjectReturnCode;

    @JacksonXmlProperty(localName = "MemberCode")
    private String memberCode;
}