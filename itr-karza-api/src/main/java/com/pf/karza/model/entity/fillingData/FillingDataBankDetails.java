package com.pf.karza.model.entity.fillingData;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "filling_data_bank_details", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FillingDataBankDetails extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = false, name = "filling_data_id")
    @JsonBackReference
    private FillingData fillingData;

    @OneToOne(mappedBy = "fillingDataBankDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private PrimaryBankDetails primaryBankDetails;

    @OneToMany(mappedBy = "fillingDataBankDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DomesticBankDetails> domesticBankDetails;

    @OneToMany(mappedBy = "fillingDataBankDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ForeignBankDetails> foreignBankDetails;

}
