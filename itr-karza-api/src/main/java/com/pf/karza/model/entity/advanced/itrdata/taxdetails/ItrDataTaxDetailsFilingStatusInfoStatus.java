package com.pf.karza.model.entity.advanced.itrdata.taxdetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pf.karza.constant.DbConstants;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "itr_data_tax_details_filing_status_info_status", schema = DbConstants.ITR_SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ItrDataTaxDetailsFilingStatusInfoStatus extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_tax_details_filing_status_info_id")
    @JsonBackReference
    private ItrDataTaxDetailsFilingStatusInfo itrDataTaxDetailsFilingStatusInfo;

    private String wrdOrCrcl;

    private String retSec;

    private String ackOrgnlRtn;

    private String ntcNo;

    private String ntcDt;

    private String rtrnTyp;

    private String rcptNo;

    private String orgRtnFilingDt;

    private String cndtnResStts;

    private String sec115BA;

    private String grossReceipt;

    private String rsdntSec90;

    private String rgstrtdLaw;

    private String finStmtFlag;

    private String isIFSC;

    private String nri_PE;

    private String fiiFPIFlag;

    private String sebiRegnNo;

    private String sec581AFlag;

    private String sevnthProv139;

    private String depAmtAggAmtExcd1CrPrYrFlg;

    private String amtSevnthProvi139i;

    private String incrExpAggAmt2LkTrvFrgnCntryFlg;

    private String amtSevnthProvisio139ii;

    private String incrExpAggAmt1LkElctrctyPrYrFlg;

    private String amtSeventhProvisio139iii;

    private String bnftUs115H;

    private String prtgseCivilCd;

    private String whthrBusTrst;

    private String invstmntFundRefrdSec115UB;
}
