package com.pf.karza.model.entity.bankingrelations;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.UserRequest;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "banking_relations", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankingRelations extends BaseId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "request_ref_id")
    @JsonBackReference
    private UserRequest userRequest;

    @OneToOne(mappedBy = "bankingRelations", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Additional additional;


    @OneToOne(mappedBy = "bankingRelations", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("primary")
    @JsonManagedReference
    private PrimaryBankingRelations primaryBankingRelations;
}