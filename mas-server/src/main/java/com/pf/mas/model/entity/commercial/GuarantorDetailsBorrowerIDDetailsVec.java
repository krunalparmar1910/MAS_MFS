package com.pf.mas.model.entity.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "guarantor_details_borrower_id_details_vec", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class GuarantorDetailsBorrowerIDDetailsVec extends BaseID {
	private String lastReportedDate;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
	@JoinColumn(name = "guarantor_details_borrower_id_details_vec_id")
	private List<GuarantorIdDetails> guarantorIdDetailsList;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
	@JoinColumn(name = "guarantor_details_borrower_id_details_vec_id")
	private List<OtherIdData> otherIdDataList;

}