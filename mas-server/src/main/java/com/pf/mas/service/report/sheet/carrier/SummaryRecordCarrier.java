package com.pf.mas.service.report.sheet.carrier;

import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.FieldDateMonthYear;
import com.pf.mas.model.entity.FieldGroup;
import com.pf.mas.model.entity.SheetTypeName;
import com.pf.mas.model.entity.sheet.GSTR3B;
import com.pf.mas.model.entity.sheet.GSTR3BValue;
import com.pf.mas.model.entity.sheet.Summary;
import com.pf.mas.model.entity.sheet.SummaryAnalysis;
import com.pf.mas.model.entity.sheet.SummaryAnalysisValue;
import com.pf.mas.model.entity.sheet.SummaryValue;
import com.pf.mas.model.entity.sheet.Tax;
import com.pf.mas.model.entity.sheet.TaxValue;
import com.pf.mas.model.entity.sheet.YearlyTax;
import com.pf.mas.model.entity.sheet.YearlyTaxValue;
import lombok.AccessLevel;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
public class SummaryRecordCarrier {
    @Getter(AccessLevel.NONE)
    private final SheetTypeName sheetTypeName;
    private List<SummaryAnalysis> summaryAnalysisList;
    private List<SummaryAnalysisValue> summaryAnalysisValueList;
    private List<Summary> summaryList;
    private List<SummaryValue> summaryValueList;
    private List<GSTR3B> gstr3BList;
    private List<GSTR3BValue> gstr3BValueList;
    private List<Tax> taxList;
    private List<TaxValue> taxValueList;
    private List<YearlyTax> yearlyTaxList;
    private List<YearlyTaxValue> yearlyTaxValueList;

    public SummaryRecordCarrier(SheetTypeName sheetTypeName) {
        this.sheetTypeName = sheetTypeName;
        if (SheetTypeName.SUMMARY_ANALYSIS == sheetTypeName) {
            summaryAnalysisList = new ArrayList<>();
            summaryAnalysisValueList = new ArrayList<>();
        } else if (SheetTypeName.SUMMARY == sheetTypeName) {
            summaryList = new ArrayList<>();
            summaryValueList = new ArrayList<>();
        } else if (SheetTypeName.GSTR_3B == sheetTypeName) {
            gstr3BList = new ArrayList<>();
            gstr3BValueList = new ArrayList<>();
        } else if (SheetTypeName.TAX == sheetTypeName) {
            taxList = new ArrayList<>();
            taxValueList = new ArrayList<>();
        } else if (SheetTypeName.YEARLY_TAX == sheetTypeName) {
            yearlyTaxList = new ArrayList<>();
            yearlyTaxValueList = new ArrayList<>();
        }
    }

    public void newRecord(FieldGroup fieldGroup, FieldDateMonthYear fieldDateMonthYear, ClientOrder clientOrder, String cellValue) {
        if (SheetTypeName.SUMMARY_ANALYSIS == sheetTypeName) {
            SummaryAnalysis summaryAnalysis = new SummaryAnalysis();
            RecordCarrierUtils.setBaseFields(summaryAnalysis, fieldGroup, fieldDateMonthYear, clientOrder);
            summaryAnalysis.setParticulars(cellValue);
            summaryAnalysisList.add(summaryAnalysis);
        } else if (SheetTypeName.SUMMARY == sheetTypeName) {
            Summary summary = new Summary();
            RecordCarrierUtils.setBaseFields(summary, fieldGroup, fieldDateMonthYear, clientOrder);
            summary.setParticulars(cellValue);
            summaryList.add(summary);
        } else if (SheetTypeName.GSTR_3B == sheetTypeName) {
            GSTR3B gstr3B = new GSTR3B();
            RecordCarrierUtils.setBaseFields(gstr3B, fieldGroup, fieldDateMonthYear, clientOrder);
            gstr3B.setParticulars(cellValue);
            gstr3BList.add(gstr3B);
        } else if (SheetTypeName.TAX == sheetTypeName) {
            Tax tax = new Tax();
            RecordCarrierUtils.setBaseFields(tax, fieldGroup, fieldDateMonthYear, clientOrder);
            tax.setParticulars(cellValue);
            taxList.add(tax);
        } else if (SheetTypeName.YEARLY_TAX == sheetTypeName) {
            YearlyTax yearlyTax = new YearlyTax();
            RecordCarrierUtils.setBaseFields(yearlyTax, fieldGroup, fieldDateMonthYear, clientOrder);
            yearlyTax.setParticulars(cellValue);
            yearlyTaxList.add(yearlyTax);
        }
    }

    public void newValueRecord(FieldDateMonthYear fieldDateMonthYear, String value, BigDecimal numericValue) {
        newValueRecord(fieldDateMonthYear, value, numericValue, null);
    }

    public void newValueRecord(FieldDateMonthYear fieldDateMonthYear, String value, BigDecimal numericValue, String quarter) {
        if (SheetTypeName.SUMMARY_ANALYSIS == sheetTypeName) {
            SummaryAnalysisValue summaryAnalysisValue = new SummaryAnalysisValue();
            RecordCarrierUtils.setBaseValueFields(summaryAnalysisValue, fieldDateMonthYear, value, numericValue);
            summaryAnalysisValue.setSummaryAnalysis(RecordCarrierUtils.getLastElement(summaryAnalysisList));
            summaryAnalysisValueList.add(summaryAnalysisValue);
        } else if (SheetTypeName.SUMMARY == sheetTypeName) {
            SummaryValue summaryValue = new SummaryValue();
            RecordCarrierUtils.setBaseValueFields(summaryValue, fieldDateMonthYear, value, numericValue);
            summaryValue.setSummary(RecordCarrierUtils.getLastElement(summaryList));
            summaryValue.setQuarter(quarter);
            summaryValueList.add(summaryValue);
        } else if (SheetTypeName.GSTR_3B == sheetTypeName) {
            GSTR3BValue gstr3BValue = new GSTR3BValue();
            RecordCarrierUtils.setBaseValueFields(gstr3BValue, fieldDateMonthYear, value, numericValue);
            gstr3BValue.setGstr3b(RecordCarrierUtils.getLastElement(gstr3BList));
            gstr3BValueList.add(gstr3BValue);
        } else if (SheetTypeName.TAX == sheetTypeName) {
            TaxValue taxValue = new TaxValue();
            RecordCarrierUtils.setBaseValueFields(taxValue, fieldDateMonthYear, value, numericValue);
            taxValue.setTax(RecordCarrierUtils.getLastElement(taxList));
            taxValueList.add(taxValue);
        } else if (SheetTypeName.YEARLY_TAX == sheetTypeName) {
            YearlyTaxValue yearlyTaxValue = new YearlyTaxValue();
            RecordCarrierUtils.setBaseValueFields(yearlyTaxValue, fieldDateMonthYear, value, numericValue);
            yearlyTaxValue.setYearlyTax(RecordCarrierUtils.getLastElement(yearlyTaxList));
            yearlyTaxValueList.add(yearlyTaxValue);
        }
    }
}
