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
@Table(name = "financial_information_balance_sheet_dt_equity_and_liabilities", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquityAndLiabilities extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "balance_sheet_dt_id")
    private BalanceSheetDt balanceSheetDt;

    @JsonManagedReference
    @OneToOne(mappedBy = "equityAndLiabilities", cascade = CascadeType.ALL, orphanRemoval = true)
    private Liabilities liabilities;

    @Column(name = "total_equity_and_liabilities")
    private String totalEquityAndLiabilities;

    @JsonManagedReference
    @OneToOne(mappedBy = "equityAndLiabilities", cascade = CascadeType.ALL, orphanRemoval = true)
    private Equity equity;
}
