package com.pf.karza.model.entity.bankingrelations;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "banking_relations_foreign", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForeignBankingRelations extends BaseId {
    @ManyToOne
    @JoinColumn(nullable = false, name = "banking_relations_additional_id", referencedColumnName = "id")
    @JsonBackReference
    private Additional additional;

    @Column(name = "swift_code")
    private String swiftCode;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "bank_account_no")
    private String bankAccountNo;


}