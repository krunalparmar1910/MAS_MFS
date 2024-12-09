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
@Table(name = "enquiry_details", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class EnquiryDetails extends BaseID {
	private Long month1;
	private Long month2to3;
	private Long month4to6;
	private Long month7to12;
	private Long month12to24;
	@Column(name = "greater_than_24_month")
	private Long greaterThan24Month;
	private Long total;
	private String mostRecentDate;
}