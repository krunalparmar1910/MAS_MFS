package com.pf.karza.model.entity.generalinfobusiness;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "general_info_address", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AddressBusiness extends BaseId {
    @JsonBackReference
    @OneToOne
    @JoinColumn(nullable = false, name = "general_info_id")
    private GeneralInfo generalInfo;

    @Column(name = "road_or_street")
    private String roadOrStreet;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "city_or_town_or_district")
    private String cityOrTownOrDistrict;

    @Column(name = "residence_name")
    private String residenceName;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "residence_no")
    private String residenceNo;

    @Column(name = "locality_or_area")
    private String localityOrArea;

    @Column(name = "state_code")
    private String stateCode;
}