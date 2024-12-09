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
@Table(name = "fi_qd_manufacturing_concern_raw_material", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawMaterial extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "manufacturing_concern_id")
    private ManufacturingConcern manufacturingConcern;

    @Column(name = "sale_qty")
    private Double saleQty;

    @Column(name = "percent_yld")
    private Double percentYld;

    @Column(name = "purchase_qty")
    private Double purchaseQty;

    @Column(name = "unit_of_measure")
    private Double unitOfMeasure;

    @Column(name = "yld_finis_prod")
    private Double yldFinisProd;

    @Column(name = "opening_stock")
    private Double openingStock;

    @Column(name = "clg_stock")
    private Double clgStock;

    @Column(name = "any_short_exces")
    private Double anyShortExces;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "prev_yr_consum")
    private Double prevYrConsum;

}
