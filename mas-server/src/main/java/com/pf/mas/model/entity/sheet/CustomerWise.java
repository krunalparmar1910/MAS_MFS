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
@Table(name = "customer_wise", schema = Constants.GST_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CustomerWise extends BaseSheetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_gstin")
    private String customerGSTIN;

    @OneToMany(mappedBy = "customerWise")
    private List<CustomerWiseValue> values;

    public static void setValueForFieldHeader(String fieldHeader, CustomerWise customerWise, String value) {
        switch (fieldHeader.trim().toUpperCase(Locale.ROOT)) {
            case "CUSTOMER'S NAME" -> customerWise.setCustomerName(value);
            case "CUSTOMER'S GSTIN" -> customerWise.setCustomerGSTIN(value);
            default -> {/* no field found */}
        }
    }
}
