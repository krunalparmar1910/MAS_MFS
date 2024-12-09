package com.pf.mas.model.entity.commercial;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.dto.cibil.commercial.RankSec;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_sec", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ProductSec extends BaseID {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "borrower_profile_sec_id")
    private BorrowerProfileSec borrowerProfileSec;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "rank_sec_id")
    private RankSec rankSec;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_vision_sec_id")
    private CreditVisionSec creditVisionSec;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_profile_summary_sec_id")
    private CreditProfileSummarySec creditProfileSummarySec;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "enquiry_summary_sec_id")
    private EnquirySummarySec enquirySummarySec;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "derogatory_information_sec_id")
    private DerogatoryInformationSec derogatoryInformationSec;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ob_cf_and_asset_classification_sec_id")
    private ObCfAndAssetClassificationSec obCfAndAssetClassificationSec;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "location_details_sec_id")
    private LocationDetailsSec locationDetailsSec;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "relationship_details_vec_id")
    private RelationshipDetailsVec relationshipDetailsVec;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_facility_details_as_borrower_sec_vec_id")
    private CreditFacilityDetailsAsBorrowerSecVec creditFacilityDetailsasBorrowerSecVec;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_facility_details_as_guarantor_vec_id")
    private CreditFacilityDetailsAsGuarantorVec creditFacilityDetailsasGuarantorVec;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "suit_filed_vec_id")
    private SuitFiledVec suitFiledVec;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_rating_summary_vec_id")
    private CreditRatingSummaryVec creditRatingSummaryVec;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "enquiry_details_vec_id")
    private EnquiryDetailsVec enquiryDetailsVec;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_facilities_details_vec_id")
    private CreditFacilitiesDetailsVec creditFacilitiesDetailsVec;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_facilities_summary_id")
    private CreditFacilitiesSummary creditFacilitiesSummary;
}
