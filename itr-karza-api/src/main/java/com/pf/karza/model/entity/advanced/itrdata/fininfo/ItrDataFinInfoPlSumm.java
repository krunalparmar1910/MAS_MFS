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
@Table(name = "itr_data_fin_info_pl_summ", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataFinInfoPlSumm extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_fin_info_id")
    @JsonBackReference
    private ItrDataFinInfo itrDataFinInfo;

    @JsonProperty("inc")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataFinInfoPlSumm", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataFinInfoPlSummInc itrDataFinInfoPlSummInc;

    @JsonProperty("exp")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataFinInfoPlSumm", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataFinInfoPlSummExp itrDataFinInfoPlSummExp;

    private BigDecimal grossProfit;

    private BigDecimal prftBfrExcptnlItmsAndTx;

    private BigDecimal excptnlItmsAndTx;

    private BigDecimal totalProfitBeforeTax;

    @JsonProperty("taxExpense")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataFinInfoPlSumm", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataFinInfoPlSummTaxExpense itrDataFinInfoPlSummTaxExpense;

    private BigDecimal netMvmntInRegDefActBal;

    private BigDecimal ttlPrftOrLsForPrdFrmCntnungOp;

    private BigDecimal profitOrLossFromDiscontinuedOperationsBeforeTax;

    private BigDecimal txExpOfDiscntndOp;

    private BigDecimal totalProfitOrLossFromDiscontinuedOperationsAfterTax;

    private BigDecimal shrOfPrftLsOfAsctJntVntrActdFrUsngEqMthd;

    private BigDecimal totalProfitOrLossForPeriod;
}
