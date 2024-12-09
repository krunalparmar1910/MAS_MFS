import {Accordion, AccordionDetails, AccordionSummary, CircularProgress, Tab, Tabs, Typography} from "@mui/material";
import {AxiosError} from "axios";
import {BankAccountDetails} from "components/Banking/BankAccountDetails";
import {BankDetailDTO, MonthlyDetailsDTO} from "interfaces/banking";
import React, {useEffect, useState} from "react";
import {useSearchParams} from "react-router-dom";
import {ErrorResponse, getBankingData} from "utils/api/request";
import {BankStatementTable} from "components/Banking/BankStatementTable";
import CustomTabPanel from "utils/components/CustomTabPanel";
import {ErrorPage} from "./errorPage";
import {ExpandMore} from "@mui/icons-material";
import {Variant} from "@mui/material/styles/createTypography";
import {useTranslation} from "react-i18next";
import {BankTxnTable} from "components/Banking/BankTxnTable";

const MAS_FIN_ID_PARAM = "mas-fin-id";
const CUSTOMER_TXN_IDS_PARAM = "customer-txn-ids";
const UNIQUE_FRIM_ID = "uniqueFirmId";

function a11yProps(index: number) {
  return {
    id: `simple-tab-${index}`,
    "aria-controls": `simple-tabpanel-${index}`,
  };
}

export interface ConsolidateObject {
  consolidate: boolean,
  customerTransactionId: string
}

export function Banking() {
  const [allData, setAllData] = useState<MonthlyDetailsDTO>();
  const [error, setError] = useState<AxiosError<ErrorResponse> | null>(null);
  const [consolidateCheck, setConsolidateCheck] =
useState<Map<string, ConsolidateObject>>(new Map<string, ConsolidateObject>()); //key: masFinId+perfiosTxnId+accNo
  const [consolidateCheckCount, setConsolidateCheckCount] = useState(0);
  const {t} = useTranslation();
  const [individualStatementTabs, setIndividualStatementTabs] = useState<number[]>([]);
  const [consolidatedStatementTab, setConsolidatedStatementTab] = useState(0);
  const [searchParams] = useSearchParams();
  const [loading, setLoading] = useState(false);
  const masFinId = searchParams.get(MAS_FIN_ID_PARAM) || "";
  const uniqueFirmId = searchParams.get(UNIQUE_FRIM_ID) || "";
  const customerTxnIds = searchParams.get(CUSTOMER_TXN_IDS_PARAM)?.split(",") || [];

  const updateConsolidatedAccounts = (bankAccountDetails: BankDetailDTO[]) =>{
    const consolidatedMap: Map<string, ConsolidateObject> = new Map<string, ConsolidateObject>();
    bankAccountDetails.forEach((bankDetail) => {
      consolidatedMap.set(getConsolidateCheckMapKey(bankDetail),
        {
          consolidate: consolidateCheck.get(getConsolidateCheckMapKey(bankDetail))?.consolidate || false,
          customerTransactionId: bankDetail.customerTransactionId,
        });
    });
    if (bankAccountDetails.length > 0) {
      consolidatedMap.set(getConsolidateCheckMapKey(bankAccountDetails[0]),
        {
          consolidate: consolidateCheck.get(getConsolidateCheckMapKey(bankAccountDetails[0]))?.consolidate || true,
          customerTransactionId: bankAccountDetails[0].customerTransactionId,
        });
    }
    setConsolidateCheck(consolidatedMap);
  };

  const fetchBankingData = () => {
    setLoading(true);
    getBankingData(masFinId,uniqueFirmId, customerTxnIds)
      .then((data) => {
        setAllData(data.data);
        setIndividualStatementTabs(Array(data?.data?.bankDetailList?.length || 0).fill(0));
        updateConsolidatedAccounts(data.data.bankDetailList);
        setError(null);
      })
      .catch((error) => setError(error))
      .finally(()=>setLoading(false));
  };

  useEffect(() => {
    fetchBankingData();
  }, []);

  useEffect(() => {
    let count = 0;
    consolidateCheck.forEach((value) => {
      if (value.consolidate) {
        count++;
      }
    });
    setConsolidateCheckCount(count);
  }, [consolidateCheck]);

  const getConsolidateCheckMapKey = (bankDetail: BankDetailDTO): string => {
    return masFinId + "+" + bankDetail.perfiosTransactionId + "+" + bankDetail.accountNumber;
  };

  const getCopyOfConsolidateCheckMap = (bankDetail: BankDetailDTO): Map<string, ConsolidateObject> => {
    const copyMap = new Map<string, ConsolidateObject>();
    const key = getConsolidateCheckMapKey(bankDetail);
    const existingValue = consolidateCheck.get(key);
    if (existingValue) {
      copyMap.set(key, {consolidate: true, customerTransactionId: bankDetail.customerTransactionId});
    }
    return copyMap;
  };

  const getAccordionSummary = (variant: Variant, summary: string) => {
    return (
      <AccordionSummary
        expandIcon={loading ? <CircularProgress size="1.5rem"/> : <ExpandMore color="primary" />}
        aria-controls="panel1-content"
        id="panel1-header"
      >
        <Typography variant={variant}>{summary}</Typography>
      </AccordionSummary>
    );
  };

  const getAccordionDetails = (
    selectedTab: number,
    onTabChange: (value: number) => void,
    data: MonthlyDetailsDTO | undefined,
    consolidateCheckMap: Map<string, ConsolidateObject>,
  ) => {
    return (
      <AccordionDetails>
        <Tabs value={selectedTab} onChange={(event, value) => onTabChange(value)}>
          <Tab label={t("bankStatementTable.bankStatement")} {...a11yProps(0)} />
          <Tab label={t("bankStatementTable.transactions")} {...a11yProps(1)} />
        </Tabs>
        <CustomTabPanel value={selectedTab} index={0}>
          <BankStatementTable
            allData={data}
            consolidateCheck={consolidateCheckMap}
            masFinId={masFinId}
            loading={loading}
            customEditableFieldDTO={allData?.customEditableFieldDTO}
          />
        </CustomTabPanel>
        <CustomTabPanel value={selectedTab} index={1}>
          <BankTxnTable
            masFinId={masFinId}
            consolidateCheck={consolidateCheckMap}
          />
        </CustomTabPanel>
      </AccordionDetails>
    );
  };

  return (
    <div>
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
        <BankAccountDetails
          consolidateCheck={consolidateCheck}
          setConsolidateCheck={setConsolidateCheck}
          allData={allData}
          masFinId={masFinId}
          loading={loading}
        />
        <br />
        <Accordion defaultExpanded>
          {getAccordionSummary("h5", t("bankStatementTable.individualStatements"))}
          <AccordionDetails>
            {allData?.bankDetailList?.map((bankDetail, index) => {
              return (
                <Accordion key={index}>
                  {getAccordionSummary("h6", t(bankDetail.customerDetails.name
                    + " - " + bankDetail.bankName + " - " + bankDetail.accountNumber))}
                  {getAccordionDetails(
                    individualStatementTabs[index],
                    (value) => {
                      value === 0 && fetchBankingData();
                      setIndividualStatementTabs((prev) => {
                        const newValue = [...prev];
                        newValue[index] = value;
                        return newValue;
                      });
                    },
                    {masFinancialId: allData.masFinancialId, bankDetailList: [bankDetail],
                      customEditableFieldDTO: allData.customEditableFieldDTO},
                    getCopyOfConsolidateCheckMap(bankDetail),
                  )}
                </Accordion>
              );
            })}
          </AccordionDetails>
        </Accordion>
        {consolidateCheckCount > 1 &&
        <Accordion>
          {getAccordionSummary("h5", t("bankStatementTable.consolidatedStatements"))}
          {getAccordionDetails(consolidatedStatementTab, setConsolidatedStatementTab, allData, consolidateCheck)}
        </Accordion>
        }
        <br />
      </React.Fragment>
      }
    </div>
  );
}
