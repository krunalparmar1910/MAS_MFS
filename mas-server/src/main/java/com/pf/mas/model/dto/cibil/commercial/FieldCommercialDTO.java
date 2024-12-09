package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "Field")
public class FieldCommercialDTO {

    @JacksonXmlElementWrapper(localName = "Applicants", useWrapping = false)
    @JacksonXmlProperty(localName = "Applicants")
    private ApplicantsCommercialDTO applicantsCommercialDTO;

    @JacksonXmlProperty(localName = "key", isAttribute = true)
    private String key;

    @JacksonXmlElementWrapper(localName = "ApplicationData", useWrapping = false)
    @JacksonXmlProperty(localName = "ApplicationData")
    private ApplicationDataCommercialDTO applicationDataDTO;

    @JacksonXmlText
    private String value;
}