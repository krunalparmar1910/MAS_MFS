package com.pf.karza.model.entity.financialInformation;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "fi_bs_dt_equity_and_l_l_cl_current_financial_liabilities", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentFinancialLiabilities extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "current_liabilities_id")
    private CurrentLiabilities currentLiabilities;

    @Column(name = "borrowings_current")
    private String borrowingsCurrent;

    @Column(name = "total_current_financial_liabilities")
    private String totalCurrentFinancialLiabilities;

    @Column(name = "trade_payables_current")
    private String tradePayablesCurrent;

    @Column(name = "other_current_financial_liabilities")
    private String otherCurrentFinancialLiabilities;
}
