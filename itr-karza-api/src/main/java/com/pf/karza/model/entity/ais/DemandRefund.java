package com.pf.karza.model.entity.ais;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "ais_data_details_demand_refund", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemandRefund extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "ais_data_details_id", referencedColumnName = "id")
    @JsonBackReference
    private AisDetails aisDetails;

    @JsonManagedReference
    @OneToMany(mappedBy = "demandRefund", cascade = CascadeType.ALL)
    @JsonProperty("demand")
    private List<Demand> demands;

    @JsonManagedReference
    @OneToMany(mappedBy = "demandRefund", cascade = CascadeType.ALL)
    @JsonProperty("refund")
    private List<Refund> refunds;
}