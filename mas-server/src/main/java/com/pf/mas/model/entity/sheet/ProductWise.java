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
@Table(name = "product_wise", schema = Constants.GST_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ProductWise extends BaseSheetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_hsn")
    private String productHSN;

    @Column(name = "hsn_name")
    private String hsnName;

    @OneToMany(mappedBy = "productWise")
    private List<ProductWiseValue> values;

    public static void setValueForFieldHeader(String fieldHeader, ProductWise productWise, String value) {
        switch (fieldHeader.trim().toUpperCase(Locale.ROOT)) {
            case "PRODUCT (HSN)" -> productWise.setProductHSN(value);
            case "HSN'S NAME" -> productWise.setHsnName(value);
            default -> {/* no field found */}
        }
    }
}
