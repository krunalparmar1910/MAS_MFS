package com.pf.karza.model.entity.advanced.twentysixas;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.constant.ModelConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "advanced_twenty_six_as_air_transaction_part_e", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdvancedTwentySixASAirTransactionPartE extends BaseId {
    @ManyToOne
    @JoinColumn(nullable = false, name = "advanced_twenty_six_as_data_entry_id")
    @JsonBackReference
    private AdvancedTwentySixASDataEntry advancedTwentySixASDataEntry;

    @Column(name = "type_of_transaction")
    private String typeOfTransaction;

    @Column(name = "name_Of_air_filer")
    private String nameOfAIRFiler;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ModelConstants.DD_MM_YYYY_FORMAT)
    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "party_transaction")
    private String partyTransaction;

    @Column(name = "no_of_parties")
    private Integer noOfParties;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "mode")
    private String mode;

    @Column(name = "remarks")
    private String remarks;
}
