package com.pf.karza.model.entity.advanced.itrdata.othdtls;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "itr_data_oth_dtls_bank_dtls_additional_foreign", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataOthDtlsBankDtlsAdditionalForeign extends BaseId {
    @ManyToOne
    @JoinColumn(nullable = false, name = "itr_data_oth_dtls_bank_dtls_additional_id")
    @JsonBackReference
    private ItrDataOthDtlsBankDtlsAdditional itrDataOthDtlsBankDtlsAdditional;

    private String bankName;

    private String bankAccountNo;

    private String countryCode;

    private String swiftCode;
}
