package com.pf.karza.model.entity.ais;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@Table(name = "ais_data_total", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AisTotal extends BaseId {
    @ManyToOne
    @JoinColumn(nullable = false, name = "ais_data_id", referencedColumnName = "id")
    @JsonBackReference
    private AisData aisData;

    @Column(name = "info_cat")
    private String infoCat;

    @Column(name = "info_cat_code")
    private String infoCatCode;

    @Column(name = "amount_processed")
    private String amountProcessed;

    @Column(name = "amount_derived")
    private String amountDerived;

    @Column(name = "category_order_no")
    private String categoryOrderNo;

    @OneToMany(mappedBy = "aisTotal", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<AISTotalCpty> cpty;

}
