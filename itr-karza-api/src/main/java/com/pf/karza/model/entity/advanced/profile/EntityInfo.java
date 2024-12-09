package com.pf.karza.model.entity.advanced.profile;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
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

import java.time.LocalDate;

@Entity
@Table(name = "profile_entity_info", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EntityInfo extends BaseId {
    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "date_of_birth_or_incorporation")
    private LocalDate dateOfBirthOrIncorporation;

    @Column(name = "cin")
    private String cin;

    @Column(name = "llp_id_no")
    private String llpIdNo;

    @Column(name = "status_of_entity")
    private String statusOfEntity;

    @Column(name = "sub_status_of_entity")
    private String subStatusOfEntity;

    @Column(name = "employer_category")
    private String employerCategory;

    @Column(name = "entity_pan")
    private String entityPan;

    @Column(name = "aadhaar_card_no")
    private String aadhaarCardNo;

    @Column(name = "date_of_form_or_commenc")
    private LocalDate dateOfFormOrCommenc;

    @Column(name = "resdntl_sts")
    private String resdntlSts;

    @OneToOne
    @JoinColumn(nullable = false, name = "profile_id")
    @JsonBackReference
    private Profile profile;

    @JsonProperty("address")
    @OneToOne(mappedBy = "entityInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private EntityInfoAddress entityInfoAddress;

    @JsonProperty("contact")
    @OneToOne(mappedBy = "entityInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private EntityInfoContact entityInfoContact;
}
