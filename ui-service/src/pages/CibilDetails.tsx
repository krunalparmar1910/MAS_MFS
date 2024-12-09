import React, {useEffect, useState} from "react";
import {ExpandMore} from "@mui/icons-material";
import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Card,
  CircularProgress,
  Table,
  TableCell,
  TableHead,
  TableRow,
  Typography,
} from "@mui/material";
import {AxiosError} from "axios";
import {CibilAccountSummary} from "components/Cibil/CibilAccountSummary";
import {CibilInquiriesTable} from "components/Cibil/CibilInquiriesTable";
import {CibilNewEntryTable} from "components/Cibil/CibilNewEntryTable";
import CibilPersonalDetail from "components/Cibil/CibilPersonalDetails";
import {BorrowingDetailAmountsDTO, CibilReport, NewLoanEntryDetails} from "interfaces/cibil";
import {ErrorPage} from "pages/errorPage";

import {useTranslation} from "react-i18next";
import {useSearchParams} from "react-router-dom";
import {ErrorResponse, getCibilData, getNonCibilData} from "utils/api/request";
import TableRowsLoader from "utils/components/TableRowsLoader";
import {CibilBorrowingDetailsTable} from "components/Cibil/CibilBorrowingDetailsTable";
import {VigilantSummary} from "components/Cibil/VigilantSummary";
import CibilAdditionalMatches from "components/Cibil/CibilAdditionalMatches";

const REPORT_ID = "report-id";

export const CibilDetails = () => {
  const [allData, setAllData] = useState<CibilReport>();
  const [loading, setLoading] = useState(false);
  const [nonCibilData, setNonCibilData] = useState<NewLoanEntryDetails[]>([]);
  const [error, setError] = useState<AxiosError<ErrorResponse> | null>(null);
  const {t} = useTranslation();
  const [searchParams] = useSearchParams();
  const reportId = searchParams.get(REPORT_ID) || "";

  const [totalSanctionedFromApi, setTotalSanctionedFromApi] = useState(0);
  const [totalOutstandingFromApi, setTotalOutstandingFromApi] = useState(0);
  const [totalEmiFromApi, setTotalEmiFromApi] = useState(0);
  const [totalEmiSystem, setTotalEmiSystem] = useState(0);
  const fetchCibilData = () => {
    setLoading(true);
    getCibilData(reportId)
      .then((data) => {
        setAllData(data);
        setError(null);
      })
      .catch((error: any) => setError(error))
      .finally(()=>setLoading(false));
  };

  const fetchNonCibilData = () => {
    setLoading(true);
    getNonCibilData(reportId)
      .then((data) => {
        setNonCibilData(data);
        setError(null);
      })
      .catch((error: any) => setError(error))
      .finally(()=>setLoading(false));
  };

  const updateTotals = () => {
    let sanctionedAmtCibil = 0;
    let sanctionedAmtNonCibil = 0;
    let outstandingAmtCibil = 0;
    let outstandingAmtNonCibil = 0;
    let totalEmiCibil = 0;
    let totalEmiNonCibil = 0;
    let totalEmiSystem = 0;

    allData?.accountBorrowingDetailsDTOList?.forEach((borrowinfDetail) => {
      if (!borrowinfDetail.duplicate && borrowinfDetail.addInTotal) {
        sanctionedAmtCibil += borrowinfDetail.sanctionedAmount;
        outstandingAmtCibil += borrowinfDetail.outstandingAmount;
        totalEmiCibil += borrowinfDetail.emi;
        totalEmiSystem += borrowinfDetail.emiSystem || 0;
      }
    });

    nonCibilData?.forEach((data: NewLoanEntryDetails) => {
      if (!data.duplicate && data.addInTotal) {
        sanctionedAmtNonCibil += data.sanctionedAmount;
        outstandingAmtNonCibil += data.outstanding;
        totalEmiNonCibil += data.emiAmount;
        totalEmiSystem += data.emiSystem || 0;
      }
    });

    setTotalSanctionedFromApi(sanctionedAmtCibil + sanctionedAmtNonCibil);
    setTotalOutstandingFromApi(outstandingAmtCibil + outstandingAmtNonCibil);
    setTotalEmiFromApi(totalEmiCibil + totalEmiNonCibil);
    setTotalEmiSystem(totalEmiSystem);
  };

  useEffect(() => {
    fetchCibilData();
    fetchNonCibilData();
  }, []);

  useEffect(() => {
    updateTotals();
  }, [allData, nonCibilData]);

  const onUpdateRow = () => {
    fetchCibilData();
  };

  const onUpdateNonCibilRow = () => {
    fetchNonCibilData();
  };

  const onCheckBoxUpdate = (row: BorrowingDetailAmountsDTO, columnField: string, value: boolean) => {
    if ((columnField === "duplicate" && row.addInTotal && value) ||
  (columnField === "addInTotal" && !row.duplicate && !value)) {
      setTotalSanctionedFromApi(totalSanctionedFromApi - row.sanctionedAmount);
      setTotalOutstandingFromApi(totalOutstandingFromApi - row.outstandingAmount);
      setTotalEmiFromApi(totalEmiFromApi - row.emi);
      setTotalEmiSystem(totalEmiSystem - row.emiSystem);
    } else if ((columnField === "addInTotal" && !row.duplicate && value) ||
    (columnField === "duplicate" && row.addInTotal && !value)) {
      setTotalSanctionedFromApi(totalSanctionedFromApi + row.sanctionedAmount);
      setTotalOutstandingFromApi(totalOutstandingFromApi + row.outstandingAmount);
      setTotalEmiFromApi(totalEmiFromApi + row.emi);
      setTotalEmiSystem(totalEmiSystem - row.emiSystem);
    }
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
      {!error &&
      <>
        <Table className="mv-10" aria-label="customized table">
          <TableHead>
            <TableRow>
              <TableCell align="center">{t("cibil.applicant")}</TableCell>
              <TableCell align="center">{t("cibil.age")}</TableCell>
              <TableCell align="center">{t("cibil.score")}</TableCell>
              <TableCell align="center">{t("cibil.controlNo")}</TableCell>
            </TableRow>
            {loading?
              <TableRowsLoader rowsNum={2} colsNum={4}/>:
              <TableRow>
                <TableCell align="center">{allData?.name}</TableCell>
                <TableCell align="center">{allData?.age}</TableCell>
                <TableCell align="center">{allData?.score}</TableCell>
                <TableCell align="center">{allData?.controlNumber}</TableCell>
              </TableRow>
            }
          </TableHead>
        </Table>
        <Accordion defaultExpanded={loading}>
          <AccordionSummary
            expandIcon={loading?<CircularProgress size="1.5rem"/>:<ExpandMore color="primary" />}
            aria-controls="panel1-content"
            id="panel1-header"
          >
            <Typography variant="h5">{t("cibil.personalDetails.personalDetails")}</Typography>
          </AccordionSummary>
          <AccordionDetails>
            <CibilPersonalDetail allData={allData} />
          </AccordionDetails>
        </Accordion>
        <Accordion defaultExpanded={loading}>
          <AccordionSummary
            expandIcon={loading?<CircularProgress size="1.5rem"/>:<ExpandMore color="primary" />}
            aria-controls="panel1-content"
            id="panel1-header"
          >
            <Typography variant="h5">{t("cibil.loanInformation.loanInformation")}</Typography>
          </AccordionSummary>
          <AccordionDetails>
            {allData?.accountSummary !== undefined &&
            <CibilAccountSummary accountSummary={allData.accountSummary} />
            }
          </AccordionDetails>
        </Accordion>
        <Accordion defaultExpanded={loading}>
          <AccordionSummary
            expandIcon={loading?<CircularProgress size="1.5rem"/>:<ExpandMore color="primary" />}
            aria-controls="panel1-content"
            id="panel1-header"
          >
            <Typography variant="h5">Vigilant Summary</Typography>
          </AccordionSummary>
          <AccordionDetails>
            {allData?.accountSummary !== undefined &&
            <VigilantSummary accountSummary={allData.accountSummary} />
            }
          </AccordionDetails>
        </Accordion>
        <Accordion defaultExpanded={loading}>
          <AccordionSummary
            expandIcon={loading?<CircularProgress size="1.5rem"/>:<ExpandMore color="primary" />}
            aria-controls="panel1-content"
            id="panel1-header"
          >
            <Typography variant="h5">{t("cibil.inquiries.inquiries")}</Typography>
          </AccordionSummary>
          <AccordionDetails>
            {allData?.accountInquiriesHistoricalData !== undefined &&
            <CibilInquiriesTable accountInquiriesHistoricalData={allData?.accountInquiriesHistoricalData} />
            }
          </AccordionDetails>
        </Accordion>
        <Accordion defaultExpanded={loading}>
          <AccordionSummary
            expandIcon={loading?<CircularProgress size="1.5rem"/>:<ExpandMore color="primary" />}
            aria-controls="panel1-content"
            id="panel1-header"
          >
            <Typography variant="h5">{t("cibil.borrowingDetails.borrowingDetails")}</Typography>
          </AccordionSummary>
          <AccordionDetails>
            {allData?.accountBorrowingDetailsDTOList &&
            <>
              <CibilBorrowingDetailsTable
                onCheckBoxUpdate={onCheckBoxUpdate}
                onUpdateRow={onUpdateRow}
                borrowingDetails={allData.accountBorrowingDetailsDTOList}
              />
            </>
            }
            <h3 style={{textAlign: "center"}}>ADDITIONAL DETAILS</h3>
            <CibilAdditionalMatches allData={allData} />
            <CibilNewEntryTable
              requestId={reportId}
              nonCibilData={nonCibilData}
              onUpdateRow={onUpdateNonCibilRow}
              onCheckBoxUpdate={onCheckBoxUpdate}
            />
          </AccordionDetails>
        </Accordion>
        <div className="flex-row-justify-between mv-10">
          <Card className="flex-49 flex-row-justify-center" raised>
            <Typography>{`${t("cibil.totalSanctioned")}`}</Typography>
            <Typography variant="h5" component="div">
              {Intl.NumberFormat("en-IN").format(totalSanctionedFromApi)}
            </Typography>
          </Card>
          <Card className="flex-49 flex-row-justify-center" raised>
            <Typography>{t("cibil.totalOutstanding")}</Typography>
            <Typography variant="h5" component="div">
              {Intl.NumberFormat("en-IN").format(totalOutstandingFromApi)}
            </Typography>
          </Card>
          <Card className="flex-49 flex-row-justify-center" raised>
            <Typography>{t("cibil.emi")}</Typography>
            <Typography variant="h5" component="div">
              {Intl.NumberFormat("en-IN").format(totalEmiFromApi)}
            </Typography>
          </Card>
          <Card className="flex-49 flex-row-justify-center" raised>
            <Typography>{t("cibil.emiSystem")}</Typography>
            <Typography variant="h5" component="div">
              {Intl.NumberFormat("en-IN").format(totalEmiSystem)}
            </Typography>
          </Card>
        </div>
        <br />
      </>
      }
    </>
  );
};
