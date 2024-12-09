package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
public class EnvelopeDTO {

    @JacksonXmlProperty(localName = "Body")
    private BodyDTO bodyDTO;
}
