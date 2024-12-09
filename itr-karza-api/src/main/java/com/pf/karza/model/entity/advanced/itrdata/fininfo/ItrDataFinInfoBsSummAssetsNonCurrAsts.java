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
@Table(name = "itr_data_fin_info_bs_summ_assets_non_curr_asts", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataFinInfoBsSummAssetsNonCurrAsts extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_fin_info_bs_summ_assets_id")
    @JsonBackReference
    private ItrDataFinInfoBsSummAssets itrDataFinInfoBsSummAssets;

    private BigDecimal propertyPlantAndEquipment;

    private BigDecimal capitalWorkInProgress;

    private BigDecimal investmentProperty;

    private BigDecimal goodwill;

    private BigDecimal otherIntangibleAssets;

    private BigDecimal intangibleAssetsUnderDevelopment;

    private BigDecimal biologicalAsts;

    private BigDecimal invstEqtyMthd;

    @JsonProperty("nonCurrFinAsts")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataFinInfoBsSummAssetsNonCurrAsts", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataFinInfoBsSummAssetsNonCurrFinAsts itrDataFinInfoBsSummAssetsNonCurrFinAsts;

    private BigDecimal deferredTaxAssetsNet;

    private BigDecimal otherNonCurrentAssets;

    private BigDecimal totalNonCurrentAssets;
}
