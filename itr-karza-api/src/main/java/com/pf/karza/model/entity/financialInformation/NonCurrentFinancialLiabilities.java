package com.pf.karza.model.entity.financialInformation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
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
@Table(name = "fi_bs_dt_equity_and_l_l_ncl_noncurr_financial_liabilities", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NonCurrentFinancialLiabilities extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "non_current_liabilities_id")
    private NonCurrentLiabilities nonCurrentLiabilities;

    @Column(name = "trade_payables_non_current")
    private String tradePayablesNonCurrent;

    @Column(name = "other_non_current_financial_liabilities")
    private String otherNonCurrentFinancialLiabilities;

    @Column(name = "borrowings_non_current")
    private String borrowingsNonCurrent;

    @JsonManagedReference
    @OneToOne(mappedBy = "nonCurrentFinancialLiabilities", cascade = CascadeType.ALL, orphanRemoval = true)
    private SecLn secLn;

    @JsonManagedReference
    @OneToOne(mappedBy = "nonCurrentFinancialLiabilities", cascade = CascadeType.ALL, orphanRemoval = true)
    private UnsecLn unsecLn;

    @Column(name = "total_non_current_financial_liabilities")
    private String totalNonCurrentFinancialLiabilities;
}
