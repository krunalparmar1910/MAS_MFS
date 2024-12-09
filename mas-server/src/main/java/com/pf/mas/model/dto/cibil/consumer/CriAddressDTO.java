package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "CriAddress")
public class CriAddressDTO {
    @JacksonXmlProperty(localName = "AddressLine1")
    private String addressLine1;

    @JacksonXmlProperty(localName = "AddressLine2")
    private String addressLine2;

    @JacksonXmlProperty(localName = "AddressLine3")
    private String addressLine3;

    @JacksonXmlProperty(localName = "AddressLine5")
    private String addressLine5;

    @JacksonXmlProperty(localName = "AddressLine4")
    private String addressLine4;

    @JacksonXmlProperty(localName = "AddressCategory")
    private String addressCategory;

    @JacksonXmlProperty(localName = "StateCode")
    private String stateCode;

    @JacksonXmlProperty(localName = "ResidenceCode")
    private String residenceCode;

    @JacksonXmlProperty(localName = "PinCode")
    private String pinCode;

}