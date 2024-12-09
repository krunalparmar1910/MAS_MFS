package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Identification")
public class IdentificationDTO {
    @JacksonXmlProperty(localName = "UId")
    private String uId;

    @JacksonXmlProperty(localName = "AdditionalID1")
    private String additionalID1;

    @JacksonXmlProperty(localName = "VoterId")
    private String voterId;

    @JacksonXmlProperty(localName = "PassportNumber")
    private String passportNumber;

    @JacksonXmlProperty(localName = "DLNo")
    private String dLNo;

    @JacksonXmlProperty(localName = "PanNo")
    private String panNo;

    @JacksonXmlProperty(localName = "RationCardNo")
    private String rationCardNo;

    @JacksonXmlProperty(localName = "AdditionalID2")
    private String additionalID2;

    @JacksonXmlProperty(localName = "emailID")
    private String emailId;
}