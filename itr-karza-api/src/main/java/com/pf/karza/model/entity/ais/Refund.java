package com.pf.karza.model.entity.ais;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
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
@Table(name = "ais_data_details_demand_refund_refund", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Refund extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "ais_demand_refund_id")
    @JsonBackReference
    private DemandRefund demandRefund;

    @Column(name = "amount")
    private String amount;

    @Column(name = "assessment_year")
    private String assessmentYear;

    @Column(name = "date_of_payment")
    private String dateOfPayment;

    @Column(name = "mode")
    private String mode;

    @Column(name = "nature_of_refund")
    private String natureOfRefund;



}