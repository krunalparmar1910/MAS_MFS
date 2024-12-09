package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "derogatory_information_sec", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class DerogatoryInformationSec extends BaseID {
	private String messageOfBorrower;
	private String messageOfBorrowerYourInstitution;
	private String messageOfBorrowerOutsideInstitution;
	private String messageOfRelatedParties;
	private String messageOfRelatedPartiesYourInstitution;
	private String messageOfRelatedPartiesOutsideInstitution;
	private String messageOfGuarantedParties;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "derogatory_information_borrower_id")
	private DerogatoryInformationBorrower derogatoryInformationBorrower;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "derogatory_information_on_related_parties_id")
	private DerogatoryInformationOnRelatedPartiesOrGuarantorsOfBorrowerSec derogatoryInformationOnRelatedPartiesOrGuarantorsOfBorrowerSec;
	@ElementCollection
	@CollectionTable(name = "di_reported_guaranteed_parties", schema = Constants.CIBIL_DB_SCHEMA, joinColumns = @JoinColumn(name = "derogatory_information_sec_id", nullable=false))
	@Column(name = "di_reported")
	private List<String> derogatoryInformationReportedOnGuarantedPartiesVec;
}