import React, {useState} from "react";
import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import {AccountSummary, CibilReport} from "interfaces/cibil";
import {useSearchParams} from "react-router-dom";
import {AxiosError} from "axios";
import {ErrorResponse,getCibilDataView} from "utils/api/request";
import {ErrorPage} from "pages/errorPage";
import {NewBorrowingDetails} from "./NewBorrowingDetails";
import "styles/cibil.scss";

const REPORT_ID = "report-id";

export function VigilantSummary(props: { accountSummary: AccountSummary }) {
  const [searchParams] = useSearchParams();
  const [newwData, setAllData] = useState<CibilReport | null>(null);
  const [error, setError] = useState<AxiosError<ErrorResponse> | null>(null);
  const [showBorrowingDetails, setshowBorrowingDetails] = useState(false);

  const reportId = searchParams.get(REPORT_ID) || "";
  const {accountSummary} = props;
  const individualMap = new Map<string, any>(
    Object.entries(accountSummary.individual ?? {}),
  );

  const individualWilfulCount = individualMap.get("wilfulCount") ?? {};
  const individualWilfulCountMap = new Map<string, any>(Object.entries(individualWilfulCount));
  const wilfulOpenInd = individualWilfulCountMap.get("wilfulOpen") ?? 0;

  const suitFileCountMap = individualMap.get("suitFileCount") ?? {};
  const individualSuitFileCount = new Map<string, any>(Object.entries(suitFileCountMap));
  const suitCountOpenInd = individualSuitFileCount.get("suitCountOpen") ?? 0;

  const individualWrittenOffCount = individualMap.get("writtenOffCount") ?? {};
  const writtenOffCountMap = new Map<string, any>(Object.entries(individualWrittenOffCount));
  const writtenOffOpenInd = writtenOffCountMap.get("writtenOffOpen") ?? 0;

  const jointMap = new Map<string, any>(
    Object.entries(accountSummary.joint ?? {}),
  );

  const jointWilfulCount = jointMap.get("wilfulCount") ?? {};
  const jointWilfulCountMap = new Map<string, any>(Object.entries(jointWilfulCount));
  const wilfulOpenJoint = jointWilfulCountMap.get("wilfulOpen") ?? 0;

  const jointSuitFileCount = jointMap.get("suitFileCount") ?? {};
  const jointSuitFileCountMap = new Map<string, any>(Object.entries(jointSuitFileCount));
  const suitCountOpenJoint = jointSuitFileCountMap.get("suitCountOpen") ?? 0;

  const jointWrittenOffCount = jointMap.get("writtenOffCount") ?? {};
  const jointWrittenOffCountMap = new Map<string, any>(Object.entries(jointWrittenOffCount));
  const writtenOffOpenJoint = jointWrittenOffCountMap.get("writtenOffOpen") ?? 0;

  const guarantorMap = new Map<string, any>(
    Object.entries(accountSummary.guarantor ?? {}),
  );

  const guarantorWilfulCount = guarantorMap.get("wilfulCount") ?? {};
  const guarantorWilfulCountMap = new Map<string, any>(Object.entries(guarantorWilfulCount));
  const wilfulOpenGuarantor = guarantorWilfulCountMap.get("wilfulOpen") ?? 0;

  const guarantorWrittenOffCount = guarantorMap.get("writtenOffCount") ?? {};
  const guarantorWrittenOffCountMap = new Map<string, any>(Object.entries(guarantorWrittenOffCount));
  const writtenOffOpenGuarantor = guarantorWrittenOffCountMap.get("writtenOffOpen") ?? 0;

  const guarantorSuitFileCount = guarantorMap.get("suitFileCount") ?? {};
  const guarantorSuitFileCountMap = new Map<string, any>(Object.entries(guarantorSuitFileCount));
  const suitCountOpenGuarantor = guarantorSuitFileCountMap.get("suitCountOpen") ?? 0;

  const Overdue=(accountSummary.dpd1to30AllAccountsInd||0)
                      +(accountSummary.dpd31to90AllAccountsInd||0)
                      +(accountSummary.dpdGreaterThan90AllAccountsInd||0)
                      +(accountSummary.dpd1to30AllAccountsJoint||0)
                      +(accountSummary.dpd31to90AllAccountsJoint||0)
                      +(accountSummary.dpdGreaterThan90AllAccountsJoint||0)
                      +(accountSummary.dpd1to30AllAccountsGuarantor||0)
                      +(accountSummary.dpd31to90AllAccountsGuarantor||0)
                      +(accountSummary.dpdGreaterThan90AllAccountsGuarantor);
  const totalWilful = (wilfulOpenGuarantor || 0)
                   + (wilfulOpenJoint || 0)
                   + (wilfulOpenInd || 0);
  const totalWrittenOff = (writtenOffOpenGuarantor || 0)
                      + (writtenOffOpenJoint || 0)
                      + (writtenOffOpenInd || 0);
  const totalSuitCount = (suitCountOpenGuarantor || 0)
                      + (suitCountOpenJoint || 0)
                      + (suitCountOpenInd || 0);

  const handleClick = (searchString: string,searchCriteria :string): void => {
    setAllData(null);
    setshowBorrowingDetails(true);
    getCibilDataView(reportId,searchCriteria,searchString,"VigilantSummery")
      .then((data)=>{
        setAllData(data);
        setError(null);
      }).catch((error: any) => setError(error));
  };
  return (
    <>
      {(error?.response?.status === 404) &&
        <ErrorPage
          errorMessage="errorPage.errorMessageNotFound"
          errorPoints={["errorPage.reportNotReadyForEntity", "errorPage.reportDetailsIncorrect"]}
          header="errorPage.errorMessageNotFoundHeader"
        />
      }
      {(error?.response?.status === 400) &&
        <ErrorPage
          errorMessage="errorPage.badRequest"
          errorPoints={[error?.response?.data?.message || ""]}
          header="errorPage.badRequestHeader"
        />
      }
      <TableContainer component={Paper}>
        <Table size="small" aria-label="Cibil table">
          <TableHead>
            <TableRow>
              <TableCell colSpan={9} align="center">Status</TableCell>
            </TableRow>
            <TableRow>
              <TableCell rowSpan={3} align="center"></TableCell>
              <TableCell align="center">Overdue</TableCell>
              <TableCell align="center">Sub-Standard</TableCell>
              <TableCell align="center">Doubtful Debt</TableCell>
              <TableCell align="center">Wilful Defaulter</TableCell>
              <TableCell align="center">Written Off</TableCell>
              <TableCell align="center">Suit File</TableCell>
              <TableCell align="center">Settlement/Settled</TableCell>
              <TableCell align="center">Restructured Loan</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            <TableRow>
              <TableCell rowSpan={3} align="center">Loan Accounts</TableCell>
              <TableCell align="center">
                <a href="#total" style={{textDecoration: "none"}} onClick={() =>
                  handleClick("alloverdue", "1to90plus")}>
                  {Overdue}
                </a>
              </TableCell>
              <TableCell align="center">
                <a href="#SUB" style={{textDecoration: "none"}} onClick={() => handleClick("SUB", "SUB")}>
                  {accountSummary.subStd}
                </a>
              </TableCell>
              <TableCell align="center">
                <a href="#DBT" style={{textDecoration: "none"}} onClick={() => handleClick("DBT", "DBT")}>
                  {accountSummary.dubDebt}
                </a>
              </TableCell>
              <TableCell align="center">
                <a href="#status" style={{textDecoration: "none"}} onClick={() =>
                  handleClick("Wilful Defaulter", "status")}>
                  {totalWilful}
                </a>
              </TableCell>
              <TableCell align="center">
                <a href="#status" style={{textDecoration: "none"}} onClick={() => handleClick("Witten Off", "status")}>
                  {totalWrittenOff}
                </a>
              </TableCell>
              <TableCell align="center">
                <a href="#Suit" style={{textDecoration: "none"}} onClick={() => handleClick("Suit filed", "Suit")}>
                  {totalSuitCount}
                </a>
              </TableCell>
              <TableCell align="center">
                <a href="#status" style={{textDecoration: "none"}} onClick={() => handleClick("Settlement/Settled",
                  "status")}>
                  {accountSummary.settled}
                </a>
              </TableCell>
              <TableCell align="center">
                <a href="#status" style={{textDecoration: "none"}} onClick={() =>
                  handleClick("Resturctured Loan", "status")}>
                  {accountSummary.resturctured}
                </a>
              </TableCell>
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
