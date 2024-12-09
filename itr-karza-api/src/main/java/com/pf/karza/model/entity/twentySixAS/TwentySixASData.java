package com.pf.karza.model.entity.twentySixAS;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.UserRequest;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "26as_data", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TwentySixASData extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "request_ref_id")
    private UserRequest userRequest;

    @Column(name = "status_of_26as_data")
    private String statusOf26asData;

    @Column(name = "assessment_year")
    private String assessmentYear;

    @JsonManagedReference
    @OneToMany(mappedBy = "twentySixASData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TcsDetails> tcsDetails;

    @JsonManagedReference
    @OneToMany(mappedBy = "twentySixASData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OtherTaxPaid> othTaxPaid;

    @JsonManagedReference
    @OneToMany(mappedBy = "twentySixASData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AirTransaction> airTransaction;

    @JsonManagedReference
    @OneToMany(mappedBy = "twentySixASData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Gstr3b> gstr3b;

    @JsonManagedReference
    @OneToOne(mappedBy = "twentySixASData", cascade = CascadeType.ALL, orphanRemoval = true)
    private TdsImmv tdsImmv;

    @JsonManagedReference
    @OneToOne(mappedBy = "twentySixASData", cascade = CascadeType.ALL, orphanRemoval = true)
    private TdsDetails tdsDetails;

    @JsonManagedReference
    @OneToMany(mappedBy = "twentySixASData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RefundPaid> refundPaid;

    @JsonManagedReference
    @OneToMany(mappedBy = "twentySixASData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TdsDefaults> tdsDefaults;

    // Include other fields and relationships here as needed
}