package com.pf.mas.service.report.sheet.carrier;

import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.FieldDateMonthYear;
import com.pf.mas.model.entity.FieldGroup;
import com.pf.mas.model.entity.base.BaseSheetBifurcatedValueEntity;
import com.pf.mas.model.entity.base.BaseSheetEntity;
import com.pf.mas.model.entity.base.BaseSheetValueEntity;

import java.math.BigDecimal;
import java.util.List;

public final class RecordCarrierUtils {
    private RecordCarrierUtils() {
        // utils class
    }

    public static void setBaseFields(BaseSheetEntity baseSheetEntity, FieldGroup fieldGroup, FieldDateMonthYear fieldDateMonthYear, ClientOrder clientOrder) {
        baseSheetEntity.setFieldGroup(fieldGroup);
        baseSheetEntity.setFieldDateMonthYear(fieldDateMonthYear);
        baseSheetEntity.setClientOrder(clientOrder);
    }

    public static void setBaseValueFields(BaseSheetValueEntity baseSheetValueEntity, FieldDateMonthYear fieldDateMonthYear, String value, BigDecimal numericValue) {
        baseSheetValueEntity.setFieldDateMonthYear(fieldDateMonthYear);
        baseSheetValueEntity.setFieldValue(value);
        baseSheetValueEntity.setFieldValueNumeric(numericValue);
    }

    public static void setBaseBifurcatedValueFields(BaseSheetBifurcatedValueEntity baseBifurcatedValueFields, FieldDateMonthYear fieldDateMonthYear) {
        baseBifurcatedValueFields.setFieldDateMonthYear(fieldDateMonthYear);
    }

    public static <T> T getLastElement(List<T> list) {
        return list.get(list.size() - 1);
    }
}
