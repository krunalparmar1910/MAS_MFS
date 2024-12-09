package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.pf.mas.model.dto.cibil.commercial.ApplicantTelephoneCommercialDTO;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "Applicant")
public class ApplicantDTO {
    @JacksonXmlProperty(localName = "DateOfBirth")
    private String dateOfBirth;

    @JacksonXmlProperty(localName = "ApplicantFirstName")
    private String applicantFirstName;


    @JacksonXmlProperty(localName = "ApplicantLastName")
    private String applicantLastName;

    @JacksonXmlProperty(localName = "ApplicantMiddleName")
    private String applicantMiddleName;

    @JacksonXmlProperty(localName = "Gender")
    private String gender;

    @JacksonXmlProperty(localName = "EmailAddress")
    private String emailAddress;

    @JacksonXmlProperty(localName = "ApplicantType")
    private String applicantType;

    @JacksonXmlProperty(localName = "Telephones")
    private ApplicantTelephonesDTO applicantTelephonesDTO;

    @JacksonXmlProperty(localName = "NodeIdentifier")
    private Long nodeIdentifier;

    @JacksonXmlProperty(localName = "DsAadharEkycOtp")
    private String dsAadharEkycOtp;

    @JacksonXmlProperty(localName = "Addresses")
    private ApplicantAddressesDTO addresses;

    @JacksonXmlProperty(localName = "Identifiers")
    private IdentifiersDTO identifiersDTO;

    @JacksonXmlProperty(localName = "DsCibilBureau")
    private DsCibilBureauDTO dsCibilBureauDTO;


}