package com.pf.karza.model.entity.advanced.itrdata.fininfo;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "itr_data_fin_info", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true) // only storing required properties for now
public class ItrDataFinInfo extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_assmt_yr_id")
    @JsonBackReference
    private ItrDataAssmtYear itrDataAssmtYear;

    @JsonProperty("plSumm")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataFinInfo", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataFinInfoPlSumm itrDataFinInfoPlSumm;

    @JsonProperty("bsSumm")
    @JsonManagedReference
    @OneToOne(mappedBy = "itrDataFinInfo", orphanRemoval = true, cascade = CascadeType.ALL)
    private ItrDataFinInfoBsSumm itrDataFinInfoBsSumm;
}
