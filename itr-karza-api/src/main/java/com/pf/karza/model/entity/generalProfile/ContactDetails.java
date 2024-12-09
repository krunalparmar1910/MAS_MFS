package com.pf.karza.model.entity.generalProfile;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "general_profile_info_contact_details", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactDetails extends BaseId {
    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "general_profile_info_id")
    private GeneralProfileInfo generalProfileInfo;

    @Column(name = "std_code")
    @JsonProperty("std")
    private String stdCode;

    @Column(name = "email")
    private String email;

    @Column(name = "ph_no")
    private String phNo;

    @Column(name = "email_2")
    private String email2;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "mobile_2")
    private String mobile2;
}