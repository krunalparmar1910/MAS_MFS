package com.pf.karza.model.entity.advanced.itrdata.taxdetails;

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

import java.math.BigDecimal;

@Entity
@Table(name = "itr_data_tax_details_cmptn_cap_gain", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataTaxDetailsCmptnCapGain extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_tax_details_cmptn_id")
    @JsonBackReference
    private ItrDataTaxDetailsCmptn itrDataTaxDetailsCmptn;

    @JsonProperty("stcg")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsCmptnCapGain", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsCmptnCapGainStcg itrDataTaxDetailsCmptnCapGainStcg;

    @JsonProperty("longTermCg")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataTaxDetailsCmptnCapGain", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataTaxDetailsCmptnCapGainLongTermCg itrDataTaxDetailsCmptnCapGainLongTermCg;

    private BigDecimal ttlCapGn;
}
