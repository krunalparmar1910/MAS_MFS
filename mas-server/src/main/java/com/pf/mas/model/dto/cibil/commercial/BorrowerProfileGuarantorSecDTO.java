package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "borrwerInfo")
public class BorrowerProfileGuarantorSecDTO {
    @JacksonXmlProperty(localName = "borrwerDetails")
    private BorrowerDetailsDTO borrowerDetailsDTO;

    @JacksonXmlElementWrapper(localName = "borrwerAddressContactDetailsVec")
    @JacksonXmlProperty(localName = "borrwerAddressContactDetails")
    private List<BorrowerAddressContactDetailsDTO> borrowerAddressContactDetailsDTOList;

    @JacksonXmlProperty(localName = "borrwerIDDetailsVec")
    private BorrowerIdDetailsVecDTO borrowerIdDetailsVecDTO;
    
}
