package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Field")
public class FieldDTO {

    @JacksonXmlElementWrapper(localName = "Applicants", useWrapping = false)
    @JacksonXmlProperty(localName = "Applicants")
    private ApplicantsDTO applicantsDTO;

    @JacksonXmlProperty(localName = "key", isAttribute = true)
    private String key;

    @JacksonXmlElementWrapper(localName = "ApplicationData", useWrapping = false)
    @JacksonXmlProperty(localName = "ApplicationData")
    private ApplicationDataDTO applicationDataDTO;

    @JacksonXmlText
    private String value;
}