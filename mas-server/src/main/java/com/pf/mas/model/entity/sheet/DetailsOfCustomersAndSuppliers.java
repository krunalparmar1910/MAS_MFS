package com.pf.mas.model.entity.sheet;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseSheetEntity;
import com.pf.mas.service.report.sheet.SheetReaderUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Locale;

@Entity
@Table(name = "details_of_customers_and_suppliers", schema = Constants.GST_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class DetailsOfCustomersAndSuppliers extends BaseSheetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_gstn")
    private String customerGSTN;

    @Column(name = "supplier_gstn")
    private String supplierGSTN;

    @Column(name = "legal_name")
    private String legalName;

    @Column(name = "trade_name")
    private String tradeName;

    @Column(name = "constitution_of_business")
    private String constitutionOfBusiness;

    @Column(name = "nature_of_business")
    private String natureOfBusiness;

    @Column(name = "address")
    private String address;

    @Column(name = "place_of_business")
    private String placeOfBusiness;

    @Column(name = "state")
    private String state;

    @Column(name = "tax_payer_type")
    private String taxPayerType;

    @Column(name = "status")
    private String status;

    @Column(name = "date_of_registration")
    private LocalDate dateOfRegistration;

    @Column(name = "date_of_cancellation")
    private LocalDate dateOfCancellation;

    @Column(name = "filing_regularity")
    private String filingRegularity;

    public static void setValueForFieldHeader(String fieldHeader, DetailsOfCustomersAndSuppliers details, String value) {
        switch (fieldHeader.trim().toUpperCase(Locale.ROOT)) {
            case "CUSTOMERS'S GSTN" -> details.setCustomerGSTN(value);
            case "SUPPLIER'S GSTN" -> details.setSupplierGSTN(value);
            case "LEGAL NAME" -> details.setLegalName(value);
            case "TRADE NAME" -> details.setTradeName(value);
            case "CONSTITUTION OF BUSINESS" -> details.setConstitutionOfBusiness(value);
            case "NATURE OF BUSINESS" -> details.setNatureOfBusiness(value);
            case "ADDRESS" -> details.setAddress(value);
            case "PLACE OF BUSINESS" -> details.setPlaceOfBusiness(value);
            case "STATE" -> details.setState(value);
            case "TAX PAYER TYPE" -> details.setTaxPayerType(value);
            case "STATUS" -> details.setStatus(value);
            case "DATE OF REGISTRATION" -> details.setDateOfRegistration(SheetReaderUtils.parseDateValue(value));
            case "DATE OF CANCELLATION" -> details.setDateOfCancellation(SheetReaderUtils.parseDateValue(value));
            case "FILLING REGULARITY" -> details.setFilingRegularity(value);
            default -> {/* no field found */}
        }
    }
}
