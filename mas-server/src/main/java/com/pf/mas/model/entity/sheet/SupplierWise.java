package com.pf.mas.model.entity.sheet;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseSheetEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "supplier_wise", schema = Constants.GST_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class SupplierWise extends BaseSheetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "supplier_gstin")
    private String supplierGSTIN;

    @OneToMany(mappedBy = "supplierWise")
    private List<SupplierWiseValue> values;

    public static void setValueForFieldHeader(String fieldHeader, SupplierWise supplierWise, String value) {
        switch (fieldHeader.trim().toUpperCase(Locale.ROOT)) {
            case "SUPPLIER'S NAME" -> supplierWise.setSupplierName(value);
            case "SUPPLIER'S GSTIN" -> supplierWise.setSupplierGSTIN(value);
            default -> {/* no field found */}
        }
    }
}
