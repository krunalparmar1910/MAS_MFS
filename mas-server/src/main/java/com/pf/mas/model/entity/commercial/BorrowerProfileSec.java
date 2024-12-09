package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "borrower_profile_sec", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class BorrowerProfileSec extends BaseID {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "borrower_details_id")
    private BorrowerDetails borrowerDetails;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "borrower_address_contact_details_id")
    private BorrowerAddressContactDetails borrowerAddressContactDetails;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "borrower_id_details_vec_id")
    private BorrowerIdDetailsVec borrowerIdDetailsVec;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "borrower_delinquency_reported_on_borrower_id")
    private BorrowerDelinquencyReportedOnBorrower borrowerDelinquencyReportedOnBorrower;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "bdr_rs_gs_of_borrower_in_24_months_vec_id")
    private BorrowerDelinquencyReportedOnRSOrGSOfBorrowerIn24MonthsVec borrowerDelinquencyReportedOnRSOrGSOfBorrowerIn24MonthsVecList;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "dispute_remarks_id")
    private DisputeRemarks disputeRemarks;
}
