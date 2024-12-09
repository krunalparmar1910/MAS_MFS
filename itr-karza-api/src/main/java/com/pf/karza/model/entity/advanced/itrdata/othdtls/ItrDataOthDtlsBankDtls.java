package com.pf.karza.model.entity.advanced.itrdata.othdtls;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "itr_data_oth_dtls_bank_dtls", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataOthDtlsBankDtls extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_oth_dtls_id")
    @JsonBackReference
    private ItrDataOthDtls itrDataOthDtls;

    @JsonProperty("additional")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataOthDtlsBankDtls", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataOthDtlsBankDtlsAdditional itrDataOthDtlsBankDtlsAdditional;

    @JsonProperty("primary")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataOthDtlsBankDtls", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataOthDtlsBankDtlsPrimary itrDataOthDtlsBankDtlsPrimary;
}
