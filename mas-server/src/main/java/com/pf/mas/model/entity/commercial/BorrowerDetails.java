package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "borrower_details", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class BorrowerDetails extends BaseID {
	private String name;
	private String borrowersLegalConstitution;
	@ElementCollection
	@CollectionTable(name = "borrower_details_class_of_activity", schema = Constants.CIBIL_DB_SCHEMA, joinColumns = @JoinColumn(name = "borrower_details_id", nullable=false))
	@Column(name = "class_of_activity")
	private List<String> classOfActivityVec;
	private String businessCategory;
	private String businessIndustryType;
	private String dateOfIncorporation;
	private String salesFigure;
	private String numberOfEmployees;
	private String year;
}