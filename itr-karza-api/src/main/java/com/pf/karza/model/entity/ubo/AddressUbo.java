package com.pf.karza.model.entity.ubo;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "address_ubo", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressUbo extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "ubo_id")
    @JsonBackReference
    private Ubo ubo;

    @Column(name = "city_or_town_or_district")
    private String cityOrTownOrDistrict;

    @Column(name = "addr_detail")
    private String addrDetail;

    @Column(name = "state_code")
    private String stateCode;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "pin_code")
    private String pinCode;
}