package com.pf.karza.model.entity.bankingrelations;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "banking_relations_primary", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrimaryBankingRelations extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "banking_relations_id")
    @JsonBackReference
    private BankingRelations bankingRelations;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "cash_deposited")
    private String cashDeposited;

    @Column(name = "bank_account_type")
    private String bankAccountType;

    @Column(name = "bank_account_no")
    private String bankAccountNo;


}