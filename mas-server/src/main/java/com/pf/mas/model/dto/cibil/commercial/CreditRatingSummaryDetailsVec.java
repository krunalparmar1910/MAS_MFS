package com.pf.mas.model.dto.cibil.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "credit_rating_summary_details_vec", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CreditRatingSummaryDetailsVec extends BaseID {
	private String creditRating;
	private String ratingAsOn;
	private String ratingExpiryDt;
	private String lastReportedDt;
}