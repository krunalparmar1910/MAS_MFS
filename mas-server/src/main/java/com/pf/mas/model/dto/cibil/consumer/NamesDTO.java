package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "Names")
public class NamesDTO {
    @JacksonXmlProperty(localName = "Name")
    @JacksonXmlElementWrapper(localName = "Name", useWrapping = false)
    private List<NameDTO> nameDTOList;
}