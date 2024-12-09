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
@Table(name = "itr_data_fin_info_bs_summ_assets", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataFinInfoBsSummAssets extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_fin_info_bs_summ_id")
    @JsonBackReference
    private ItrDataFinInfoBsSumm itrDataFinInfoBsSumm;

    @JsonProperty("nonCurrAsts")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataFinInfoBsSummAssets", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataFinInfoBsSummAssetsNonCurrAsts itrDataFinInfoBsSummAssetsNonCurrAsts;

    @JsonProperty("currAsts")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataFinInfoBsSummAssets", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataFinInfoBsSummAssetsCurrAsts itrDataFinInfoBsSummAssetsCurrAsts;

    private BigDecimal nonCurrentAssetsClassifiedAsHeldForSale;

    private BigDecimal regDefActDbtBalRelDefTxAst;

    private BigDecimal totalAssets;
}