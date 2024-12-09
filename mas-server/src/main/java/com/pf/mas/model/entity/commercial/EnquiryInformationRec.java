package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "enquiry_information_rec", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class EnquiryInformationRec extends BaseID {

	private String borrowerName;
	private String dateOfRegistration;
	private String pan;
	private String cin;
	private String tin;
	private String crn;
	private Long addressCount;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
	@JoinColumn(name = "enquiry_information_rec_id")
	private List<EnquiryInfoAddress> enquiryInfoAddressList;
}