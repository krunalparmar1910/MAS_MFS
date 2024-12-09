package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "creditFacilityDetailsasBorrowerSec")
public class CreditFacilityDetailsAsBorrowerSecDTO {
	private String message;
	@JacksonXmlProperty(localName = "creditFacilityCurrentDetailsVec")
	private CreditFacilityCurrentDetailsVecDTO creditFacilityCurrentDetailsVecDTO;

	@JacksonXmlProperty(localName = "CFHistoryforACOrDPDVec")
	private CfHistoryForAcOrDpdVecDTO cfHistoryForAcOrDpdVecDTO;

	@JacksonXmlProperty(localName = "creditFacilityOverdueDetailsVec")
	private CreditFacilityOverdueDetailsVecDTO creditFacilityOverdueDetailsVecDTO;
	@JacksonXmlProperty(localName = "chequeDishounouredDuetoInsufficientFunds")
	private ChequeDishounouredDuetoInsufficientFundsDTO chequeDishounouredDuetoInsufficientFundsDTO;
	@JacksonXmlProperty(localName = "creditFacilitySecurityDetailsVec")
	private CreditFacilitySecurityDetailsVecDTO creditFacilitySecurityDetailsVecDTO;
	@JacksonXmlProperty(localName = "creditFacilityGuarantorDetailsVec")
	private CreditFacilityGuarantorDetailsVecDTO creditFacilityGuarantorDetailsVecDTO;
}