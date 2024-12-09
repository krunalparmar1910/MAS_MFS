package com.pf.karza.model.entity.advanced.itrdata.fininfo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "itr_data_fin_info_bs_summ_non_curr_fin_liab", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataFinInfoBsSummNonCurrFinLiab extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_fin_info_bs_summ_non_curr_liab_id")
    @JsonBackReference
    private ItrDataFinInfoBsSummNonCurrLiab itrDataFinInfoBsSummNonCurrLiab;

    @JsonProperty("secLn")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataFinInfoBsSummNonCurrFinLiab", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataFinInfoBsSummNonCurrFinLiabSecLn itrDataFinInfoBsSummNonCurrFinLiabSecLn;

    @JsonProperty("unsecLn")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataFinInfoBsSummNonCurrFinLiab", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataFinInfoBsSummNonCurrFinLiabUnsecLn itrDataFinInfoBsSummNonCurrFinLiabUnsecLn;

    private BigDecimal borrowingsNonCurrent;

    private BigDecimal tradePayablesNonCurrent;

    private BigDecimal otherNonCurrentFinancialLiabilities;

    private BigDecimal totalNonCurrentFinancialLiabilities;
}
