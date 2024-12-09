package com.pf.karza.model.entity.twentySixAS;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "26as_data_tds_immv", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TdsImmv extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "26as_data_id", referencedColumnName = "id")
    private TwentySixASData twentySixASData;

    @JsonManagedReference
    @OneToOne(mappedBy = "tdsImmv", cascade = CascadeType.ALL, orphanRemoval = true)
    private TdsSellerOrLandlord sellerOrLandlord;

    @JsonManagedReference
    @OneToOne(mappedBy = "tdsImmv", cascade = CascadeType.ALL, orphanRemoval = true)
    private TdsBuyerOrTenant buyerOrTenant;

    // Include other fields and relationships here as needed
}