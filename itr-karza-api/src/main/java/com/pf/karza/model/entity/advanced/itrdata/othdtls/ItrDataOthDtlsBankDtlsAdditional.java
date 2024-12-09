package com.pf.karza.model.entity.advanced.itrdata.othdtls;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "itr_data_oth_dtls_bank_dtls_additional", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataOthDtlsBankDtlsAdditional extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_oth_dtls_bank_dtls_id")
    @JsonBackReference
    private ItrDataOthDtlsBankDtls itrDataOthDtlsBankDtls;

    @JsonProperty("domestic")
    @JsonManagedReference
    @OneToMany(mappedBy = "itrDataOthDtlsBankDtlsAdditional", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ItrDataOthDtlsBankDtlsAdditionalDomestic> itrDataOthDtlsBankDtlsAdditionalDomesticList;

    @JsonProperty("foreign")
    @JsonManagedReference
    @OneToMany(mappedBy = "itrDataOthDtlsBankDtlsAdditional", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ItrDataOthDtlsBankDtlsAdditionalForeign> itrDataOthDtlsBankDtlsAdditionalForeignList;
}
