package com.pf.karza.model.entity.advanced.itrdata.othdtls;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "itr_data_oth_dtls_bank_dtls_primary", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataOthDtlsBankDtlsPrimary extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_oth_dtls_bank_dtls_id")
    @JsonBackReference
    private ItrDataOthDtlsBankDtls itrDataOthDtlsBankDtls;

    private String bankName;

    private String bankAccountNo;

    private String cashDeposited;

    private String ifscCode;

    private String bankAccountType;
}
