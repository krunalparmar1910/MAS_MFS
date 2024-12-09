package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "borrowerProfileSec")
public class BorrowerProfileSecDTO {

    @JacksonXmlProperty(localName = "borrwerDetails")
    private BorrowerDetailsDTO borrowerDetailsDTO;

    @JacksonXmlProperty(localName = "borrwerAddressContactDetails")
    private BorrowerAddressContactDetailsDTO borrowerAddressContactDetailsDTO;

    @JacksonXmlProperty(localName = "borrwerIDDetailsVec")
    private BorrowerIdDetailsVecDTO borrowerIdDetailsVecDTO;

    @JacksonXmlProperty(localName = "borrowerDelinquencyReportedOnBorrower")
    private BorrowerDelinquencyReportedOnBorrowerDTO borrowerDelinquencyReportedOnBorrowerDTO;

    @JacksonXmlProperty(localName = "borrowerDelinquencyReportedOnRSOrGSOftheBorrowerIn24MonthsVec")
    private BorrowerDelinquencyReportedOnRSOrGSOftheBorrowerIn24MonthsVecDTO borrowerDelinquencyReportedOnRSOrGSOftheBorrowerIn24MonthsVecDTO;

    @JacksonXmlProperty(localName = "borrowerDispute")
    private DisputeRemarksDTO disputeRemarksDTO;
}
