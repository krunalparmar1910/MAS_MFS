package com.pf.karza.model.entity.ais;


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
@Table(name = "ais_profile", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AisProfile extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "ais_id")
    @JsonBackReference
    private Ais ais;


    @Column(name = "date_of_incorporation")
    private String dateOfIncorporation;

    @Column(name = "name")
    private String name;

    @Column(name = "pan")
    private String pan;

    @Column(name = "address")
    private String address;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "aadhar_number")
    private String aadharNumber;
}