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
@Table(name = "credit_facilities_details_sec", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CreditFacilitiesDetailsSec extends BaseID {
	private String creditType;
	@Column(name = "group_name")
	private String group;
	private String ownership;
	private String accountNo;
	private String reportedBy;
	private String currentBalance;
	private String closedDate;
	private String lastReported;
}