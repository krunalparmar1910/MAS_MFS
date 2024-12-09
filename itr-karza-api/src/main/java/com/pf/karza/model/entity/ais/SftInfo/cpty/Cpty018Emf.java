package com.pf.karza.model.entity.ais.SftInfo.cpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.ais.SftInfo.Sft018Emf;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ais_data_details_sft_info_sft_018Emf_cpty", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cpty018Emf extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "sft018Emf_id")
    @JsonBackReference
    private Sft018Emf sft018Emf;

    @Column(name = "amc_name_code")
    private String amcNameCode;

    @Column(name = "amount_derived")
    private String amountDerived;

    @Column(name = "amount_processed")
    private String amountProcessed;

    @Column(name = "asset_type")
    private String assetType;

    @Column(name = "cost_of_acquisition")
    private String costOfAcquisition;

    @Column(name = "credit_type")
    private String creditType;

    @Column(name = "debit_type")
    private String debitType;

    @Column(name = "filer_id")
    private String filerId;

    @Column(name = "filer_name")
    private String filerName;

    @Column(name = "fmv_value")
    private String fmvValue;

    @Column(name = "fmv_value_unit")
    private String fmvValueUnit;

    @Column(name = "index_cost_of_acquisition")
    private String indexCostOfAcquisition;

    @Column(name = "qualifies_for")
    private String qualifiesFor;

    @Column(name = "quantity")
    private String quantity;

    @Column(name = "reported_on")
    private String reportedOn;

    @Column(name = "sales_consideration")
    private String salesConsideration;

    @Column(name = "security_class")
    private String securityClass;

    @Column(name = "security_name")
    private String securityName;

    @Column(name = "sell_price_per_unit")
    private String sellPricePerUnit;

    @Column(name = "status")
    private String status;

    @Column(name = "stt")
    private String stt;

    @Column(name = "trans_feedback")
    private String transFeedback;

    @Column(name = "transfer_date")
    private String transferDate;

    @Column(name = "tsn_id")
    private String tsnId;

}
