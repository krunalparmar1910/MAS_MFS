package com.pf.karza.model.entity.twentySixAS;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "26as_data_tds_immv_tds_buyer_tenant_tds_buyer_tenant_record", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TdsBuyerOrTenantRecord extends BaseId {
    @ManyToOne
    @JsonBackReference
    @JoinColumn(nullable = false, name = "tds_buyer_tenant_id")
    private TdsBuyerOrTenant tdsBuyerOrTenant;

    @JsonManagedReference
    @OneToMany(mappedBy = "buyerTenantRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TdsBuyerOrTenantSummary> summary;

    @JsonManagedReference
    @OneToMany(mappedBy = "buyerTenantRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonAlias({"deductorWise", "deducteeWise"})
    private List<TdsBuyerOrTenantDeducteeWise> deducteeWise;


}