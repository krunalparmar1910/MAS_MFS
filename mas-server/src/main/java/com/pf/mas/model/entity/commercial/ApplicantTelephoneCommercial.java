package com.pf.mas.model.entity.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "applicant_telephone_commercial", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ApplicantTelephoneCommercial extends BaseID {
	private String telephoneType;
	private String telephoneNumber;
	private String telephoneExtension;
	private String contactPrefix;
	private String contactArea;
}