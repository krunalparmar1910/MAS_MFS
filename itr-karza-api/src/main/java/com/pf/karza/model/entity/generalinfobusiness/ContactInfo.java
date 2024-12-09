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
@Table(name = "general_info_contact", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ContactInfo extends BaseId {
    @JsonBackReference
    @OneToOne
    @JoinColumn(nullable = false, name = "general_info_id")
    private GeneralInfo generalInfo;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "email_address_secondary")
    private String emailAddressSecondary;

    @Column(name = "mobile_number_secondary")
    private String mobileNumberSecondary;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "country_code_mobile_number_secondary")
    private String countryCodeMobileNumberSecondary;

    @Column(name = "country_code_mobile_number")
    private String countryCodeMobileNumber;
}