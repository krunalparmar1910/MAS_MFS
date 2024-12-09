package com.pf.perfios.model.entity;

import com.pf.perfios.utils.DbConst;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "custom_total_Fields",schema = DbConst.SCHEMA_NAME)
public class CustomTotalFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long customCreditLoanReceipts;
    private long customDebitLoanReceipts;
    private long totalCreditInterBank;
    private long totalDebitInterBank;
    private long totalCreditInterFirm;
    private long totalDebitInterFirm;
    private long totalCreditNonBusinessTransaction;
    private long totalDebitNonBusinessTransaction;
    private String masFinId;
}
