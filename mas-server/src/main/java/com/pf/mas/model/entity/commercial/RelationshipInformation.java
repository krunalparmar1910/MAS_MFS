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
@Table(name = "relationship_information", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class RelationshipInformation extends BaseID {
	private String name;
	private String relatedType;
	private String relationship;
	private String percentageOfControl;
	private String dateOfBirth;
	private String dateOfIncorporation;
	private String gender;
	private String businessCategory;
	private String businessIndustryType;
	@Column(name = "class_of_activity_1")
	private String classOfActivity1;
}
