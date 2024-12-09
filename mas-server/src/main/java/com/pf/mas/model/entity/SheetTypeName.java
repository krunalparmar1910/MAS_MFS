package com.pf.mas.model.entity;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum SheetTypeName {
    // these are in order of appearance in a report
    DISCLAIMER("Disclaimer"),
    INDEX("Index"),
    INTRA_GROUP("Intra Group"),
    SUMMARY_ANALYSIS("Summary Analysis"),
    TREND_CHARTS("Trend Charts"),
    SEASONALITY_ANALYSIS("Seasonality Analysis"),
    CUSTOMERS_ANALYSIS("Customers Analysis"),
    SUPPLIERS_ANALYSIS("Suppliers Analysis"),
    HSN_CHAPTER_ANALYSIS("HSN & Chapter Analysis"),
    CIRCULAR_TRANSACTIONS("Circular Transactions"),
    PROFILE_FILING_TABLE("Profile & Filing Table"),
    SUMMARY("Summary"),
    BIFURCATION("Bifurcation"),
    ADJUSTED_AMOUNTS("Adjusted Amounts"),
    STATE_WISE("State Wise"),
    CUSTOMER_WISE("Customer Wise"),
    SUPPLIER_WISE("Supplier Wise"),
    PRODUCT_WISE("Product Wise"),
    GSTR_3B("GSTR 3B"),
    DETAILS_OF_CUSTOMERS_AND_SUPP("Details of Customers and Supp."),
    TAX("Tax"),
    YEARLY_RETURN_SUMMARY("Yearly Return Summary"),
    YEARLY_TAX("Yearly Tax");

    private static final Map<String, SheetTypeName> STRING_TYPES_MAP = Arrays.stream(SheetTypeName.values())
            .collect(Collectors.toMap(SheetTypeName::getName, Function.identity()));

    private final String name;

    SheetTypeName(String name) {
        this.name = name;
    }

    public static SheetTypeName getFromSheetName(String sheetName) {
        try {
            return STRING_TYPES_MAP.get(sheetName);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public String getName() {
        return name;
    }
}
