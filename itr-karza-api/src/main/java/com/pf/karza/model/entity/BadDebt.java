package com.pf.karza.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "bad_debt_details_bad_debt", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BadDebt extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "bad_debt_details_id")
    @JsonBackReference
    private BadDebtDetails badDebtDetails;

    @Column(name = "total_bad_debt")
    private String totalBadDebt;

    @Column(name = "others_where_pan_not_available")
    private String othersWherePANNotAvlble;

    @Column(name = "others_amt_lt_1_lakh")
    private String othersAmtLt1Lakh;
}