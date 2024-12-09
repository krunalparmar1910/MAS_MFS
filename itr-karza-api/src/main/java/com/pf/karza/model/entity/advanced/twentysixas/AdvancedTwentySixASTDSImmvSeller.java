package com.pf.karza.model.entity.advanced.twentysixas;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "advanced_twenty_six_as_tds_immv_seller", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdvancedTwentySixASTDSImmvSeller extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "advanced_twenty_six_as_tds_immv_id")
    @JsonBackReference
    private AdvancedTwentySixASTdsImmv advancedTwentySixASTdsImmv;

    @Column(name = "ttl_ddmnd_pymnt")
    private BigDecimal ttlDdmndPymnt;

    @Column(name = "ttl_tds_depstd")
    private BigDecimal ttlTdsDepstd;

    @JsonProperty("records")
    @JsonManagedReference
    @OneToMany(mappedBy = "advancedTwentySixASTDSImmvSeller", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<AdvancedTwentySixASTDSImmvSellerRecord> advancedTwentySixASTDSImmvSellerRecordList;
}
