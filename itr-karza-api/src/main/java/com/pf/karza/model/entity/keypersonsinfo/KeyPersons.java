package com.pf.karza.model.entity.keypersonsinfo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "key_persons_info_key_persons", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeyPersons extends BaseId {
    @ManyToOne
    @JoinColumn(nullable = false, name = "key_persons_info_id", referencedColumnName = "id")
    @JsonBackReference
    private KeyPersonsInfo keyPersonsInfo;

    @Column(name = "din")
    private String din;

    @Column(name = "address_detail_pin_code")
    private String addressDetailPinCode;

    @Column(name = "name")
    private String name;

    @Column(name = "designation")
    private String designation;

    @Column(name = "key_person_pan")
    private String keyPersonPan;

    @Column(name = "remuneration_paid_payable")
    private String remunerationPaidPayable;

    @Column(name = "aadhaar_card_no")
    private String aadhaarCardNo;

    @Column(name = "percentage_of_share")
    private String percentageOfShare;

    @Column(name = "address_detail_city_or_town_or_district")
    private String addressDetailCityOrTownOrDistrict;

    @Column(name = "partners_status")
    private String partnersStatus;

    @Column(name = "address_detail_state_code")
    private String addressDetailStateCode;

    @Column(name = "address_detail_country_code")
    private String addressDetailCountryCode;

    @Column(name = "address_detail")
    private String addressDetail;

    @Column(name = "if_member_a_foreign_company")
    private String ifMemberAForeignCompany;

    @Column(name = "rate_of_interest_on_capital")
    private String rateOfInterestOnCapital;
}