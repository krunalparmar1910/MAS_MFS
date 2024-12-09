package com.pf.mas.service.report.sheet.carrier;

import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.FieldDateMonthYear;
import com.pf.mas.model.entity.FieldGroup;
import com.pf.mas.model.entity.SheetTypeName;
import com.pf.mas.model.entity.sheet.CustomerSupplierAnalysisTotal;
import com.pf.mas.model.entity.sheet.CustomersAnalysis;
import com.pf.mas.model.entity.sheet.DetailsOfCustomersAndSuppliers;
import com.pf.mas.model.entity.sheet.HSNChapterAnalysis;
import com.pf.mas.model.entity.sheet.SuppliersAnalysis;
import lombok.AccessLevel;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class CustomerSupplierAnalysisRecordCarrier {
    @Getter(AccessLevel.NONE)
    private final SheetTypeName sheetTypeName;
    private final List<CustomerSupplierAnalysisTotal> customerSupplierAnalysisTotalList;
    private final List<CustomersAnalysis> customersAnalysisList;
    private final List<SuppliersAnalysis> suppliersAnalysisList;
    private final List<HSNChapterAnalysis> hsnChapterAnalysisList;
    private final List<DetailsOfCustomersAndSuppliers> detailsOfCustAndSuppList;

    public CustomerSupplierAnalysisRecordCarrier(SheetTypeName sheetTypeName) {
        this.sheetTypeName = sheetTypeName;
        customerSupplierAnalysisTotalList = new ArrayList<>();
        customersAnalysisList = SheetTypeName.CUSTOMERS_ANALYSIS == sheetTypeName ? new ArrayList<>() : Collections.emptyList();
        suppliersAnalysisList = SheetTypeName.SUPPLIERS_ANALYSIS == sheetTypeName ? new ArrayList<>() : Collections.emptyList();
        hsnChapterAnalysisList = SheetTypeName.HSN_CHAPTER_ANALYSIS == sheetTypeName ? new ArrayList<>() : Collections.emptyList();
        detailsOfCustAndSuppList = SheetTypeName.DETAILS_OF_CUSTOMERS_AND_SUPP == sheetTypeName ? new ArrayList<>() : Collections.emptyList();
    }

    public void newRecord(FieldGroup fieldGroup, FieldDateMonthYear fieldDateMonthYear, ClientOrder clientOrder) {
        if (SheetTypeName.CUSTOMERS_ANALYSIS == sheetTypeName) {
            CustomersAnalysis customersAnalysis = new CustomersAnalysis();
            RecordCarrierUtils.setBaseFields(customersAnalysis, fieldGroup, fieldDateMonthYear, clientOrder);
            customersAnalysisList.add(customersAnalysis);
        } else if (SheetTypeName.SUPPLIERS_ANALYSIS == sheetTypeName) {
            SuppliersAnalysis suppliersAnalysis = new SuppliersAnalysis();
            RecordCarrierUtils.setBaseFields(suppliersAnalysis, fieldGroup, fieldDateMonthYear, clientOrder);
            suppliersAnalysisList.add(suppliersAnalysis);
        } else if (SheetTypeName.HSN_CHAPTER_ANALYSIS == sheetTypeName) {
            HSNChapterAnalysis hsnChapterAnalysis = new HSNChapterAnalysis();
            RecordCarrierUtils.setBaseFields(hsnChapterAnalysis, fieldGroup, fieldDateMonthYear, clientOrder);
            hsnChapterAnalysisList.add(hsnChapterAnalysis);
        } else if (SheetTypeName.DETAILS_OF_CUSTOMERS_AND_SUPP == sheetTypeName) {
            DetailsOfCustomersAndSuppliers details = new DetailsOfCustomersAndSuppliers();
            RecordCarrierUtils.setBaseFields(details, fieldGroup, fieldDateMonthYear, clientOrder);
            detailsOfCustAndSuppList.add(details);
        }
    }

    public void newTotalRecord(String fieldName, FieldGroup fieldGroup, FieldDateMonthYear fieldDateMonthYear, ClientOrder clientOrder) {
        CustomerSupplierAnalysisTotal customersAnalysisTotal = new CustomerSupplierAnalysisTotal();
        RecordCarrierUtils.setBaseFields(customersAnalysisTotal, fieldGroup, fieldDateMonthYear, clientOrder);
        customersAnalysisTotal.setFieldName(fieldName);
        customerSupplierAnalysisTotalList.add(customersAnalysisTotal);
    }

    public void setFieldValue(String header, String value, BigDecimal numericValue) {
        if (SheetTypeName.CUSTOMERS_ANALYSIS == sheetTypeName) {
            CustomersAnalysis.setValueForFieldHeader(header, RecordCarrierUtils.getLastElement(customersAnalysisList), value, numericValue);
        } else if (SheetTypeName.SUPPLIERS_ANALYSIS == sheetTypeName) {
            SuppliersAnalysis.setValueForFieldHeader(header, RecordCarrierUtils.getLastElement(suppliersAnalysisList), value, numericValue);
        } else if (SheetTypeName.HSN_CHAPTER_ANALYSIS == sheetTypeName) {
            HSNChapterAnalysis.setValueForFieldHeader(header, RecordCarrierUtils.getLastElement(hsnChapterAnalysisList), value, numericValue);
        } else if (SheetTypeName.DETAILS_OF_CUSTOMERS_AND_SUPP == sheetTypeName) {
            DetailsOfCustomersAndSuppliers.setValueForFieldHeader(header, RecordCarrierUtils.getLastElement(detailsOfCustAndSuppList), value);
        }
    }

    public void setTotalFieldValue(String header, String value, BigDecimal numericValue) {
        CustomerSupplierAnalysisTotal.setValueForFieldHeader(header, RecordCarrierUtils.getLastElement(customerSupplierAnalysisTotalList), value, numericValue);
    }
}
