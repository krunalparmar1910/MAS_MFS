package com.pf.karza.model.entity.bankingrelations;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "banking_relations_domestic", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomesticBankingRelations extends BaseId {
    @ManyToOne
    @JoinColumn(nullable = false, name = "banking_relations_additional_id", referencedColumnName = "id")
    @JsonBackReference
    private Additional additional;

    @Column(name = "use_refund")
    private String useRefund;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "bank_account_type")
    private String bankAccountType;

    @Column(name = "bank_account_no")
    private String bankAccountNo;

    @Column(name = "cash_deposited")
    private String cashDeposited;


}