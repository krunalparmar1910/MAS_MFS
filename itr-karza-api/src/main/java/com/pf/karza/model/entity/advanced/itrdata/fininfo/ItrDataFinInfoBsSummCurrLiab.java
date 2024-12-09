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
@Table(name = "itr_data_fin_info_bs_summ_curr_liab", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataFinInfoBsSummCurrLiab extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_fin_info_bs_summ_liab_id")
    @JsonBackReference
    private ItrDataFinInfoBsSummLiab itrDataFinInfoBsSummLiab;

    @JsonProperty("currFinLiab")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataFinInfoBsSummCurrLiab", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataFinInfoBsSummCurrFinLiab itrDataFinInfoBsSummCurrFinLiab;

    private BigDecimal otherCurrentLiabilities;

    private BigDecimal provisionsCurrent;

    private BigDecimal currentTaxLiabilities;

    private BigDecimal defGovtGrntCurr;

    private BigDecimal totalCurrentLiabilities;
}