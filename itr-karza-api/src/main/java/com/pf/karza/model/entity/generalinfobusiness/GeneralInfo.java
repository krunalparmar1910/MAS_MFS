package com.pf.karza.model.entity.generalinfobusiness;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.UserRequest;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "general_info", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralInfo extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "request_ref_id")
    private UserRequest userRequest;

    @JsonManagedReference
    @OneToMany(mappedBy = "generalInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NatOfBusiness> natOfBusiness;

    @JsonManagedReference
    @OneToOne(mappedBy = "generalInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Phone phone;

    @JsonManagedReference
    @OneToOne(mappedBy = "generalInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private AddressBusiness address;

    @JsonManagedReference
    @OneToOne(mappedBy = "generalInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private ContactInfo contact;

    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "entity_pan")
    private String entityPan;

    @Column(name = "date_of_birth_or_incorporation")
    private String dateOfBirthOrIncorporation;

    @Column(name = "status_of_entity")
    private String statusOfEntity;

    @Column(name = "aadhar_card_no")
    private String aadharCardNo;

    @Column(name = "cin")
    private String cin;

    @Column(name = "sub_status_of_entity")
    private String subStatusOfEntity;

    @Column(name = "employer_category")
    private String employerCategory;

    @Column(name = "domestic_company_flag")
    private String domesticCompanyFlag;

    @Column(name = "date_of_form_or_commenc")
    private String dateOfFormOrCommenc;
}
