package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "credit_facility_current_details", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CreditFacilityCurrentDetails extends BaseID {
	private String derivative;
	private String accountNumber;
	private String cfSerialNumber;
	private String cfType;
	private String cfMember;
	private String assetClassificationDaysPastDueDpd;
	private String status;
	private String statusDate;
	private String lastReportedDate;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "amount_id")
	private CreditFacilityCurrentAmountDetails amount;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "dates_id")
	private CreditFacilityCurrentDatesDetail dates;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "other_details_id")
	private CreditFacilityCurrentMiscDetail otherDetails;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "credit_facility_dispute_id")
	private DisputeRemarks creditFacilityDispute;
}