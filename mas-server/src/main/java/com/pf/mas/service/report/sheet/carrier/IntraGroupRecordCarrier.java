package com.pf.mas.service.report.sheet.carrier;

import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.FieldDateMonthYear;
import com.pf.mas.model.entity.FieldGroup;
import com.pf.mas.model.entity.sheet.IntraGroup;
import com.pf.mas.model.entity.sheet.IntraGroupPurchasesAndExpensesValue;
import com.pf.mas.model.entity.sheet.IntraGroupRevenueValue;
import com.pf.mas.model.entity.sheet.IntraGroupSummaryOfGroupTransaction;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class IntraGroupRecordCarrier {
    private static final String REVENUE = "REVENUE";
    private static final String PURCHASE = "PURCHASE";
    private static final String GROUP_TRANSACTIONS = "GROUP TRANSACTIONS";
    private final Map<String, IntraGroup> gstMap;
    private final List<IntraGroup> intraGroupList;
    private final List<IntraGroupRevenueValue> intraGroupRevenueValueList;
    private final List<IntraGroupPurchasesAndExpensesValue> intraGroupPurchasesAndExpensesValueList;
    private final List<IntraGroupSummaryOfGroupTransaction> intraGroupSummaryOfGroupTransactionList;

    public IntraGroupRecordCarrier() {
        gstMap = new HashMap<>();
        intraGroupList = new ArrayList<>();
        intraGroupRevenueValueList = new ArrayList<>();
        intraGroupPurchasesAndExpensesValueList = new ArrayList<>();
        intraGroupSummaryOfGroupTransactionList = new ArrayList<>();
    }

    public IntraGroup newIntraGroupRecord(FieldGroup fieldGroup, FieldDateMonthYear fieldDateMonthYear, ClientOrder clientOrder, String gstin) {
        return gstMap.computeIfAbsent(gstin, x -> {
            IntraGroup intraGroup = new IntraGroup();
            RecordCarrierUtils.setBaseFields(intraGroup, fieldGroup, fieldDateMonthYear, clientOrder);
            intraGroup.setGstin(gstin);
            intraGroupList.add(intraGroup);
            return intraGroup;
        });
    }

    public void newIntraGroupValueRecord(FieldGroup fieldGroup, FieldDateMonthYear fieldDateMonthYear, IntraGroup intraGroup) {
        if (StringUtils.containsIgnoreCase(fieldGroup.getFieldGroupName(), REVENUE)) {
            IntraGroupRevenueValue intraGroupRevenueValue = new IntraGroupRevenueValue();
            intraGroupRevenueValue.setFieldDateMonthYear(fieldDateMonthYear);
            intraGroupRevenueValue.setIntraGroup(intraGroup);
            intraGroupRevenueValueList.add(intraGroupRevenueValue);
        } else if (StringUtils.containsIgnoreCase(fieldGroup.getFieldGroupName(), PURCHASE)) {
            IntraGroupPurchasesAndExpensesValue intraGroupPurchasesAndExpensesValue = new IntraGroupPurchasesAndExpensesValue();
            intraGroupPurchasesAndExpensesValue.setFieldDateMonthYear(fieldDateMonthYear);
            intraGroupPurchasesAndExpensesValue.setIntraGroup(intraGroup);
            intraGroupPurchasesAndExpensesValueList.add(intraGroupPurchasesAndExpensesValue);
        }
    }

    public void newIntraGroupValueSummaryRecord(FieldGroup fieldGroup, IntraGroup intraGroup, List<FieldDateMonthYear> fieldDateMonthYearList) {
        if (StringUtils.containsIgnoreCase(fieldGroup.getFieldGroupName(), GROUP_TRANSACTIONS)) {
            IntraGroupSummaryOfGroupTransaction intraGroupSummaryOfGroupTransaction = new IntraGroupSummaryOfGroupTransaction();
            FieldDateMonthYear fromGroupName = fieldDateMonthYearList.stream()
                    .filter(fieldDateMonthYear -> fieldGroup.getFieldGroupName().contains(fieldDateMonthYear.getFieldDateValue())).findFirst().orElse(null);
            intraGroupSummaryOfGroupTransaction.setFieldDateMonthYear(fromGroupName);
            intraGroupSummaryOfGroupTransaction.setIntraGroup(intraGroup);
            intraGroupSummaryOfGroupTransactionList.add(intraGroupSummaryOfGroupTransaction);
        }
    }

    public void setIntraGroupValueFieldValue(FieldGroup fieldGroup, String header, String value, BigDecimal numericValue) {
        if (StringUtils.containsIgnoreCase(fieldGroup.getFieldGroupName(), REVENUE)) {
            IntraGroupRevenueValue.setValueForFieldHeader(header, RecordCarrierUtils.getLastElement(intraGroupRevenueValueList), value, numericValue);
        } else if (StringUtils.containsIgnoreCase(fieldGroup.getFieldGroupName(), PURCHASE)) {
            IntraGroupPurchasesAndExpensesValue.setValueForFieldHeader(header, RecordCarrierUtils.getLastElement(intraGroupPurchasesAndExpensesValueList), value, numericValue);
        }
    }

    public void setIntraGroupValueFieldSummaryValue(String againstGSTIN, String revenue, BigDecimal numericValue) {
        IntraGroupSummaryOfGroupTransaction intraGroupSummaryOfGroupTransaction = RecordCarrierUtils.getLastElement(intraGroupSummaryOfGroupTransactionList);
        intraGroupSummaryOfGroupTransaction.setAgainstGSTIN(againstGSTIN);
        intraGroupSummaryOfGroupTransaction.setRevenue(revenue);
        intraGroupSummaryOfGroupTransaction.setRevenueNumeric(numericValue);
    }
}
