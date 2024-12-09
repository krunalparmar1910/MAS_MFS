package com.pf.karza.model.entity.ais.SftInfo.cpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.ais.SftInfo.Sft018Pur;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ais_data_details_sft_info_sft_018Pur_cpty", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cpty018Pur extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "sft018Pur_id")
    private Sft018Pur sft018Pur;

    @Column(name = "tsn_id")
    private String tsnId;

    @Column(name = "reported_on")
    private String reportedOn;

    @Column(name = "status")
    private String status;

    @Column(name = "trans_feedback")
    private String transFeedback;

    @Column(name = "amount_processed")
    private String amountProcessed;

    @Column(name = "amount_derived")
    private String amountDerived;

    @Column(name = "filer_id")
    private String filerId;

    @Column(name = "filer_name")
    private String filerName;

    @Column(name = "qualifies_for")
    private String qualifiesFor;

    @Column(name = "amc_name_code")
    private String amcNameCode;

    @Column(name = "number_of_holders")
    private String numberOfHolders;

    @Column(name = "holder_flag")
    private String holderFlag;

    @Column(name = "opening_value")
    private String openingValue;

    @Column(name = "total_credits")
    private String totalCredits;

    @Column(name = "total_debits")
    private String totalDebits;

    @Column(name = "total_purchase_amount")
    private String totalPurchaseAmount;

    @Column(name = "value_holding_period_end")
    private String valueHoldingPeriodEnd;

    @Column(name = "market_purchase")
    private String marketPurchase;

    @Column(name = "purchase_through_ipo")
    private String purchaseThroughIpo;

    @Column(name = "corporate_action_credits")
    private String corporateActionCredits;

    @Column(name = "dividend_amount")
    private String dividendAmount;

    @Column(name = "sales")
    private String sales;

    @Column(name = "total_sales_value")
    private String totalSalesValue;

    @Column(name = "client_id")
    private String clientID;
}
