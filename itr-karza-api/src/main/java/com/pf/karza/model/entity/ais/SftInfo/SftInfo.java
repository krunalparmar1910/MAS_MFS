package com.pf.karza.model.entity.ais.SftInfo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.ais.AisDetails;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "ais_data_details_sft_info", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SftInfo extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "ais_data_details_id", referencedColumnName = "id")
    private AisDetails aisDetails;

    // For sft016Td
    @JsonManagedReference
    @OneToMany(mappedBy = "sftInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sft016Td> sft016Td;

    // For sft018Emf
    @JsonManagedReference
    @OneToMany(mappedBy = "sftInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sft018Emf> sft018Emf;

    // For sft017Otu
    @JsonManagedReference
    @OneToMany(mappedBy = "sftInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sft017Otu> sft017Otu;

    // For sft018Otu
    @JsonManagedReference
    @OneToMany(mappedBy = "sftInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sft018Otu> sft018Otu;

    // For sft010
    @JsonManagedReference
    @OneToMany(mappedBy = "sftInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sft010> sft010;

    // For sft017Pur
    @JsonManagedReference
    @OneToMany(mappedBy = "sftInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sft017Pur> sft017Pur;

    // For sft015
    @JsonManagedReference
    @OneToMany(mappedBy = "sftInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sft015> sft015;

    // For sft016Rd
    @JsonManagedReference
    @OneToMany(mappedBy = "sftInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sft016Rd> sft016Rd;

    // For sft017Les
    @JsonManagedReference
    @OneToMany(mappedBy = "sftInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sft017Les> sft017Les;

    // For sft017Emf
    @JsonManagedReference
    @OneToMany(mappedBy = "sftInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sft017Emf> sft017Emf;

    @JsonManagedReference
    @OneToMany(mappedBy = "sftInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sft018Pur> sft018Pur;

    // For sft006
    @JsonManagedReference
    @OneToMany(mappedBy = "sftInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sft006> sft006;

    // For sft004P
    @JsonManagedReference
    @OneToMany(mappedBy = "sftInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sft004P> sft004P;

    // For sft004R
    @JsonManagedReference
    @OneToMany(mappedBy = "sftInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sft004R> sft004R;

    // For sft003P
    @JsonManagedReference
    @OneToMany(mappedBy = "sftInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sft003P> sft003P;

    // For sft003R
    @JsonManagedReference
    @OneToMany(mappedBy = "sftInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sft003R> sft003R;

    // For sft005
    @JsonManagedReference
    @OneToMany(mappedBy = "sftInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sft005> sft005;

    // For sft012P
    @JsonManagedReference
    @OneToMany(mappedBy = "sftInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sft012P> sft012P;

    // For sft016Sb
    @JsonManagedReference
    @OneToMany(mappedBy = "sftInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sft016Sb> sft016Sb;

    // For sft012S
    @JsonManagedReference
    @OneToMany(mappedBy = "sftInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sft012S> sft012S;




    // Not present in sheet.
    // For sft016Od
    @JsonManagedReference
    @OneToMany(mappedBy = "sftInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sft016Od> sft016Od;

    // For sft018OtuOcMf
    @JsonManagedReference
    @OneToMany(mappedBy = "sftInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sft018OtuOcMf> sft018OtuOcMf;

    // For sft011
    @JsonManagedReference
    @OneToMany(mappedBy = "sftInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sft011> sft011;

    // For sft008
    @JsonManagedReference
    @OneToMany(mappedBy = "sftInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sft008> sft008;





}
