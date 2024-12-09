package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.dto.cibil.commercial.BorrowerDelinquencyReportedOnRSOrGSOftheBorrowerIn24MonthsDTO;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "bdr_rs_gs_of_borrower_in_24_months_vec", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class BorrowerDelinquencyReportedOnRSOrGSOfBorrowerIn24MonthsVec extends BaseID {
	private String message;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "bdr_rs_gs_of_borrower_in_24_months_vec_id")
	private List<BorrowerDelinquencyReportedOnRSOrGSOftheBorrowerIn24Months> borrowerDelinquencyReportedOnRSOrGSOftheBorrowerIn24Months;

}