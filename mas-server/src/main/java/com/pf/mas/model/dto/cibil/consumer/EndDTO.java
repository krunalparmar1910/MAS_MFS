package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "End")
public class EndDTO {
    @JacksonXmlProperty(localName = "TotalLength")
    private String totalLength;
    @JacksonXmlProperty(localName = "SegmentTag")
    private String segmentTag;
}