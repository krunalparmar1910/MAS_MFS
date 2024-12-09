package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "outsideInstitution")
public class CpsOutsideInstitutionDTO {
	@JacksonXmlProperty(localName = "otherPublicSectorBanks")
	public CpsInstitutionWiseDTO otherPublicSectorBanksDTO;
	@JacksonXmlProperty(localName = "otherPrivateForeignBanks")
	public CpsInstitutionWiseDTO otherPrivateForeignBanksDTO;
	@JacksonXmlProperty(localName = "NBFC_Others")
	public CpsInstitutionWiseDTO nbfcOthersDTO;
	@JacksonXmlProperty(localName = "OutsideTotal")
	public CpsInstitutionWiseDTO outsideTotalDTO;
}
