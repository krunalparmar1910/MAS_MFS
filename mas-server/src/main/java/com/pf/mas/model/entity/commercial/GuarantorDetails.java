package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "guarantor_details", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class GuarantorDetails extends BaseID {
	private String name;
	private String relatedType;
	private String dateOfBirth;
	private String gender;
	private String dateOfIncorporation;
	private String businessCategory;
	private String businessIndustryType;
	private String message;
}