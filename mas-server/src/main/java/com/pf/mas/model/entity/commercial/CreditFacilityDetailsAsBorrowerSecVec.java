package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "credit_facility_details_as_borrower_sec_vec", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CreditFacilityDetailsAsBorrowerSecVec extends BaseID {
	private String message;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
	@JoinColumn(name = "credit_facility_details_as_borrower_sec_vec_id")
	private List<CreditFacilityDetailsAsBorrowerSec> creditFacilityDetailsAsBorrowerSec;
}