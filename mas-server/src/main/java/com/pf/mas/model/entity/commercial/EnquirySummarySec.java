package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "enquiry_summary_sec", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class EnquirySummarySec extends BaseID {

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enquiry_your_institution_id")
	private EnquiryDetails enquiryYourInstitution;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enquiry_outside_institution_id")
	private EnquiryDetails enquiryOutsideInstitution;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enquiry_total_id")
	private EnquiryDetails enquiryTotal;
}