package com.pf.mas.service.report.sheet.carrier;

import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.FieldDateMonthYear;
import com.pf.mas.model.entity.FieldGroup;
import com.pf.mas.model.entity.SheetTypeName;
import com.pf.mas.model.entity.base.BaseSheetBifurcatedValueEntity;
import com.pf.mas.model.entity.sheet.AdjustedAmounts;
import com.pf.mas.model.entity.sheet.AdjustedAmountsValue;
import com.pf.mas.model.entity.sheet.Bifurcation;
import com.pf.mas.model.entity.sheet.BifurcationValue;
import com.pf.mas.model.entity.sheet.StateWise;
import com.pf.mas.model.entity.sheet.StateWiseValue;
import com.pf.mas.model.entity.sheet.YearlyReturnSummary;
import com.pf.mas.model.entity.sheet.YearlyReturnSummaryValue;
import lombok.AccessLevel;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BifurcationStateAmountRecordCarrier {
    @Getter(AccessLevel.NONE)
    private final SheetTypeName sheetTypeName;
    private List<Bifurcation> bifurcationList;
    private List<BifurcationValue> bifurcationValueList;
    private List<AdjustedAmounts> adjustedAmountsList;
    private List<AdjustedAmountsValue> adjustedAmountsValueList;
    private List<StateWise> stateWiseList;
    private List<StateWiseValue> stateWiseValueList;
    private List<YearlyReturnSummary> yearlyReturnSummaryList;
    private List<YearlyReturnSummaryValue> yearlyReturnSummaryValueList;

    public BifurcationStateAmountRecordCarrier(SheetTypeName sheetTypeName) {
        this.sheetTypeName = sheetTypeName;
        if (SheetTypeName.BIFURCATION == sheetTypeName) {
            bifurcationList = new ArrayList<>();
            bifurcationValueList = new ArrayList<>();
        } else if (SheetTypeName.ADJUSTED_AMOUNTS == sheetTypeName) {
            adjustedAmountsList = new ArrayList<>();
            adjustedAmountsValueList = new ArrayList<>();
        } else if (SheetTypeName.STATE_WISE == sheetTypeName) {
            stateWiseList = new ArrayList<>();
            stateWiseValueList = new ArrayList<>();
        } else if (SheetTypeName.YEARLY_RETURN_SUMMARY == sheetTypeName) {
            yearlyReturnSummaryList = new ArrayList<>();
            yearlyReturnSummaryValueList = new ArrayList<>();
        }
    }

    public void newRecord(FieldGroup fieldGroup, FieldDateMonthYear fieldDateMonthYear, ClientOrder clientOrder) {
        if (SheetTypeName.BIFURCATION == sheetTypeName) {
            Bifurcation bifurcation = new Bifurcation();
            RecordCarrierUtils.setBaseFields(bifurcation, fieldGroup, fieldDateMonthYear, clientOrder);
            bifurcationList.add(bifurcation);
        } else if (SheetTypeName.ADJUSTED_AMOUNTS == sheetTypeName) {
            AdjustedAmounts adjustedAmounts = new AdjustedAmounts();
            RecordCarrierUtils.setBaseFields(adjustedAmounts, fieldGroup, fieldDateMonthYear, clientOrder);
            adjustedAmountsList.add(adjustedAmounts);
        } else if (SheetTypeName.STATE_WISE == sheetTypeName) {
            StateWise stateWise = new StateWise();
            RecordCarrierUtils.setBaseFields(stateWise, fieldGroup, fieldDateMonthYear, clientOrder);
            stateWiseList.add(stateWise);
        } else if (SheetTypeName.YEARLY_RETURN_SUMMARY == sheetTypeName) {
            YearlyReturnSummary yearlyReturnSummary = new YearlyReturnSummary();
            RecordCarrierUtils.setBaseFields(yearlyReturnSummary, fieldGroup, fieldDateMonthYear, clientOrder);
            yearlyReturnSummaryList.add(yearlyReturnSummary);
        }
    }

    public void newValueRecord(FieldDateMonthYear fieldDateMonthYear) {
        if (SheetTypeName.BIFURCATION == sheetTypeName) {
            BifurcationValue bifurcationValue = new BifurcationValue();
            RecordCarrierUtils.setBaseBifurcatedValueFields(bifurcationValue, fieldDateMonthYear);
            bifurcationValue.setBifurcation(RecordCarrierUtils.getLastElement(bifurcationList));
            bifurcationValueList.add(bifurcationValue);
        } else if (SheetTypeName.ADJUSTED_AMOUNTS == sheetTypeName) {
            AdjustedAmountsValue adjustedAmountsValue = new AdjustedAmountsValue();
            RecordCarrierUtils.setBaseBifurcatedValueFields(adjustedAmountsValue, fieldDateMonthYear);
            adjustedAmountsValue.setAdjustedAmounts(RecordCarrierUtils.getLastElement(adjustedAmountsList));
            adjustedAmountsValueList.add(adjustedAmountsValue);
        } else if (SheetTypeName.STATE_WISE == sheetTypeName) {
            StateWiseValue stateWiseValue = new StateWiseValue();
            RecordCarrierUtils.setBaseBifurcatedValueFields(stateWiseValue, fieldDateMonthYear);
            stateWiseValue.setStateWise(RecordCarrierUtils.getLastElement(stateWiseList));
            stateWiseValueList.add(stateWiseValue);
        } else if (SheetTypeName.YEARLY_RETURN_SUMMARY == sheetTypeName) {
            YearlyReturnSummaryValue yearlyReturnSummaryValue = new YearlyReturnSummaryValue();
            yearlyReturnSummaryValue.setFieldDateMonthYear(fieldDateMonthYear);
            yearlyReturnSummaryValue.setYearlyReturnSummary(RecordCarrierUtils.getLastElement(yearlyReturnSummaryList));
            yearlyReturnSummaryValueList.add(yearlyReturnSummaryValue);
        }
    }

    public void setFieldValue(String header, String value) {
        if (SheetTypeName.BIFURCATION == sheetTypeName) {
            Bifurcation.setValueForFieldHeader(header, RecordCarrierUtils.getLastElement(bifurcationList), value);
        } else if (SheetTypeName.ADJUSTED_AMOUNTS == sheetTypeName) {
            AdjustedAmounts.setValueForFieldHeader(header, RecordCarrierUtils.getLastElement(adjustedAmountsList), value);
        } else if (SheetTypeName.STATE_WISE == sheetTypeName) {
            StateWise.setValueForFieldHeader(header, RecordCarrierUtils.getLastElement(stateWiseList), value);
        } else if (SheetTypeName.YEARLY_RETURN_SUMMARY == sheetTypeName) {
            YearlyReturnSummary.setValueForFieldHeader(header, RecordCarrierUtils.getLastElement(yearlyReturnSummaryList), value);
        }
    }

    public void setValueFieldValue(String header, String value, BigDecimal numericValue) {
        if (SheetTypeName.BIFURCATION == sheetTypeName) {
            BaseSheetBifurcatedValueEntity.setValueForFieldHeader(header, RecordCarrierUtils.getLastElement(bifurcationValueList), value, numericValue);
        } else if (SheetTypeName.ADJUSTED_AMOUNTS == sheetTypeName) {
            BaseSheetBifurcatedValueEntity.setValueForFieldHeader(header, RecordCarrierUtils.getLastElement(adjustedAmountsValueList), value, numericValue);
        } else if (SheetTypeName.STATE_WISE == sheetTypeName) {
            BaseSheetBifurcatedValueEntity.setValueForFieldHeader(header, RecordCarrierUtils.getLastElement(stateWiseValueList), value, numericValue);
        } else if (SheetTypeName.YEARLY_RETURN_SUMMARY == sheetTypeName) {
            YearlyReturnSummaryValue.setValueForFieldHeader(header, RecordCarrierUtils.getLastElement(yearlyReturnSummaryValueList), value, numericValue);
        }
    }
}
