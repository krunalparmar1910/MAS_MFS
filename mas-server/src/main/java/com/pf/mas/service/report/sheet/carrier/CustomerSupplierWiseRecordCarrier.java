package com.pf.mas.service.report.sheet.carrier;

import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.FieldDateMonthYear;
import com.pf.mas.model.entity.FieldGroup;
import com.pf.mas.model.entity.SheetTypeName;
import com.pf.mas.model.entity.sheet.CircularTransactions;
import com.pf.mas.model.entity.sheet.CircularTransactionsValue;
import com.pf.mas.model.entity.sheet.CustomerProductSupplierWiseTotal;
import com.pf.mas.model.entity.sheet.CustomerWise;
import com.pf.mas.model.entity.sheet.CustomerWiseValue;
import com.pf.mas.model.entity.sheet.ProductWise;
import com.pf.mas.model.entity.sheet.ProductWiseValue;
import com.pf.mas.model.entity.sheet.SupplierWise;
import com.pf.mas.model.entity.sheet.SupplierWiseValue;
import lombok.AccessLevel;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CustomerSupplierWiseRecordCarrier {
    @Getter(AccessLevel.NONE)
    private final SheetTypeName sheetTypeName;
    private final List<CustomerProductSupplierWiseTotal> customerProductSupplierWiseTotalList;
    private List<CircularTransactions> circularTransactionsList;
    private List<CustomerWise> customerWiseList;
    private List<SupplierWise> supplierWiseList;
    private List<ProductWise> productWiseList;
    private List<CircularTransactionsValue> circularTransactionsValueList;
    private List<CustomerWiseValue> customerWiseValueList;
    private List<SupplierWiseValue> supplierWiseValueList;
    private List<ProductWiseValue> productWiseValueList;

    public CustomerSupplierWiseRecordCarrier(SheetTypeName sheetTypeName) {
        this.sheetTypeName = sheetTypeName;
        customerProductSupplierWiseTotalList = new ArrayList<>();
        if (SheetTypeName.CIRCULAR_TRANSACTIONS == sheetTypeName) {
            circularTransactionsList = new ArrayList<>();
            circularTransactionsValueList = new ArrayList<>();
        } else if (SheetTypeName.CUSTOMER_WISE == sheetTypeName) {
            customerWiseList = new ArrayList<>();
            customerWiseValueList = new ArrayList<>();
        } else if (SheetTypeName.SUPPLIER_WISE == sheetTypeName) {
            supplierWiseList = new ArrayList<>();
            supplierWiseValueList = new ArrayList<>();
        } else if (SheetTypeName.PRODUCT_WISE == sheetTypeName) {
            productWiseList = new ArrayList<>();
            productWiseValueList = new ArrayList<>();
        }
    }

    public void newRecord(FieldGroup fieldGroup, FieldDateMonthYear fieldDateMonthYear, ClientOrder clientOrder) {
        if (SheetTypeName.CIRCULAR_TRANSACTIONS == sheetTypeName) {
            CircularTransactions circularTransactions = new CircularTransactions();
            RecordCarrierUtils.setBaseFields(circularTransactions, fieldGroup, fieldDateMonthYear, clientOrder);
            circularTransactionsList.add(circularTransactions);
        } else if (SheetTypeName.CUSTOMER_WISE == sheetTypeName) {
            CustomerWise customerWise = new CustomerWise();
            RecordCarrierUtils.setBaseFields(customerWise, fieldGroup, fieldDateMonthYear, clientOrder);
            customerWiseList.add(customerWise);
        } else if (SheetTypeName.SUPPLIER_WISE == sheetTypeName) {
            SupplierWise supplierWise = new SupplierWise();
            RecordCarrierUtils.setBaseFields(supplierWise, fieldGroup, fieldDateMonthYear, clientOrder);
            supplierWiseList.add(supplierWise);
        } else if (SheetTypeName.PRODUCT_WISE == sheetTypeName) {
            ProductWise productWise = new ProductWise();
            RecordCarrierUtils.setBaseFields(productWise, fieldGroup, fieldDateMonthYear, clientOrder);
            productWiseList.add(productWise);
        }
    }

    public void newValueRecord(FieldDateMonthYear fieldDateMonthYear, String value, BigDecimal numericValue) {
        if (SheetTypeName.CIRCULAR_TRANSACTIONS == sheetTypeName) {
            CircularTransactionsValue circularTransactionsValue = new CircularTransactionsValue();
            RecordCarrierUtils.setBaseValueFields(circularTransactionsValue, fieldDateMonthYear, value, numericValue);
            circularTransactionsValue.setCircularTransactions(RecordCarrierUtils.getLastElement(circularTransactionsList));
            circularTransactionsValueList.add(circularTransactionsValue);
        } else if (SheetTypeName.CUSTOMER_WISE == sheetTypeName) {
            CustomerWiseValue customerWiseValue = new CustomerWiseValue();
            RecordCarrierUtils.setBaseValueFields(customerWiseValue, fieldDateMonthYear, value, numericValue);
            customerWiseValue.setCustomerWise(RecordCarrierUtils.getLastElement(customerWiseList));
            customerWiseValueList.add(customerWiseValue);
        } else if (SheetTypeName.SUPPLIER_WISE == sheetTypeName) {
            SupplierWiseValue supplierWiseValue = new SupplierWiseValue();
            RecordCarrierUtils.setBaseValueFields(supplierWiseValue, fieldDateMonthYear, value, numericValue);
            supplierWiseValue.setSupplierWise(RecordCarrierUtils.getLastElement(supplierWiseList));
            supplierWiseValueList.add(supplierWiseValue);
        } else if (SheetTypeName.PRODUCT_WISE == sheetTypeName) {
            ProductWiseValue productWiseValue = new ProductWiseValue();
            RecordCarrierUtils.setBaseValueFields(productWiseValue, fieldDateMonthYear, value, numericValue);
            productWiseValue.setProductWise(RecordCarrierUtils.getLastElement(productWiseList));
            productWiseValueList.add(productWiseValue);
        }
    }

    public void newTotalRecord(
            String fieldName, String totalValue, BigDecimal numericValue, FieldGroup fieldGroup, FieldDateMonthYear fieldDateMonthYear, ClientOrder clientOrder) {
        CustomerProductSupplierWiseTotal customerProductSupplierWiseTotal = new CustomerProductSupplierWiseTotal();
        RecordCarrierUtils.setBaseFields(customerProductSupplierWiseTotal, fieldGroup, fieldDateMonthYear, clientOrder);
        customerProductSupplierWiseTotal.setFieldName(fieldName);
        customerProductSupplierWiseTotal.setTotalValue(totalValue);
        customerProductSupplierWiseTotal.setTotalValueNumeric(numericValue);
        customerProductSupplierWiseTotalList.add(customerProductSupplierWiseTotal);
    }

    public void setFieldValue(String header, String value) {
        if (SheetTypeName.CIRCULAR_TRANSACTIONS == sheetTypeName) {
            CircularTransactions.setValueForFieldHeader(header, RecordCarrierUtils.getLastElement(circularTransactionsList), value);
        } else if (SheetTypeName.CUSTOMER_WISE == sheetTypeName) {
            CustomerWise.setValueForFieldHeader(header, RecordCarrierUtils.getLastElement(customerWiseList), value);
        } else if (SheetTypeName.SUPPLIER_WISE == sheetTypeName) {
            SupplierWise.setValueForFieldHeader(header, RecordCarrierUtils.getLastElement(supplierWiseList), value);
        } else if (SheetTypeName.PRODUCT_WISE == sheetTypeName) {
            ProductWise.setValueForFieldHeader(header, RecordCarrierUtils.getLastElement(productWiseList), value);
        }
    }
}
