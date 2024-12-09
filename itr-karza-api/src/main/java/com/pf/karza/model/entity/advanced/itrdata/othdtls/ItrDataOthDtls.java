package com.pf.karza.model.entity.advanced.itrdata.othdtls;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.advanced.itrdata.ItrDataAssmtYear;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "itr_data_oth_dtls", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true) // only storing required properties for now
public class ItrDataOthDtls extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_assmt_yr_id")
    @JsonBackReference
    private ItrDataAssmtYear itrDataAssmtYear;

    @JsonProperty("topCustomers")
    @JsonManagedReference
    @OneToMany(mappedBy = "itrDataOthDtls", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ItrDataOthDtlsTopCustomer> itrDataOthDtlsTopCustomers;

    @JsonProperty("topSuppliers")
    @JsonManagedReference
    @OneToMany(mappedBy = "itrDataOthDtls", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ItrDataOthDtlsTopSupplier> itrDataOthDtlsTopSuppliers;

    @JsonProperty("bankDtls")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataOthDtls", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataOthDtlsBankDtls itrDataOthDtlsBankDtls;
}
