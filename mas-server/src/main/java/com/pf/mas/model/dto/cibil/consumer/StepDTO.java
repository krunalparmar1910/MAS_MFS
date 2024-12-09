package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Step")
public class StepDTO {
    @JacksonXmlProperty(localName = "Duration")
    private String duration;
    @JacksonXmlProperty(localName = "Name")
    private String name;

}