package com.pf.karza.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bad_debt_details", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BadDebtDetails extends BaseId {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "request_ref_id")
    private UserRequest userRequest;

    @OneToOne(mappedBy = "badDebtDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private BadDebt badDebt;

    @OneToOne(mappedBy = "badDebtDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private ProvisionForBadAndDoubtfulDebts provisionForBadAndDoubtfulDebts;
}