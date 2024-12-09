package com.pf.karza.model.entity.financialInformation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "financial_information_quantitative_details_trading_concern", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradingConcern extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "quantitative_details_id")
    private QuantitativeDetails quantitativeDetails;

    @Column(name = "sale_qty")
    private Double saleQty;

    @Column(name = "unit_of_measure")
    private Double unitOfMeasure;

    @Column(name = "purchase_qty")
    private Double purchaseQty;

    @Column(name = "percent_yld")
    private Double percentYld;

    @Column(name = "yld_finis_prod")
    private Double yldFinisProd;

    @Column(name = "opening_stock")
    private Double openingStock;

    @Column(name = "prevyr_manfact")
    private Double prevyrManfact;

    @Column(name = "clg_stock")
    private Double clgStock;

    @Column(name = "any_short_exces")
    private Double anyShortExces;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "prev_yr_consum")
    private Double prevYrConsum;

}
