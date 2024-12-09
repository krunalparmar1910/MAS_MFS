package com.pf.mas.model.dto.cibil.consumer;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Address")
public class ApplicantAddressDTO {
    @JacksonXmlProperty(localName = "ResidenceType")
    private String residenceType;

    @JacksonXmlProperty(localName = "StateCode")
    private String stateCode;

    @JacksonXmlProperty(localName = "City")
    private String city;

    @JacksonXmlProperty(localName = "PinCode")
    private String pinCode;

    @JacksonXmlProperty(localName = "AddressLine1")
    private String addressLine1;

    @JacksonXmlProperty(localName = "AddressLine2")
    private String addressLine2;

    @JacksonXmlProperty(localName = "AddressLine3")
    private String addressLine3;

    @JacksonXmlProperty(localName = "AddressLine4")
    private String addressLine4;

    @JacksonXmlProperty(localName = "AddressLine5")
    private String addressLine5;

    @JacksonXmlProperty(localName = "AddressType")
    private String addressType;

    @JacksonXmlProperty(localName = "AccountNumber", isAttribute = true)
    private String accountNumber;

    @JacksonXmlProperty(localName = "MemberOtherId4Type")
    private String memberOtherId4Type;

    @JacksonXmlProperty(localName = "MemberOtherId4")
    private String memberOtherId4;

    @JacksonXmlProperty(localName = "MemberOtherId3Type")
    private String memberOtherId3Type;

    @JacksonXmlProperty(localName = "MemberOtherId3")
    private String memberOtherId3;

    @JacksonXmlProperty(localName = "MemberOtherId2Type")
    private String memberOtherId2Type;

    @JacksonXmlProperty(localName = "MemberOtherId2")
    private String memberOtherId2;

    @JacksonXmlProperty(localName = "MemberOtherId1Type")
    private String memberOtherId1Type;

    @JacksonXmlProperty(localName = "MemberOtherId1")
    private String memberOtherId1;

    @JacksonXmlProperty(localName = "NomineeRelation")
    private String nomineeRelation;

    @JacksonXmlProperty(localName = "NomineeName")
    private String nomineeName;

    @JacksonXmlProperty(localName = "MemberRelationType4")
    private String memberRelationType4;

    @JacksonXmlProperty(localName = "MemberRelationName4")
    private String memberRelationName4;

    @JacksonXmlProperty(localName = "MemberRelationType3")
    private String memberRelationType3;

    @JacksonXmlProperty(localName = "MemberRelationName3")
    private String memberRelationName3;

    @JacksonXmlProperty(localName = "MemberRelationType2")
    private String memberRelationType2;

    @JacksonXmlProperty(localName = "MemberRelationName2")
    private String memberRelationName2;

    @JacksonXmlProperty(localName = "MemberRelationType1")
    private String memberRelationType1;

    @JacksonXmlProperty(localName = "MemberRelationName1")
    private String memberRelationName1;

    @JacksonXmlProperty(localName = "KeyPersonName")
    private String keyPersonName;

    @JacksonXmlProperty(localName = "KeyPersonRelation")
    private String keyPersonRelation;

    @JacksonXmlProperty(localName = "CompanyName")
    private String companyName;

    @JacksonXmlProperty(localName = "EmailAddress")
    private String emailAddress;

    @JacksonXmlProperty(localName = "Gender")
    private String gender;

    @JacksonXmlProperty(localName = "DateOfBirth")
    private String dateOfBirth;

    @JacksonXmlProperty(localName = "ApplicantLastName")
    private String applicantLastName;

    @JacksonXmlProperty(localName = "ApplicantMiddleName")
    private String applicantMiddleName;

    @JacksonXmlProperty(localName = "ApplicantFirstName")
    private String applicantFirstName;

    @JacksonXmlProperty(localName = "ApplicantType")
    private String applicantType;

    @JacksonXmlProperty(localName = "NodeIdentifier")
    private String nodeIdentifier;



}