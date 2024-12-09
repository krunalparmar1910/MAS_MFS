package com.pf.karza.model.entity;

import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "key_person_info", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeyPersonInfo extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "request_ref_id")
    private UserRequest userRequest;

    private String kp_type;

    private String din;

    private String addressDetailPinCode;

    private String name;

    private String designation;

    private String keyPersonPan;

    private String remunerationPaidPayable;

    private String aadhaarCardNo;

    private String percentageOfShare;

    private String addressDetailCityOrTownOrDistrict;

    private String partnersStatus;

    private String addressDetailStateCode;

    private String addressDetailCountryCode;

    private String addressDetail;

    @Column(name = "if_member_a_foreign_company")
    private String ifMemberAForeignCompany;

    private String rateOfInterestOnCapital;
}
