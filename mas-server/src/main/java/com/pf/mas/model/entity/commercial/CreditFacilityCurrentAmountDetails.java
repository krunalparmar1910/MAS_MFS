package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "credit_facility_current_amount_details", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CreditFacilityCurrentAmountDetails extends BaseID {
	private String currency;
	private Long sanctionedAmt;
	private Long drawingPower;
	private Long outstandingBalance;
	private String markToMarket;
	private Long overdue;
	private Long highCredit;
	private Long installmentAmt;
	private String suitFiledAmt;
	private Long lastRepaid;
	@Column(name = "written_off")
	private Long writtenOFF;
	private Long settled;
	private String naorc;
	@Column(name = "contracts_classified_as_npa")
	private String contractsClassifiedAsNPA;
	private String notionalAmountOfContracts;
}