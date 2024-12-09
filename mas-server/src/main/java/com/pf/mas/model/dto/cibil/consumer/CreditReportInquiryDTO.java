package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "CreditReportInquiry")
public class CreditReportInquiryDTO {
    @JacksonXmlProperty(localName = "Names")
    private NamesDTO namesDTO;

    @JacksonXmlProperty(localName = "Addresses")
    private CriAddressesDTO criAddressesDTO;

    @JacksonXmlProperty(localName = "Header")
    private CriHeaderDTO criHeaderDTO;

    @JacksonXmlProperty(localName = "Telephones")
    private List<String> telephoneList;

    @JacksonXmlProperty(localName = "Identifications")
    private IdentificationsDTO identificationsDTO;
}