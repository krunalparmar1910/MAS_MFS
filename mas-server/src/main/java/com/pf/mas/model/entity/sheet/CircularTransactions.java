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
@Table(name = "circular_transactions", schema = Constants.GST_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CircularTransactions extends BaseSheetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vendor_name")
    private String vendorName;

    @Column(name = "vendor_gstn")
    private String vendorGSTN;

    @Column(name = "vendor_state")
    private String vendorState;

    @Column(name = "adjusted_revenue_or_purchases_expenses")
    private String adjustedRevenueOrPurchasesExpenses;

    @OneToMany(mappedBy = "circularTransactions")
    private List<CircularTransactionsValue> values;

    public static void setValueForFieldHeader(String fieldHeader, CircularTransactions circularTransactions, String value) {
        switch (fieldHeader.trim().toUpperCase(Locale.ROOT)) {
            case "VENDOR NAME" -> circularTransactions.setVendorName(value);
            case "VENDOR GSTN" -> circularTransactions.setVendorGSTN(value);
            case "VENDOR STATE" -> circularTransactions.setVendorState(value);
            case "ADJUSTED REVENUE/\nADJUSTED PURCHASE AND EXPENSES" -> circularTransactions.setAdjustedRevenueOrPurchasesExpenses(value);
            default -> {/* no field found */}
        }
    }
}
