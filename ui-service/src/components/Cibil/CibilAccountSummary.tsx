import React, {useState} from "react";
import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Tooltip,
} from "@mui/material";
import {AccountSummary, CibilReport} from "interfaces/cibil";
import {useTranslation} from "react-i18next";
import {useSearchParams} from "react-router-dom";
import {getCibilDataView} from "utils/api/request";
import {NewBorrowingDetails} from "./NewBorrowingDetails";

const REPORT_ID = "report-id";
export function CibilAccountSummary(props: { accountSummary: AccountSummary }) {
  const [searchParams] = useSearchParams();
  const {t} = useTranslation();
  const reportId = searchParams.get(REPORT_ID) || "";
  const {accountSummary} = props;
  const [newwData, setAllData] = useState<CibilReport | null>(null);
  const [showBorrowingDetails, setshowBorrowingDetails] = useState(false);

  const individualMap = new Map<string, any>(
    Object.entries(accountSummary.individual ?? {}),
  );

  const individualsubcount = individualMap.get("subStandardCount") ?? {};
  const individualsubcountMap = new Map<string, any>(Object.entries(individualsubcount));
  const subStandOpenInd = individualsubcountMap.get("subStandOpen") ?? 0;
  const subStandCloseInd = individualsubcountMap.get("subStandClose") ?? 0;
  const subStandLiveInd = individualsubcountMap.get("subStandLive") ?? 0;

  const individualwilfulCount = individualMap.get("wilfulCount") ?? {};
  const individualwilfulCountMap = new Map<string, any>(Object.entries(individualwilfulCount));
  const wilfulOpenInd = individualwilfulCountMap.get("wilfulOpen") ?? 0;
  const wilfulCloseInd = individualwilfulCountMap.get("wilfulClose") ?? 0;
  const wilfulLiveInd = individualwilfulCountMap.get("wilfulLive") ?? 0;

  const suitFileCountMap = individualMap.get("suitFileCount") ?? {};
  const individualsuitFileCount = new Map<string, any>(Object.entries(suitFileCountMap));
  const suitCountOpenInd = individualsuitFileCount.get("suitCountOpen") ?? 0;
  const suitCountCloseInd = individualsuitFileCount.get("suitCountClose") ?? 0;
  const suitCountLiveInd = individualsuitFileCount.get("suitCountLive") ?? 0;

  const individualwrittenOffCount = individualMap.get("writtenOffCount") ?? {};
  const writtenOffCountMap = new Map<string, any>(Object.entries(individualwrittenOffCount));
  const writtenOffOpenInd = writtenOffCountMap.get("writtenOffOpen") ?? 0;
  const writtenOffCloseInd = writtenOffCountMap.get("writtenOffClose") ?? 0;
  const writtenOffLiveInd = writtenOffCountMap.get("writtenOffLive") ?? 0;

  const settleCountsettleCount = individualMap.get("settleCount") ?? {};
  const settleCountCountMap = new Map<string, any>(Object.entries(settleCountsettleCount));
  const settleCountOpenInd = settleCountCountMap.get("settleCountOpen") ?? 0;
  const settleCountCloseInd = settleCountCountMap.get("settleCountClose") ?? 0;
  const settleCountLiveInd = settleCountCountMap.get("settleCountLive") ?? 0;

  const jointMap = new Map<string, any>(
    Object.entries(accountSummary.joint ?? {}),
  );

  const jointSubcount = jointMap.get("subStandardCount") ?? {};
  const jointSubcountMap = new Map<string, any>(Object.entries(jointSubcount));
  const subStandOpenJoint = jointSubcountMap.get("subStandOpen") ?? 0;
  const subStandCloseJoint = jointSubcountMap.get("subStandClose") ?? 0;
  const subStandLiveJoint = jointSubcountMap.get("subStandLive") ?? 0;

  const jointwilfulCount = jointMap.get("wilfulCount") ?? {};
  const jointWilfulCountMap = new Map<string, any>(Object.entries(jointwilfulCount));
  const wilfulOpenJoint = jointWilfulCountMap.get("wilfulOpen") ?? 0;
  const wilfulCloseJoint = jointWilfulCountMap.get("wilfulClose") ?? 0;
  const wilfulLiveJoint = jointWilfulCountMap.get("wilfulLive") ?? 0;

  const jointsuitFileCount = jointMap.get("suitFileCount") ?? {};
  const jointSuitFileCountMap = new Map<string, any>(Object.entries(jointsuitFileCount));
  const suitCountOpenJoint = jointSuitFileCountMap.get("suitCountOpen") ?? 0;
  const suitCountCloseJoint = jointSuitFileCountMap.get("suitCountClose") ?? 0;
  const suitCountLiveJoint = jointSuitFileCountMap.get("suitCountLive") ?? 0;

  const jointwrittenOffCount = jointMap.get("writtenOffCount") ?? {};
  const jointWrittenOffCountMap = new Map<string, any>(Object.entries(jointwrittenOffCount));
  const writtenOffOpenJoint = jointWrittenOffCountMap.get("writtenOffOpen") ?? 0;
  const writtenOffCloseJoint = jointWrittenOffCountMap.get("writtenOffClose") ?? 0;
  const writtenOffLiveJoint = jointWrittenOffCountMap.get("writtenOffLive") ?? 0;

  const jointsettleCount = jointMap.get("settleCount") ?? {};
  const jointsettleCountMap = new Map<string, any>(Object.entries(jointsettleCount));
  const settleCountOpenJoint = jointsettleCountMap.get("settleCountOpen") ?? 0;
  const settleCountCloseJoint = jointsettleCountMap.get("settleCountClose") ?? 0;
  const settleCountLiveJoint = jointsettleCountMap.get("settleCountLive") ?? 0;

  const guarantorMap = new Map<string, any>(
    Object.entries(accountSummary.guarantor ?? {}),
  );

  const guarantorSubcount = guarantorMap.get("subStandardCount") ?? {};
  const guarantorSubcountMap = new Map<string, any>(Object.entries(guarantorSubcount));
  const subStandOpenGuarantor = guarantorSubcountMap.get("subStandOpen") ?? 0;
  const subStandCloseGuarantor = guarantorSubcountMap.get("subStandClose") ?? 0;
  const subStandLiveGuarantor = guarantorSubcountMap.get("subStandLive") ?? 0;

  const guarantorWilfulCount = guarantorMap.get("wilfulCount") ?? {};
  const guarantorWilfulCountMap = new Map<string, any>(Object.entries(guarantorWilfulCount));
  const wilfulOpenGuarantor = guarantorWilfulCountMap.get("wilfulOpen") ?? 0;
  const wilfulCloseGuarantor = guarantorWilfulCountMap.get("wilfulClose") ?? 0;
  const wilfulLiveGuarantor = guarantorWilfulCountMap.get("wilfulLive") ?? 0;

  const guarantorWrittenOffCount = guarantorMap.get("writtenOffCount") ?? {};
  const guarantorWrittenOffCountMap = new Map<string, any>(Object.entries(guarantorWrittenOffCount));
  const writtenOffOpenGuarantor = guarantorWrittenOffCountMap.get("writtenOffOpen") ?? 0;
  const writtenOffCloseGuarantor = guarantorWrittenOffCountMap.get("writtenOffClose") ?? 0;
  const writtenOffLiveGuarantor = guarantorWrittenOffCountMap.get("writtenOffLive") ?? 0;

  const guarantorSuitFileCount = guarantorMap.get("suitFileCount") ?? {};
  const guarantorSuitFileCountMap = new Map<string, any>(Object.entries(guarantorSuitFileCount));
  const suitCountOpenGuarantor = guarantorSuitFileCountMap.get("suitCountOpen") ?? 0;
  const suitCountCloseGuarantor = guarantorSuitFileCountMap.get("suitCountClose") ?? 0;
  const suitCountLiveGuarantor = guarantorSuitFileCountMap.get("suitCountLive") ?? 0;

  const guarantorsettleCount=guarantorMap.get("settleCount") ?? {};
  const guarantorsettleCountMap=new Map<string, any>(Object.entries(guarantorsettleCount));
  const settleCountOpenguarantor = guarantorsettleCountMap?.get("settleCountOpen")??0;
  const settleCountCloseguarantor = guarantorsettleCountMap?.get("settleCountClose")??0;
  const settleCountLiveguarantor = guarantorsettleCountMap?.get("settleCountLive")??0;

  const totalDPDIndAll = accountSummary.dpd0AllAccountsInd
  + accountSummary.dpd1to30AllAccountsInd
  + accountSummary.dpd31to90AllAccountsInd
  + accountSummary.dpdGreaterThan90AllAccountsInd;

  const totalClosedAccountsDPDInd = accountSummary.dpd0ClosedAccountsInd
  + accountSummary.dpd1to30ClosedAccountsInd
  + accountSummary.dpd31to90ClosedAccountsInd
  + accountSummary.dpdGreaterThan90ClosedAccountsInd;
  const totalLiveAccountsDPDInd = accountSummary.dpd0LiveAccountsInd
                           + accountSummary.dpd1to30LiveAccountsInd
                           + accountSummary.dpd31to90LiveAccountsInd
                           + accountSummary.dpdGreaterThan90LiveAccountsInd;
  const totalJointAllAccountsDPD = accountSummary.dpd0AllAccountsJoint
                           + accountSummary.dpd1to30AllAccountsJoint
                           + accountSummary.dpd31to90AllAccountsJoint
                           + accountSummary.dpdGreaterThan90AllAccountsJoint;

  const totalClosedJointAccountsDPD = accountSummary.dpd0ClosedAccountsJoint
                                  + accountSummary.dpd1to30ClosedAccountsJoint
                                  + accountSummary.dpd31to90ClosedAccountsJoint
                                  + accountSummary.dpdGreaterThan90ClosedAccountsJoint;
  const totalLiveJointAccountsDPD = accountSummary.dpd0LiveAccountsJoint
                                + accountSummary.dpd1to30LiveAccountsJoint
                                + accountSummary.dpd31to90LiveAccountsJoint
                                + accountSummary.dpdGreaterThan90LiveAccountsJoint;
  const totalGuarantorAllAccountsDPD = accountSummary.dpd0AllAccountsGuarantor
                                + accountSummary.dpd1to30AllAccountsGuarantor
                                + accountSummary.dpd31to90AllAccountsGuarantor
                                + accountSummary.dpdGreaterThan90AllAccountsGuarantor;
  const totalClosedGuarantorAccountsDPD = accountSummary.dpd0ClosedAccountsGuarantor
                                      + accountSummary.dpd1to30ClosedAccountsGuarantor
                                      + accountSummary.dpd31to90ClosedAccountsGuarantor
                                      + accountSummary.dpdGreaterThan90ClosedAccountsGuarantor;
  const totalLiveGuarantorAccountsDPD = accountSummary.dpd0LiveAccountsGuarantor
                                    + accountSummary.dpd1to30LiveAccountsGuarantor
                                    + accountSummary.dpd31to90LiveAccountsGuarantor
                                    + accountSummary.dpdGreaterThan90LiveAccountsGuarantor;
  const reportedAllAccounts = accountSummary.smaAllAccounts + accountSummary.dbtAllAccounts +
    accountSummary.subAllAccounts + accountSummary.lssAllAccounts;

  const handleClick = (searchString: string, searchCriteria: string): void => {
    setAllData(null);
    setshowBorrowingDetails(true);
    getCibilDataView(reportId, searchCriteria, searchString, "LoanInformation")
      .then((data) => {
        setAllData(data);
      });

  };

  return (
    <>
      <TableContainer component={Paper}>
        <Table size="small" aria-label="Cibil table">
          <TableHead>
            <TableRow>
              <TableCell align="center">Ownership</TableCell> {/* New Ownership Column */}
              <TableCell align="center">{t("cibil.loanInformation.columns.particulars")}</TableCell>
              <TableCell align="center">{t("cibil.loanInformation.columns.totalAccounts")}</TableCell>
              <TableCell align="center">{t("cibil.loanInformation.rows.dpdRange0")}</TableCell>
              <TableCell align="center">{t("cibil.loanInformation.rows.dpdRange1to30")}</TableCell>
              <TableCell align="center">{t("cibil.loanInformation.rows.dpdRange31to90")}</TableCell>
              <TableCell align="center">{t("cibil.loanInformation.rows.dpdRangeGreaterThan90")}</TableCell>
              <Tooltip title={t("cibil.loanInformation.tooltips.reportedAccounts")} placement="top">
                <TableCell align="center">{t("cibil.loanInformation.rows.reportedAccounts")}</TableCell>
              </Tooltip>
              <TableCell align="center">Sub/DBT Standard</TableCell>
              <TableCell align="center">Wilful Defaulter</TableCell>
              <TableCell align="center">Written Off</TableCell>
              <TableCell align="center">Suit Filed</TableCell>
              <TableCell align="center" style={{maxWidth: "150px", whiteSpace:
                "normal", wordWrap: "break-word"}}> Settlement / Settled / Restructured Loan</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {/* Individual Section */}
            <TableRow>
              <TableCell rowSpan={3} style={{fontWeight: "bold", textAlign: "center"}}>
                Individual
              </TableCell>
              <TableCell>{t("cibil.loanInformation.rows.numberOfLoansOpenedSinceInception")}</TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("DPDIndAll", "dpdInd")}
                >
                  {totalDPDIndAll}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("0DPDAll", "dpdInd")}
                >
                  {accountSummary.dpd0AllAccountsInd}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("1to30DPDAll", "dpdInd")}
                >
                  {accountSummary.dpd1to30AllAccountsInd}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("31to90DPDAll", "dpdInd")}
                >
                  {accountSummary.dpd31to90AllAccountsInd}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("90DPDAll", "dpdInd")}
                >
                  {accountSummary.dpdGreaterThan90AllAccountsInd}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("Reported All Accounts", "report")}
                >
                  {reportedAllAccounts}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("subStandOpenInd", "subOpenInd")}
                >
                  {subStandOpenInd}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("wilfulOpenInd", "statusInd")}
                >
                  {wilfulOpenInd}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("writtenOffOpenInd", "statusInd")}
                >
                  {writtenOffOpenInd}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("suitCountOpenInd", "suitOpenInd")}
                >
                  {suitCountOpenInd}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("settleCountOpenInd", "statusInd")}
                >
                  {settleCountOpenInd}
                </a>
              </TableCell>

            </TableRow>
            <TableRow>
              <TableCell>{t("cibil.loanInformation.rows.numberOfClosedLoansAsOnDate")}</TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("TotalClosedDPD", "dpdInd")}
                >
                  {totalClosedAccountsDPDInd}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("0DPDClosed", "dpdInd")}
                >
                  {accountSummary.dpd0ClosedAccountsInd}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("1to30DPDClosed", "dpdInd")}
                >
                  {accountSummary.dpd1to30ClosedAccountsInd}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("31to90DPDClosed", "dpdInd")}
                >
                  {accountSummary.dpd31to90ClosedAccountsInd}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("90DPDClosed", "dpdInd")}
                >
                  {accountSummary.dpdGreaterThan90ClosedAccountsInd}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("Reported Closed Accounts", "report")}
                >
                  {reportedAllAccounts}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("subStandCloseInd", "subCloseInd")}
                >
                  {subStandCloseInd}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("wilfulCloseInd", "statusInd")}
                >
                  {wilfulCloseInd}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("writtenOffCloseInd", "statusInd")}
                >
                  {writtenOffCloseInd}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("suitCountCloseInd", "suitCloseInd")}
                >
                  {suitCountCloseInd}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("settleCountCloseInd", "statusInd")}
                >
                  {settleCountCloseInd}
                </a>
              </TableCell>

            </TableRow>
            <TableRow>
              <TableCell>{t("cibil.loanInformation.rows.numberOfLiveLoans")}</TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("TotalLiveAccountsDPD", "dpdInd")}
                >
                  {totalLiveAccountsDPDInd}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("0DPDLive", "dpdInd")}
                >
                  {accountSummary.dpd0LiveAccountsInd}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("1to30DPDLive", "dpdInd")}
                >
                  {accountSummary.dpd1to30LiveAccountsInd}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("31to90DPDLive", "dpdInd")}
                >
                  {accountSummary.dpd31to90LiveAccountsInd}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("90DPDLive", "dpdInd")}
                >
                  {accountSummary.dpdGreaterThan90LiveAccountsInd}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("Reported Live Accounts", "report")}
                >
                  {reportedAllAccounts}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("subStandLiveInd ", "subLiveInd")}
                >
                  {subStandLiveInd}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("wilfulLiveInd", "statusInd")}
                >
                  {wilfulLiveInd}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("writtenOffLiveInd ", "statusInd")}
                >
                  {writtenOffLiveInd}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("suitCountLiveInd", "suitLiveInd")}
                >
                  {suitCountLiveInd}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdInd"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("settleCountLiveInd", "statusInd")}
                >
                  {settleCountLiveInd}
                </a>
              </TableCell>

            </TableRow>
            <TableRow>  {/*           Joint           */}
              <TableCell rowSpan={3} style={{fontWeight: "bold", textAlign: "center"}}>
                Joint
              </TableCell>
              <TableCell>{t("cibil.loanInformation.rows.numberOfLoansOpenedSinceInception")}</TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("TotalJointAllDPD", "dpdJoint")}
                >
                  {totalJointAllAccountsDPD}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("0DPDAllJoint", "dpdJoint")}
                >
                  {accountSummary.dpd0AllAccountsJoint}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("1to30AllDPDJoint", "dpdJoint")}
                >
                  {accountSummary.dpd1to30AllAccountsJoint}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("31to90AllDPDJoint", "dpdJoint")}
                >
                  {accountSummary.dpd31to90AllAccountsJoint}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("90DPDAllJoint", "dpdJoint")}
                >
                  {accountSummary.dpdGreaterThan90AllAccountsJoint}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("Reported Joint Accounts", "report")}
                >
                  {reportedAllAccounts}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("subStandOpenJoint", "subOpenJoint")}
                >
                  {subStandOpenJoint}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("wilfulOpenJoint", "statusJoint")}
                >
                  {wilfulOpenJoint}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("writtenOffOpenJoint", "statusJoint")}
                >
                  {writtenOffOpenJoint}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("suitCountOpenJoint", "suitOpenJoint")}
                >
                  {suitCountOpenJoint}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("settleCountOpenJoint", "statusJoint")}
                >
                  {settleCountOpenJoint}
                </a>
              </TableCell>

            </TableRow>
            <TableRow>
              <TableCell>{t("cibil.loanInformation.rows.numberOfClosedLoansAsOnDate")}</TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("TotalClosedJointDPD", "dpdJoint")}
                >
                  {totalClosedJointAccountsDPD}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("0DPDClosedJoint", "dpdJoint")}
                >
                  {accountSummary.dpd0ClosedAccountsJoint}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("1to30DPDClosedJoint", "dpdJoint")}
                >
                  {accountSummary.dpd1to30ClosedAccountsJoint}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("31to90DPDClosedJoint", "dpdJoint")}
                >
                  {accountSummary.dpd31to90ClosedAccountsJoint}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("90DPDClosedJoint", "dpdJoint")}
                >
                  {accountSummary.dpdGreaterThan90ClosedAccountsJoint}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("Reported Closed Joint Accounts", "report")}
                >
                  {reportedAllAccounts}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("subStandCloseJoint", "subCloseJoint")}
                >
                  {subStandCloseJoint}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("wilfulCloseJoint", "statusJoint")}
                >
                  {wilfulCloseJoint}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("writtenOffCloseJoint", "statusJoint")}
                >
                  {writtenOffCloseJoint}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("suitCountCloseJoint", "suitCloseJoint")}
                >
                  {suitCountCloseJoint}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("settleCountCloseJoint", "statusJoint")}
                >
                  {settleCountCloseJoint}
                </a>
              </TableCell>

            </TableRow>
            <TableRow>
              <TableCell>{t("cibil.loanInformation.rows.numberOfLiveLoans")}</TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("TotalLiveJointDPD", "dpdJoint")}
                >
                  {totalLiveJointAccountsDPD}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("0DPDLiveJoint", "dpdJoint")}
                >
                  {accountSummary.dpd0LiveAccountsJoint}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("1to30DPDLiveJoint", "dpdJoint")}
                >
                  {accountSummary.dpd1to30LiveAccountsJoint}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("31to90DPDLiveJoint", "dpdJoint")}
                >
                  {accountSummary.dpd31to90LiveAccountsJoint}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("90DPDLiveJoint", "dpdJoint")}
                >
                  {accountSummary.dpdGreaterThan90LiveAccountsJoint}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("Reported Live Joint Accounts", "report")}
                >
                  {reportedAllAccounts}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("subStandLiveJoint", "subLiveJoint")}
                >
                  {subStandLiveJoint}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("wilfulLiveJoint", "statusJoint")}
                >
                  {wilfulLiveJoint}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("WrittenOffLiveJoint", "statusJoint")}
                >
                  {writtenOffLiveJoint}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("SuitCountLiveJoint", "suitLiveJoint")}
                >
                  {suitCountLiveJoint}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdJoint"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("settleCountLiveJoint", "statusJoint")}
                >
                  {settleCountLiveJoint}
                </a>
              </TableCell>

            </TableRow>

            <TableRow> {/*           Guarantor           */}
              <TableCell rowSpan={3} style={{fontWeight: "bold", textAlign: "center"}}>
                Guarantor
              </TableCell>

              <TableCell>{t("cibil.loanInformation.rows.numberOfLoansOpenedSinceInception")}</TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("TotalGuarantorDPD", "dpdGuarantor")}
                >
                  {totalGuarantorAllAccountsDPD}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("0DPDAllGuarantor", "dpdGuarantor")}
                >
                  {accountSummary.dpd0AllAccountsGuarantor}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("1to30DPDAllGuarantor", "dpdGuarantor")}
                >
                  {accountSummary.dpd1to30AllAccountsGuarantor}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("31to90DPDAllGuarantor", "dpdGuarantor")}
                >
                  {accountSummary.dpd31to90AllAccountsGuarantor}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("90DPDAllGuarantor", "dpdGuarantor")}
                >
                  {accountSummary.dpdGreaterThan90AllAccountsGuarantor}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("Reported Guarantor Accounts", "report")}
                >
                  {reportedAllAccounts}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("subStandOpenGuarantor", "subOpenGuarantor")}
                >
                  {subStandOpenGuarantor}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("WilfulOpenGuarantor", "statusGuarantor")}
                >
                  {wilfulOpenGuarantor}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("WrittenOffOpenGuarantor", "statusGuarantor")}
                >
                  {writtenOffOpenGuarantor}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("suitCountOpenGuarantor", "suitOpenGuarantor")}
                >
                  {suitCountOpenGuarantor}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("settleCountOpenguarantor", "statusGuarantor")}
                >
                  {settleCountOpenguarantor}
                </a>
              </TableCell>

            </TableRow>
            <TableRow>
              <TableCell>{t("cibil.loanInformation.rows.numberOfClosedLoansAsOnDate")}</TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("TotalClosedGuarantorDPD", "dpdGuarantor")}
                >
                  {totalClosedGuarantorAccountsDPD}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("0ClosedDPDGuarantor", "dpdGuarantor")}
                >
                  {accountSummary.dpd0ClosedAccountsGuarantor}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("1to30ClosedDPDGuarantor", "dpdGuarantor")}
                >
                  {accountSummary.dpd1to30ClosedAccountsGuarantor}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("31to90ClosedDPDGuarantor", "dpdGuarantor")}
                >
                  {accountSummary.dpd31to90ClosedAccountsGuarantor}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("90ClosedDPDGuarantor", "dpdGuarantor")}
                >
                  {accountSummary.dpdGreaterThan90ClosedAccountsGuarantor}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("Reported Closed Guarantor Accounts", "report")}
                >
                  {reportedAllAccounts}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("subStandCloseGuarantor", "subCloseGuarantor")}
                >
                  {subStandCloseGuarantor}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("wilfulCloseGuarantor", "statusGuarantor")}
                >
                  {wilfulCloseGuarantor}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("writtenOffCloseGuarantor", "statusGuarantor")}
                >
                  {writtenOffCloseGuarantor}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("suitCountCloseGuarantor", "suitCloseGuarantor")}
                >
                  {suitCountCloseGuarantor}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("settleCountCloseguarantor", "statusGuarantor")}
                >
                  {settleCountCloseguarantor}
                </a>
              </TableCell>

            </TableRow>
            <TableRow>
              <TableCell>{t("cibil.loanInformation.rows.numberOfLiveLoans")}</TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("TotalLiveGuarantorDPD", "dpdGuarantor")}
                >
                  {totalLiveGuarantorAccountsDPD}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("0LiveDPDGuarantor", "dpdGuarantor")}
                >
                  {accountSummary.dpd0LiveAccountsGuarantor}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("1to30LiveDPDGuarantor", "dpdGuarantor")}
                >
                  {accountSummary.dpd1to30LiveAccountsGuarantor}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("31to90LiveDPDGuarantor", "dpdGuarantor")}
                >
                  {accountSummary.dpd31to90LiveAccountsGuarantor}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("90LiveDPDGuarantor", "dpdGuarantor")}
                >
                  {accountSummary.dpdGreaterThan90LiveAccountsGuarantor}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("Reported Live Guarantor Accounts", "report")}
                >
                  {reportedAllAccounts}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("subStandLiveGuarantor ", "subLiveGuarantor")}
                >
                  {subStandLiveGuarantor}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("wilfulLiveGuarantor", "statusGuarantor")}
                >
                  {wilfulLiveGuarantor}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("WrittenOffLiveGuarantor", "statusGuarantor")}
                >
                  {writtenOffLiveGuarantor}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("SuitCountLiveGuarantor", "suitLiveGuarantor")}
                >
                  {suitCountLiveGuarantor}
                </a>
              </TableCell>
              <TableCell align="center">
                <a
                  href="#dpdGuarantor"
                  style={{textDecoration: "none"}}
                  onClick={() => handleClick("settleCountLiveguarantor", "statusGuarantor")}
                >
                  {settleCountLiveguarantor}
                </a>
              </TableCell>

            </TableRow>
            <TableRow>
              <TableCell>{t("cibil.loanInformation.rows.sanctionedAmountOfLiveLoans")}</TableCell>
              <TableCell align="center">{accountSummary.sanctionedAmountLiveLoans}</TableCell>
            </TableRow>
            <TableRow>
              <TableCell>{t("cibil.loanInformation.rows.posOfLiveLoans")}</TableCell>
              <TableCell align="center">{accountSummary.posLiveLoans}</TableCell>
            </TableRow>
            <TableRow>
              <TableCell>{t("cibil.loanInformation.rows.overdueAmountInLiveLoans")}</TableCell>
              <TableCell align="center">{accountSummary.overdueAmountLiveLoans}</TableCell>
            </TableRow>
          </TableBody>
        </Table>
      </TableContainer>

      {/* Borrowing Details Section */}
      {showBorrowingDetails && newwData?.accountBorrowingDetailsDTOList && (
        <>
          <div
            className="borrowing-details-header"
            style={{
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
              position: "relative",
            }}
          >
            <h2 style={{margin: 0}}>Borrowing Details</h2>
            <button
              onClick={() => setshowBorrowingDetails(false)}
              style={{
                position: "absolute",
                right: "10px",
                background: "none",
                border: "none",
                fontSize: "20px",
                cursor: "pointer",
                color: "#000",
              }}
            >
              ✕
            </button>
          </div>
          <NewBorrowingDetails borrowingDetails={newwData.accountBorrowingDetailsDTOList} />
        </>
      )}

    </>
  );
}
