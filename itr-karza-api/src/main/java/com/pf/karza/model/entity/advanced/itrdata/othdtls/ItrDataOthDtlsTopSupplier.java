package com.pf.karza.model.entity.advanced.itrdata.othdtls;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "itr_data_oth_dtls_top_suppliers", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataOthDtlsTopSupplier extends BaseId {
    @ManyToOne
    @JoinColumn(nullable = false, name = "itr_data_oth_dtls_id")
    @JsonBackReference
    private ItrDataOthDtls itrDataOthDtls;

    private String supplierName;
}
