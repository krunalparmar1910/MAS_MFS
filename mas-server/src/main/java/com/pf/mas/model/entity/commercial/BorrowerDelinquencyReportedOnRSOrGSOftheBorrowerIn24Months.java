package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bdr_rs_gs_of_borrower_in_24_months_data", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class BorrowerDelinquencyReportedOnRSOrGSOftheBorrowerIn24Months extends BaseID {

	private String detail;

	private String relationship;

	private String tlOutstanding;

	private String wcCount;

	private String wcOutstanding;

	private String nfCount;

	private String fxCount;

	private String fxOutstanding;

	private String tlCount;

	private String nfOutstanding;
}