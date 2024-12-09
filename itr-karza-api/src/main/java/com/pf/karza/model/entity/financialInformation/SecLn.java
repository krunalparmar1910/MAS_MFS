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
@Table(name = "fi_bs_dt_equity_and_l_l_ncl_ncfl_sec_ln", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecLn extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "non_current_financial_liabilities_id")
    private NonCurrentFinancialLiabilities nonCurrentFinancialLiabilities;

    @Column(name = "ttl_sec_ln")
    private String ttlSecLn;

    @Column(name = "rpl_from_others")
    private String rplFromOthers;

    @Column(name = "frgn_curr_ln")
    private String frgnCurrLn;

    @Column(name = "rpl_from_banks")
    private String rplFromBanks;

    @Column(name = "ttl_rp_ln")
    private String ttlRpLn;
}
