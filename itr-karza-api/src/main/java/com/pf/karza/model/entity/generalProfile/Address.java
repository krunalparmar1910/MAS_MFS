package com.pf.karza.model.entity.generalProfile;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "general_profile_info_address", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address extends BaseId {
    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "general_profile_info_id")
    private GeneralProfileInfo generalProfileInfo;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "state_code")
    private String stateCode;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "residence_no")
    private String residenceNo;

    @Column(name = "road_or_street")
    private String roadOrStreet;

    @Column(name = "residence_name")
    private String residenceName;

    @Column(name = "locality_or_area")
    private String localityOrArea;

    @Column(name = "city_or_town_or_district")
    private String cityOrTownOrDistrict;
}