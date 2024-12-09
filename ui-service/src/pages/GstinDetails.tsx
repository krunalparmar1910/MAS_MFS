import React, {useEffect, useState} from "react";
import {useSearchParams} from "react-router-dom";
import {ProfileDetails} from "components/GST/ProfileDetails";
import SalesTable from "components/GST/SalesTable";
import {GSTReport, GstReportProfileDetail} from "interfaces/gst";
import {ErrorResponse, getGstSalesData} from "utils/api/request";
import {ErrorPage} from "pages/errorPage";
import {AxiosError} from "axios";
import {Accordion, AccordionDetails, AccordionSummary, CircularProgress, Typography} from "@mui/material";
import {ExpandMore} from "@mui/icons-material";
import {useTranslation} from "react-i18next";
import {CLIENT_ORDER_IDS_PARAM, ENTITY_ID_PARAM} from "utils/constants/constants";

export function GstinDetails() {
  const [allData, setAllData] = useState<GSTReport>();
  const [error, setError] = useState<AxiosError<ErrorResponse> | null>(null);
  const [loading, setLoading] = useState(false);
  const [consolidateCheck, setConsolidateCheck] = useState<Map<string, boolean>>(new Map());
  const [consolidateCheckCnt, setConsolidateCheckCnt] = useState(0);
  const [searchParams] = useSearchParams();
  const entityId = searchParams.get(ENTITY_ID_PARAM) || "";
  const clientOrderIds = searchParams.get(CLIENT_ORDER_IDS_PARAM) || "";
  const {t} = useTranslation();

  useEffect(() => {
    setLoading(true);
    getGstSalesData(entityId, clientOrderIds)
      .then((data) => {
        setAllData(data);
        const tempMap = new Map();
        data.gstReportProfileDetails.forEach((profileDetails: GstReportProfileDetail) => {
          tempMap.set(profileDetails.gstNumber, false);
        });
        setConsolidateCheck(tempMap);
        setError(null);
      })
      .catch((error) => setError(error))
      .finally(()=>setLoading(false));
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  useEffect(() => {
    let cnt = 0;
    consolidateCheck.forEach((value) => {
      if (value === true) {
        cnt++;
      }
    });
    setConsolidateCheckCnt(cnt);
  }, [consolidateCheck]);

  return (
    <React.Fragment>
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
      <React.Fragment>
        <ProfileDetails
          consolidateCheck={consolidateCheck}
          setConsolidateCheck={setConsolidateCheck}
          allData={allData}
          loading={loading}
        />
        {allData?.gst3BSalesReports && Object.keys(allData?.gst3BSalesReports).length > 0 &&
        <Accordion>
          <AccordionSummary
            expandIcon={loading?<CircularProgress size="1.5rem"/>:<ExpandMore color="primary" />}
            aria-controls="panel1-content"
            id="panel1-header"
          >
            <Typography variant="h5">{t("gstinDetails.individualReports")}</Typography>
          </AccordionSummary>
          <AccordionDetails>
            {Object.entries(allData.gst3BSalesReports).map(([key, value]) => {
              return (
                <Accordion key={key}>
                  <AccordionSummary
                    expandIcon={<ExpandMore color="primary" />}
                    aria-controls="panel1-content"
                    id="panel1-header"
                  >
                    <Typography variant="h6">{value.tradeName} - {key}</Typography>
                  </AccordionSummary>
                  <AccordionDetails>
                    <SalesTable
                      allData={allData}
                      entityId={entityId}
                      consolidateCheck={consolidateCheck}
                      displayConsolidatedReport={false}
                      displayIndividualReport={true}
                      gstNo={key}

                      key={key}
                      individualReport={value}
                    />
                  </AccordionDetails>
                </Accordion>
              );
            })}
          </AccordionDetails>
        </Accordion>}
        {consolidateCheckCnt >= 2 &&
        <Accordion>
          <AccordionSummary
            expandIcon={<ExpandMore color="primary" />}
            aria-controls="panel1-content"
            id="panel1-header"
          >
            <Typography variant="h5">{t("gstinDetails.consolidatedReport")}</Typography>
          </AccordionSummary>
          <AccordionDetails>
            <SalesTable
              allData={allData}
              entityId={entityId}
              consolidateCheck={consolidateCheck}
              displayConsolidatedReport={false}
              displayIndividualReport={false}
            />
          </AccordionDetails>
        </Accordion>}
        {entityId &&
        <Accordion>
          <AccordionSummary
            expandIcon={loading?<CircularProgress size="1.5rem"/>:<ExpandMore color="primary" />}
            aria-controls="panel1-content"
            id="panel1-header"
          >
            <Typography variant="h5">{t("gstinDetails.corpositoryConsolidatedReport")}</Typography>
          </AccordionSummary>
          <AccordionDetails>
            <SalesTable
              entityId={entityId}
              allData={allData}
              consolidateCheck={consolidateCheck}
              displayConsolidatedReport={true}
              displayIndividualReport={false}
            />
          </AccordionDetails>
        </Accordion>
        }
      </React.Fragment>}
    </React.Fragment>
  );
}
