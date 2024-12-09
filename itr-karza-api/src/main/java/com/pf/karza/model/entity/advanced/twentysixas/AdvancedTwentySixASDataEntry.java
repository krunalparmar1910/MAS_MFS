package com.pf.karza.model.entity.advanced.twentysixas;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "advanced_twenty_six_as_data_entry", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdvancedTwentySixASDataEntry extends BaseId {
    @JsonProperty("tdsDtls")
    @JsonManagedReference
    @OneToOne(mappedBy = "advancedTwentySixASDataEntry", orphanRemoval = true, cascade = CascadeType.ALL)
    private AdvancedTwentySixASTdsDtls advancedTwentySixASTdsDtls;

    @JsonProperty("tcsDetailsPartB")
    @JsonManagedReference
    @OneToMany(mappedBy = "advancedTwentySixASDataEntry", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<AdvancedTwentySixASTcsDetailsPartB> advancedTwentySixASTcsDetailsPartBList;

    @JsonProperty("othTaxPaidPartC")
    @JsonManagedReference
    @OneToMany(mappedBy = "advancedTwentySixASDataEntry", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<AdvancedTwentySixASOthTaxPaidPartC> advancedTwentySixASOthTaxPaidPartCList;

    @JsonProperty("refundPaidPartD")
    @JsonManagedReference
    @OneToMany(mappedBy = "advancedTwentySixASDataEntry", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<AdvancedTwentySixASRefundPaidPartD> advancedTwentySixASRefundPaidPartDList;

    @JsonProperty("tdsImmv")
    @JsonManagedReference
    @OneToOne(mappedBy = "advancedTwentySixASDataEntry", orphanRemoval = true, cascade = CascadeType.ALL)
    private AdvancedTwentySixASTdsImmv advancedTwentySixASTdsImmv;

    @JsonProperty("airTransactionPartE")
    @JsonManagedReference
    @OneToMany(mappedBy = "advancedTwentySixASDataEntry", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<AdvancedTwentySixASAirTransactionPartE> advancedTwentySixASAirTransactionPartEList;

    @JsonProperty("tdsDefaultsPartG")
    @JsonManagedReference
    @OneToMany(mappedBy = "advancedTwentySixASDataEntry", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<AdvancedTwentySixASTdsDefaultsPartG> advancedTwentySixASTdsDefaultsPartGList;

    @JsonProperty("gstr3bPartH")
    @JsonManagedReference
    @OneToMany(mappedBy = "advancedTwentySixASDataEntry", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<AdvancedTwentySixASGstr3bPartH> advancedTwentySixASGstr3bPartHList;

    @ManyToOne
    @JoinColumn(nullable = false, name = "advanced_twenty_six_as_data_id")
    @JsonBackReference
    private AdvancedTwentySixASData advancedTwentySixASData;
}
