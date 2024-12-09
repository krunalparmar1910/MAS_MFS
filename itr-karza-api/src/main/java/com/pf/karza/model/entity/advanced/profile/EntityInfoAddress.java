package com.pf.karza.model.entity.advanced.profile;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profile_entity_info_address", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EntityInfoAddress extends BaseId {
    @Column(name = "residence_name")
    private String residenceName;

    @Column(name = "residence_no")
    private String residenceNo;

    @Column(name = "road_or_street")
    private String roadOrStreet;

    @Column(name = "city_or_town_or_district")
    private String cityOrTownOrDistrict;

    @Column(name = "locality_or_area")
    private String localityOrArea;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "state_code")
    private String stateCode;

    @Column(name = "country_code")
    private String countryCode;

    @OneToOne
    @JoinColumn(nullable = false, name = "entity_info_id")
    @JsonBackReference
    private EntityInfo entityInfo;
}
