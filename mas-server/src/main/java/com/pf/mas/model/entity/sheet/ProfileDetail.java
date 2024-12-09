package com.pf.mas.model.entity.sheet;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseSheetEntity;
import com.pf.mas.service.report.sheet.SheetReaderUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

@Entity
@Table(name = "profile_detail", schema = Constants.GST_DB_SCHEMA)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ProfileDetail extends BaseSheetEntity {
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "uuid")
    private final UUID uuid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdTimestamp;

    @Column(name = "pan_number")
    private String panNumber;

    @Column(name = "gst_number")
    private String gstNumber;

    @Column(name = "legal_name")
    private String legalName;

    @Column(name = "trade_name")
    private String tradeName;

    @Column(name = "constitution_of_business")
    private String constitutionOfBusiness;

    @Column(name = "nature_of_business")
    private String natureOfBusiness;

    @Column(name = "nature_of_core_business_activity")
    private String natureOfCoreBusinessActivity;

    @Column(name = "status")
    private String status;

    @Column(name = "tax_payer_type")
    private String taxPayerType;

    @Column(name = "address")
    private String address;

    @Column(name = "place_of_business")
    private String placeOfBusiness;

    @Column(name = "state")
    private String state;

    @Column(name = "state_jurisdiction")
    private String stateJurisdiction;

    @Column(name = "state_jurisdiction_code")
    private String stateJurisdictionCode;

    @Column(name = "center_jurisdiction")
    private String centerJurisdiction;

    @Column(name = "center_jurisdiction_code")
    private String centerJurisdictionCode;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "date_of_registration")
    private LocalDate dateOfRegistration;

    @Column(name = "date_of_cancellation")
    private LocalDate dateOfCancellation;

    @Column(name = "last_updated_date")
    private LocalDate lastUpdatedDate;

    public ProfileDetail() {
        super();
        this.uuid = UUID.randomUUID();
    }

    public static boolean setValueForFieldHeader(String fieldHeader, ProfileDetail profileDetail, String value) {
        switch (fieldHeader.trim().toUpperCase(Locale.ROOT)) {
            case "PAN" -> profileDetail.setPanNumber(value);
            case "GSTN" -> profileDetail.setGstNumber(value);
            case "LEGAL NAME OF BUSINESS" -> profileDetail.setLegalName(value);
            case "TRADE NAME" -> profileDetail.setTradeName(value);
            case "CONSTITUTION OF BUSINESS" -> profileDetail.setConstitutionOfBusiness(value);
            case "NATURE OF BUSINESS" -> profileDetail.setNatureOfBusiness(value);
            case "NATURE OF CORE BUSINESS ACTIVITY" -> profileDetail.setNatureOfCoreBusinessActivity(value);
            case "STATUS" -> profileDetail.setStatus(value);
            case "TAX PAYER TYPE" -> profileDetail.setTaxPayerType(value);
            case "ADDRESS" -> profileDetail.setAddress(value);
            case "PLACE OF BUSINESS" -> profileDetail.setPlaceOfBusiness(value);
            case "STATE" -> profileDetail.setState(value);
            case "STATE JURISDICTION" -> profileDetail.setStateJurisdiction(value);
            case "STATE JURISDICTION CODE" -> profileDetail.setStateJurisdictionCode(value);
            case "CENTER JURISDICTION" -> profileDetail.setCenterJurisdiction(value);
            case "CENTER JURISDICTION CODE" -> profileDetail.setCenterJurisdictionCode(value);
            case "LATITUDE" -> profileDetail.setLatitude(value);
            case "LONGITUDE" -> profileDetail.setLongitude(value);
            case "DATE OF REGISTRATION" -> profileDetail.setDateOfRegistration(SheetReaderUtils.parseDateValue(value));
            case "DATE OF CANCELLATION" -> profileDetail.setDateOfCancellation(SheetReaderUtils.parseDateValue(value));
            case "LAST UPDATED DATE" -> profileDetail.setLastUpdatedDate(SheetReaderUtils.parseDateValue(value));
            default -> {
                // no field found
                return false;
            }
        }
        return true;
    }
}
