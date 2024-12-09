package com.pf.karza.model.entity.twentySixAS;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@Table(name = "26as_data_tds_immv_tds_buyer_tenant", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TdsBuyerOrTenant extends BaseId {
    @OneToOne
    @JsonBackReference
    @JoinColumn(nullable = false, name = "tds_immv_id")
    private TdsImmv tdsImmv;

    @JsonManagedReference
    @OneToMany(mappedBy = "tdsBuyerOrTenant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TdsBuyerOrTenantRecord> records;

    @Column(name = "ttl_amt_oth_tds")
    private String ttlAmtOthTDS;

    @Column(name = "ttl_demd_paymnt")
    private String ttlDemdPaymnt;

    @Column(name = "ttl_tds_deposited")
    private String ttlTdsDeposited;
}
