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
@Table(name = "fi_qd_manufacturing_concern_finishr_by_prod", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinishrByProd extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "manufacturing_concern_id")
    private ManufacturingConcern manufacturingConcern;

    @Column(name = "sale_qty")
    private Double saleQty;

    @Column(name = "unit_of_measure")
    private Double unitOfMeasure;

    @Column(name = "purchase_qty")
    private Double purchaseQty;

    @Column(name = "any_short_exces")
    private Double anyShortExces;

    @Column(name = "opening_stock")
    private Double openingStock;

    @Column(name = "prevyr_manfact")
    private Double prevyrManfact;

    @Column(name = "clg_stock")
    private Double clgStock;

    @Column(name = "item_name")
    private String itemName;
}
