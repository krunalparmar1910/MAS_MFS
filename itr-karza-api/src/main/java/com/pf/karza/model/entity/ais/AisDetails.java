package com.pf.karza.model.entity.ais;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.ais.SftInfo.SftInfo;
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
@Table(name = "ais_data_details", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AisDetails extends BaseId {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = false, name = "ais_data_id")
    @JsonBackReference
    private AisData aisData;


    @JsonManagedReference
    @OneToOne(mappedBy = "aisDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private DemandRefund demandRefund;

    @JsonManagedReference
    @OneToOne(mappedBy = "aisDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private SftInfo sftInfo;

    @JsonManagedReference
    @OneToMany(mappedBy = "aisDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("tdsTcsInfo")
    private List<TdsTcsInfo> tdsTcsInfoList;

    @JsonManagedReference
    @OneToMany(mappedBy = "aisDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("paymentOfTaxes")
    private List<PaymentOfTaxes> paymentOfTaxesList;

}
