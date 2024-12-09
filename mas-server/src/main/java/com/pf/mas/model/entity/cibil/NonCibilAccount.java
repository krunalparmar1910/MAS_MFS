package com.pf.mas.model.entity.cibil;

import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseUUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "non_cibil_account", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class NonCibilAccount extends BaseUUID {
    private String sanctionedDate;
    private String typeOfLoan;
    private Long sanctionedAmount;
    private Long outstanding;
    private String bankNbfc;
    private Long emiAmount;
    private Long tenure;
    private String lastPaymentDate;
    private String comment;
    private boolean duplicate;
    private boolean addInTotal;
    private boolean archived;
    private String requestId;
}
