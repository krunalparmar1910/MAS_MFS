package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Account")
public class AccountDTO {
    @JacksonXmlProperty(localName = "Account_Summary_Segment_Fields")
    private AccountSummarySegmentFieldsDTO accountSummarySegmentFieldsDTO;
    @JacksonXmlProperty(localName = "Length")
    private String length;
    @JacksonXmlProperty(localName = "SegmentTag")
    private String segmentTag;
    @JacksonXmlProperty(localName = "Account_NonSummary_Segment_Fields")
    private AccountNonSummarySegmentFieldsDTO accountNonSummarySegmentFieldsDTO;
}