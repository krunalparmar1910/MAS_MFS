package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "DsCibilBureau")
public class DsCibilBureauDTO {
    @JacksonXmlProperty(localName = "DsCibilBureauData")
    private DsCibilBureauDataDTO dsCibilBureauDataDTO;

    @JacksonXmlProperty(localName = "Response")
    private ResponseDTO responseDTO;

    @JacksonXmlProperty(localName = "DsCibilBureauStatus")
    private DsCibilBureauStatusDTO dsCibilBureauStatusDTO;

    @JacksonXmlProperty(localName = "Document")
    private DocumentDTO documentDTO;
}