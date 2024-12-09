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
@Table(name = "profile_entity_info_contact", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EntityInfoContact extends BaseId {
    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "country_code_mobile_number")
    private String countryCodeMobileNumber;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "mobile_number_secondary")
    private String mobileNumberSecondary;

    @Column(name = "country_code_mobile_number_secondary")
    private String countryCodeMobileNumberSecondary;

    @Column(name = "email_address_secondary")
    private String emailAddressSecondary;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "std_code")
    private String stdCode;

    @OneToOne
    @JoinColumn(nullable = false, name = "entity_info_id")
    @JsonBackReference
    private EntityInfo entityInfo;
}
