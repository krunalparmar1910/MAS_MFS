package com.pf.karza.model.entity.twentySixAS;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "26as_data_air_transaction", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirTransaction extends BaseId {

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(nullable = false, name = "26as_data_id")
    private TwentySixASData twentySixASData;

    @Column(name = "party_transaction")
    private String partyTransaction;

    @Column(name = "name_of_air_filer")
    private String nameOfAIRFiler;

    @Column(name = "no_of_parties")
    private String noOfParties;

    @Column(name = "transaction_date")
    private String transactionDate;

    @Column(name = "type_of_transaction")
    private String typeOfTransaction;

    @Column(name = "amount")
    private String amount;

    @Column(name = "mode")
    private String mode;

    @Column(name = "remarks")
    private String remarks;

    // Include other fields and relationships here as needed
}
