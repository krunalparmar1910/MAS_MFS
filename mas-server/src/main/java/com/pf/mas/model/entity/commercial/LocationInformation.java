package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "location_information", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class LocationInformation extends BaseID {
	private String borrowerOfficeLocationType;
	private String address;
	private String firstReportedDate;
	private String lastReportedDate;
	private Long numberOfInstitutions;
}