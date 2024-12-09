package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "amount")
public class CreditFacilityCurrentAmountDetailsDTO {
	private String currency;
	private Long sanctionedAmt;
	private Long drawingPower;
	private Long outstandingBalance;
	private Long overdue;
	private String markToMarket;
	private Long highCredit;
	private Long installmentAmt;
	private String suitFiledAmt;
	private Long lastRepaid;
	private Long writtenOFF;
	private Long settled;
	private String naorc;
	private String contractsClassifiedAsNPA;
	private String notionalAmountOfContracts;
}