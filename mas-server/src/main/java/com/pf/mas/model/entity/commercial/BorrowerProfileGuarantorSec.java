package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "borrower_profile_guarantor_sec", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class BorrowerProfileGuarantorSec extends BaseID {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "borrower_details_id")
    private BorrowerDetails borrowerDetails;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "credit_facility_details_as_guarantor_rec_id")
    private List<BorrowerAddressContactDetails> borrowerAddressContactDetailsList;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "borrower_id_details_vec_id")
    private BorrowerIdDetailsVec borrowerIdDetailsVec;
    
}
