package com.pf.karza.model.entity.bankingrelations;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "banking_relations_additional", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Additional extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "banking_relations_id")
    @JsonBackReference
    private BankingRelations bankingRelations;

    @OneToMany(mappedBy = "additional", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("domestic")
    @JsonManagedReference
    private List<DomesticBankingRelations> domesticBankingRelationsList;

    @OneToMany(mappedBy = "additional", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("foreign")
    @JsonManagedReference
    private List<ForeignBankingRelations> foreignBankingRelationsList;

}