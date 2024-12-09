package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "ContextData")
public class ContextDataDTO {
    @JacksonXmlProperty(localName = "Field")
    @JacksonXmlElementWrapper(useWrapping = false, localName = "Field")
    private List<FieldDTO> fieldDTOList;
}