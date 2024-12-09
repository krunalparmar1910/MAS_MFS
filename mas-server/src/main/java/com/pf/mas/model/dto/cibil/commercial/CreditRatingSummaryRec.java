package com.pf.mas.model.dto.cibil.commercial;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "credit_rating_summary_rec", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CreditRatingSummaryRec extends BaseID {
	private String creditRatingAgency;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
	@JsonBackReference
	@JoinColumn(name = "credit_rating_summary_rec_id")
	private List<CreditRatingSummaryDetailsVec> creditRatingSummaryDetailsVecList;
}