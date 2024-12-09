package com.pf.karza.model.entity.generalProfile;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.UserRequest;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "general_profile_info", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralProfileInfo extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "request_ref_id")
    private UserRequest userRequest;

    @JsonManagedReference
    @OneToOne(mappedBy = "generalProfileInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

    @JsonManagedReference
    @OneToOne(mappedBy = "generalProfileInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private ContactDetails contactDetails;

    @Column(name = "pan")
    private String pan;

    @Column(name = "name")
    private String name;

    @Column(name = "dob")
    private String dob;

    @Column(name = "aadhaar")
    private String aadhaar;

    @Column(name = "passport_no")
    private String passportNo;

    @Column(name = "employer_category")
    @JsonProperty("emplyrCat")
    private String employerCategory;

    @Column(name = "status_of_entity")
    private String statusOfEntity;

    @Column(name = "residential_status")
    private String residentialStatus;

}
