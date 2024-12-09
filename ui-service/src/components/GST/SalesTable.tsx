import {
  Alert,
  AlertColor,
  Button,
  Paper,
  Snackbar,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import React, { useEffect, useState } from "react";
import {
  Gst3BCustomerDetails,
  Gst3BSalesDetails,
  Gst3BSalesReport,
  Gst3BSupplierDetails,
  GSTReport,
  ParticularsData
} from "interfaces/gst";
import { updateGstDetails } from "utils/api/request";
import { getMonthYear } from "utils/dateFormat";
import { getPercentageFormat, getRoundedOffNumberWithCommas } from "utils/numberFormat";
import { AnalysisTable } from "components/GST/AnalysisTable";
import ParticularsTable from "components/GST/ParticularsTable";
import { useTranslation } from "react-i18next";

const OTHERS = "Others";
const MAX_ENTRIES = 10;

interface TempParticularsData {
  customers: {
    gst3BCustomersDetails: Gst3BCustomerDetails[];
    adjustedRevenueTotal: number;
  };
  suppliers: {
    gst3BSuppliersDetails: Gst3BSupplierDetails[];
    adjustedPurchaseAndExpensesTotal: number;
  };
}

export interface ParticularsDataMap {
  suppliers: { [name: string]: Gst3BSupplierDetails }
  customers: { [name: string]: Gst3BCustomerDetails }
}

interface SalesTableProps {
  entityId?: string,
  allData: GSTReport | undefined,
  consolidateCheck: Map<string, boolean>,
  displayConsolidatedReport: boolean,
  displayIndividualReport: boolean,
  gstNo?: string,
  individualReport?: Gst3BSalesReport
}

const initialParticularsData = {
  customers: {
    gst3BCustomersDetails: [],
    adjustedRevenueTotal: 0,
  },
  suppliers: {
    gst3BSuppliersDetails: [],
    adjustedPurchaseAndExpensesTotal: 0,
  },
};

const initialParticularsDataMap = {
  customers: {},
  suppliers: {},
};

const SalesTable = (props: SalesTableProps) => {

  // eslint-disable-next-line max-len
  const { entityId, allData, consolidateCheck, displayConsolidatedReport, displayIndividualReport, gstNo, individualReport } = props;

  const [showNotification, setShowNotification] = useState(false);
  const [alertContent, setAlertContent] =
    useState<{ value: string, severity: AlertColor }>({ value: "", severity: "success" });

  //for filtered data
  const [rowData, setRowData] = useState<Gst3BSalesDetails[]>([]);

  //for sales table
  const [salesTotal12, setSalesTotal12] = useState(0);
  const [purchaseTotal12, setPurchaseTotal12] = useState(0);

  const [salesTotalF, setSalesTotalF] = useState(0);
  const [purchaseTotalF, setPurchaseTotalF] = useState(0);

  const [salesTotalL, setSalesTotalL] = useState(0);
  const [purchaseTotalL, setPurchaseTotalL] = useState(0);


  //for analysis table
  const [customerInterfirm, setCustomerInterfirm] = useState(0);
  const [supplierInterfirm, setSupplierInterfirm] = useState(0);
  const [customerCircularOrOthers, setCustomerCircularOrOthers] = useState(0);
  const [supplierCircularOrOthers, setSupplierCircularOrOthers] = useState(0);
  const [customerMonths, setCustomerMonths] = useState(0);
  const [supplierMonths, setSupplierMonths] = useState(0);
  const [totalInterStateCustomersAnalysis, setTotalInterStateCustomersAnalysis] = useState(0);
  const [totalInterStateSuppliersAnalysis, setTotalInterStateSuppliersAnalysis] = useState(0);
  const [customerNetTotal, setCustomerNetTotal] = useState(0);
  const [supplierNetTotal, setSupplierNetTotal] = useState(0);
  const [customerMonthlyTo, setCustomerMonthlyTo] = useState(0);
  const [supplierMonthlyTo, setSupplierMonthlyTo] = useState(0);
  const { t } = useTranslation();

  //for particulars table
  const [particularsData, setParticularsData] = useState<ParticularsData>(initialParticularsData);
  const [particularsDataMap, setParticularsDataMap] = useState<ParticularsDataMap>(initialParticularsDataMap);

  useEffect(() => {
    setCustomerNetTotal(
      salesTotal12 - totalInterStateCustomersAnalysis - (customerInterfirm || 0) - (customerCircularOrOthers || 0));
    setSupplierNetTotal(
      purchaseTotal12 - totalInterStateSuppliersAnalysis - (supplierInterfirm || 0) - (supplierCircularOrOthers || 0));
    setCustomerMonthlyTo(salesTotal12 / customerMonths);
    setSupplierMonthlyTo(purchaseTotal12 / supplierMonths);
    // eslint-disable-next-line max-len
  }, [customerInterfirm, supplierInterfirm, customerCircularOrOthers, supplierCircularOrOthers, customerMonths, supplierMonths]);

  useEffect(() => {
    const fetch = async () => {
      const monthwiseAggregatedData: { [month: string]: Gst3BSalesDetails } = {};

      let tempTotalInterStateCustomersAnalysis = 0;
      let tempTotalInterStateSuppliersAnalysis = 0;

      let tempCustomerInterfirm = 0;
      let tempSupplierInterfirm = 0;

      let tempCustomerCircularOrOthers = 0;
      let tempSupplierCircularOrOthers = 0;
      let tempCustomerMonths = 0;
      let tempSupplierMonths = 0;

      let customersMap: { [name: string]: Gst3BCustomerDetails } = {};
      let suppliersMap: { [name: string]: Gst3BSupplierDetails } = {};

      const tempParticularsData: TempParticularsData = {
        customers: {
          gst3BCustomersDetails: [],
          adjustedRevenueTotal: 0,
        },
        suppliers: {
          gst3BSuppliersDetails: [],
          adjustedPurchaseAndExpensesTotal: 0,
        },
      };

      const getUpdatedCustomersMap = (
        customerDetails: Gst3BCustomerDetails[]): { [name: string]: Gst3BCustomerDetails } => {
        let updatedMap: { [name: string]: Gst3BCustomerDetails } = {};
        const others: Gst3BCustomerDetails[] = [];
        customerDetails
          // filter others as others record always has to be last
          .filter((value) => {
            if (value.customerName === OTHERS) {
              others.push(value);
              return false;
            }
            return true;
          })
          // sort descending order
          .sort((el1, el2) => el2.adjustedRevenue - el1.adjustedRevenue)
          // update entries, for existing entries add the value in existing entry
          .forEach((acc) => {
            updatedMap[acc.customerName] = updatedMap[acc.customerName] ? {
              ...updatedMap[acc.customerName],
              adjustedRevenue: updatedMap[acc.customerName].adjustedRevenue
                + acc.adjustedRevenue,
            } : acc;
          });
        // keep only max entries
        updatedMap = Object.entries(updatedMap).slice(0, MAX_ENTRIES).reduce((prev, acc) => ({
          ...prev,
          [acc[1].customerName]: {
            ...acc[1],
            numericEntryAddToInterfirm: false,
          },
        }), {});
        // add the others key, add if multiple others present
        others.forEach((value) => {
          updatedMap[value.customerName] = updatedMap[value.customerName] ? {
            ...updatedMap[value.customerName],
            adjustedRevenue: updatedMap[value.customerName].adjustedRevenue
              + value.adjustedRevenue,
          } : value;
        });
        return updatedMap;
      };

      const getUpdatedSuppliersMap = (
        supplierDetails: Gst3BSupplierDetails[]): { [name: string]: Gst3BSupplierDetails } => {
        let updatedMap: { [name: string]: Gst3BSupplierDetails } = {};
        const others: Gst3BSupplierDetails[] = [];
        supplierDetails
          .filter((value) => {
            if (value.supplierName === OTHERS) {
              others.push(value);
              return false;
            }
            return true;
          })
          // sort descending order
          .sort((el1, el2) => el2.adjustedPurchaseAndExpenses - el1.adjustedPurchaseAndExpenses)
          .forEach((acc) => {
            updatedMap[acc.supplierName] = updatedMap[acc.supplierName] ? {
              ...updatedMap[acc.supplierName],
              adjustedPurchaseAndExpenses: updatedMap[acc.supplierName].adjustedPurchaseAndExpenses
                + acc.adjustedPurchaseAndExpenses,
            } : acc;
          });
        // keep only max entries
        updatedMap = Object.entries(updatedMap).slice(0, MAX_ENTRIES).reduce((prev, acc) => ({
          ...prev,
          [acc[1].supplierName]: {
            ...acc[1],
            numericEntryAddToInterfirm: false,
          },
        }), {});
        // add the others key, add if multiple others present
        others.forEach((value) => {
          updatedMap[value.supplierName] = updatedMap[value.supplierName] ? {
            ...updatedMap[value.supplierName],
            adjustedPurchaseAndExpenses: updatedMap[value.supplierName].adjustedPurchaseAndExpenses
              + value.adjustedPurchaseAndExpenses,
          } : value;
        });
        return updatedMap;
      };

      const updatePercentageForCustomersMap = (adjustedRevenueTotal: number): void => {
        Object.entries(customersMap).forEach((customer) => {
          customer[1].adjustedRevenuePercent = getPercentageFormat(
            customer[1].adjustedRevenue * 100 / adjustedRevenueTotal);
        });
      };

      const updatePercentageForSuppliersMap = (adjustedPurchaseAndExpensesTotal: number): void => {
        Object.entries(suppliersMap).forEach((supplier) => {
          supplier[1].adjustedPurchaseAndExpensesPercent = getPercentageFormat(
            supplier[1].adjustedPurchaseAndExpenses * 100 / adjustedPurchaseAndExpensesTotal);
        });
      };

      const createFilteredData = (reportToBeDisplayed: Gst3BSalesReport) => {
        const { gst3BCustomers, gst3BSuppliers } = reportToBeDisplayed;
        const { gst3BSuppliersDetails } = gst3BSuppliers;
        const { gst3BCustomersDetails } = gst3BCustomers;

        const customerDetails = Object.values(customersMap).concat(gst3BCustomersDetails);
        const supplierDetails = Object.values(suppliersMap).concat(gst3BSuppliersDetails);

        if (displayIndividualReport) {
          customerDetails.forEach((curr) => {
            if (curr.numericEntryAddToInterfirm) {
              tempCustomerInterfirm += curr.adjustedRevenue;
            }
            customersMap = {
              ...customersMap,
              [curr.customerName]: curr,
            };
          });

          supplierDetails.forEach((curr) => {
            if (curr.numericEntryAddToInterfirm) {
              tempSupplierInterfirm += curr.adjustedPurchaseAndExpenses;
            }
            suppliersMap = {
              ...suppliersMap,
              [curr.supplierName]: curr,
            };
          });

        } else {
          // consolidate selected values only if multiple reports selected
          if (getSelectedGstNumbers().length > 1) {
            customersMap = getUpdatedCustomersMap(customerDetails);
            suppliersMap = getUpdatedSuppliersMap(supplierDetails);
          } else {
            customersMap = customerDetails.reduce(
              (acc, curr) => ({
                ...acc,
                [curr.customerName]: curr,
              }), {});
            suppliersMap = supplierDetails.reduce(
              (acc, curr) => ({
                ...acc,
                [curr.supplierName]: curr,
              }), {});
          }

          for (const name in customersMap) {
            const customerDetails = customersMap[name];
            if (customerDetails.numericEntryAddToInterfirm) {
              tempCustomerInterfirm += customerDetails.adjustedRevenue;
            }
          }

          for (const name in suppliersMap) {
            const supplierDetails = suppliersMap[name];
            if (supplierDetails.numericEntryAddToInterfirm) {
              tempSupplierInterfirm += supplierDetails.adjustedPurchaseAndExpenses;
            }
          }
        }

        tempCustomerMonths += reportToBeDisplayed?.totalNumberOfMonthsCustomersAnalysis;
        tempSupplierMonths += reportToBeDisplayed?.totalNumberOfMonthsSuppliersAnalysis;
        tempCustomerCircularOrOthers += reportToBeDisplayed?.circularOrOthersCustomersAnalysis;
        tempSupplierCircularOrOthers += reportToBeDisplayed?.circularOrOthersSuppliersAnalysis;
        tempTotalInterStateCustomersAnalysis += reportToBeDisplayed?.interStateCustomersAnalysis;
        tempTotalInterStateSuppliersAnalysis += reportToBeDisplayed?.interStateSuppliersAnalysis;
        const gst3BSalesDetails = reportToBeDisplayed?.gst3BSales?.gst3BSalesDetails;

        if (gst3BSalesDetails) {
          if (displayIndividualReport) {
            gst3BSalesDetails.forEach((data: Gst3BSalesDetails) => {
              const formattedMonth = getMonthYear(data.month);
              if (!monthwiseAggregatedData[formattedMonth]) {
                monthwiseAggregatedData[formattedMonth] = {
                  month: formattedMonth,
                  sales: 0,
                  purchase: 0,
                  delayInFiling: null,
                  delayedDays: null,
                };
              }
              monthwiseAggregatedData[formattedMonth].sales += data.sales;
              monthwiseAggregatedData[formattedMonth].purchase += data.purchase;
              if (data.delayedDays) {
                monthwiseAggregatedData[formattedMonth].delayedDays =
                  Number(monthwiseAggregatedData[formattedMonth].delayedDays) + Number(data.delayedDays);
              }
              monthwiseAggregatedData[formattedMonth].delayInFiling = data.delayInFiling;
            });
          } else {
            const maxGrossAdjustedRevenueSalesReport = getMaxGrossAdjustedRevenueSalesReport();
            const monthWiseSalesReport: { [key: string]: Gst3BSalesDetails } = {};
            maxGrossAdjustedRevenueSalesReport?.gst3BSales?.gst3BSalesDetails?.forEach((details) => {
              monthWiseSalesReport[details.month] = details;
            });
            gst3BSalesDetails.forEach((data) => {
              const formattedMonth = getMonthYear(data.month);
              if (!monthwiseAggregatedData[formattedMonth]) {
                monthwiseAggregatedData[formattedMonth] = {
                  month: formattedMonth,
                  sales: 0,
                  purchase: 0,
                  delayInFiling: null,
                  delayedDays: null,
                };
              }
              monthwiseAggregatedData[formattedMonth].sales += data.sales;
              monthwiseAggregatedData[formattedMonth].purchase += data.purchase;
              monthwiseAggregatedData[formattedMonth].delayedDays
                = monthWiseSalesReport[data.month]?.delayedDays ?? null;
              monthwiseAggregatedData[formattedMonth].delayInFiling
                = monthWiseSalesReport[data.month]?.delayInFiling ?? null;
            });
          }
        }
      };

      if (displayIndividualReport) {
        createFilteredData(individualReport!);
      } else if (!displayConsolidatedReport) {
        Object.keys(allData?.gst3BSalesReports || {}).forEach((key: string) => {
          if (consolidateCheck.get(key)) {
            if (allData?.gst3BSalesReports[key]) {
              createFilteredData(allData?.gst3BSalesReports[key]);
            }
          }
        });
      } else if (allData?.gst3BConsolidatedSalesReport) {
        createFilteredData(allData?.gst3BConsolidatedSalesReport);
      }

      const aggregatedDataArray = Object.entries(monthwiseAggregatedData)
        .sort(([monthA], [monthB]) => {
          return new Date(monthA).getTime() - new Date(monthB).getTime();
        });

      let tempSalesTotal = 0;
      let tempPurchaseTotal = 0;
      const tempRowData: Gst3BSalesDetails[] = aggregatedDataArray.map(([month, data], index) => {
        tempSalesTotal += data.sales;
        tempPurchaseTotal += data.purchase;
        return {
          ...data,
        };
      });

      let tempSalesTotal12 = 0;
      let tempPurchaseTotal12 = 0;
      const tempRowData12: Gst3BSalesDetails[] = aggregatedDataArray.slice(-12).map(([month, data], index) => {
        tempSalesTotal12 += data.sales;
        tempPurchaseTotal12 += data.purchase;
        return {
          ...data
        };
      });


      let tempSalesTotalF = 0;
      let tempPurchaseTotalF = 0;
      const tempRowDataF: Gst3BSalesDetails[] = aggregatedDataArray.slice(-6).map(([month, data], index) => {
        tempSalesTotalF += data.sales;
        tempPurchaseTotalF += data.purchase;
        return {
          ...data,
        };
      });

      let tempSalesTotalL = 0;
      let tempPurchaseTotalL = 0;
      const tempRowDataL: Gst3BSalesDetails[] = aggregatedDataArray.slice(12, 18).map(([month, data], index) => {
        tempSalesTotalL += data.sales;
        tempPurchaseTotalL += data.purchase;
        return {
          ...data,
        };
      });

      const mergedCustomers: Gst3BCustomerDetails[] = Object.values(customersMap);
      const mergedSuppliers: Gst3BSupplierDetails[] = Object.values(suppliersMap);
      tempParticularsData.customers.gst3BCustomersDetails = mergedCustomers;
      tempParticularsData.customers.adjustedRevenueTotal = mergedCustomers.reduce((total, customer) =>
        total + customer.adjustedRevenue, 0);
      tempParticularsData.suppliers.gst3BSuppliersDetails = mergedSuppliers;
      tempParticularsData.suppliers.adjustedPurchaseAndExpensesTotal = mergedSuppliers.reduce((total, supplier) =>
        total + supplier.adjustedPurchaseAndExpenses, 0);
      // update percentages only if multiple selected
      if (!displayIndividualReport && getSelectedGstNumbers().length > 1) {
        updatePercentageForCustomersMap(tempParticularsData.customers.adjustedRevenueTotal);
        updatePercentageForSuppliersMap(tempParticularsData.suppliers.adjustedPurchaseAndExpensesTotal);
      }

      const tempParticularsDataMap = {
        suppliers: suppliersMap,
        customers: customersMap,
      };

      setRowData(tempRowData);
      setSalesTotal12(tempSalesTotal12);
      setSalesTotalF(tempSalesTotalF);
      setSalesTotalL(tempSalesTotalL);
      setPurchaseTotal12(tempPurchaseTotal12);
      setCustomerInterfirm(tempCustomerInterfirm);
      setSupplierInterfirm(tempSupplierInterfirm);
      setCustomerNetTotal(
        tempSalesTotal - tempTotalInterStateCustomersAnalysis - (tempCustomerCircularOrOthers || 0));
      setSupplierNetTotal(
        tempPurchaseTotal - tempTotalInterStateSuppliersAnalysis - (tempSupplierCircularOrOthers || 0));
      setParticularsData(tempParticularsData);
      setParticularsDataMap(tempParticularsDataMap);
      setCustomerCircularOrOthers(tempCustomerCircularOrOthers);
      setSupplierCircularOrOthers(tempSupplierCircularOrOthers);
      setCustomerMonths(tempCustomerMonths);
      setSupplierMonths(tempSupplierMonths);
      setTotalInterStateCustomersAnalysis(tempTotalInterStateCustomersAnalysis);
      setTotalInterStateSuppliersAnalysis(tempTotalInterStateSuppliersAnalysis);
    };

    fetch();
  }, [consolidateCheck, displayConsolidatedReport]);

  const updateParticulars = () => {
    return {
      ...particularsData,
      customers: {
        ...particularsData.customers,
        gst3BCustomersDetails: Object.entries(particularsDataMap.customers).map((value) => value[1]),
      },
      suppliers: {
        ...particularsData.suppliers,
        gst3BSuppliersDetails: Object.entries(particularsDataMap.suppliers).map((value) => value[1]),
      },
    };
  };

  const updateAnalysisEntries = () => {
    let updatedData = {};
    const tempParticularsData = updateParticulars();
    const updatedDataValues = {
      gst3BSuppliers: tempParticularsData.suppliers,
      gst3BCustomers: tempParticularsData.customers,
      circularOrOthersCustomersAnalysis: customerCircularOrOthers,
      circularOrOthersSuppliersAnalysis: supplierCircularOrOthers,
      totalNumberOfMonthsCustomersAnalysis: customerMonths,
      totalNumberOfMonthsSuppliersAnalysis: supplierMonths,
    } as Gst3BSalesReport;
    if (displayIndividualReport) {
      updatedData = {
        clientOrderId: individualReport!.clientOrderId,
        gstReport: {
          ...allData,
          gst3BSalesReports: {
            ...allData?.gst3BSalesReports,
            [gstNo!]: {
              ...allData?.gst3BSalesReports[gstNo!],
              ...updatedDataValues,
            },
          },
        },
      };
    } else {
      updatedData = {
        entityId: entityId,
        gstReport: {
          ...allData,
          gst3BConsolidatedSalesReport: {
            ...allData?.gst3BConsolidatedSalesReport,
            ...updatedDataValues,
          },
        },
      };

    }
    updateGstDetails(updatedData)
      .then(() => {
        setAlertContent({ value: t("salesTable.alert.reportUpdatedSuccessfully"), severity: "success" });
      })
      .catch(() => {
        setAlertContent({ value: t("salesTable.alert.errorWhileUpdating"), severity: "error" });
      })
      .finally(() => setShowNotification(true));
  };

  const handleSubmit = () => {
    updateAnalysisEntries();
  };
  
  const updateInterfirm = (asPerGst: number, newCheckedValue: boolean, isSupplier: boolean) => {
    if (isSupplier) {
      if (newCheckedValue) {
        setSupplierInterfirm(supplierInterfirm + asPerGst);
      } else {
        setSupplierInterfirm(supplierInterfirm - asPerGst);
      }
    } else {
      if (newCheckedValue) {
        setCustomerInterfirm(customerInterfirm + asPerGst);
      } else {
        setCustomerInterfirm(customerInterfirm - asPerGst);
      }
    }
  };

  const getSelectedGstNumbers = (): string[] => {
    const selectedGstNumbers: string[] = [];
    consolidateCheck.forEach((value, key) => value && selectedGstNumbers.push(key));
    return selectedGstNumbers;
  };

  const getMaxGrossAdjustedRevenueSalesReport = (): Gst3BSalesReport | null => {
    const selectedGstNumbers = getSelectedGstNumbers();
    let maxGrossAdjustedRevenue = Number.MIN_SAFE_INTEGER;
    let maxGrossAdjustedRevenueSalesReport: Gst3BSalesReport | null = null;
    for (const entry of Object.entries(allData?.gst3BSalesReports || {})) {
      if (selectedGstNumbers.includes(entry[0]) && maxGrossAdjustedRevenue < Number(entry[1].grossAdjustedRevenue)) {
        maxGrossAdjustedRevenue = Number(entry[1].grossAdjustedRevenue);
        maxGrossAdjustedRevenueSalesReport = entry[1];
      }
    }
    return maxGrossAdjustedRevenueSalesReport;
  };

  const getAverageDelayInDays = (): string => {
    if (displayIndividualReport) {
      const averageDelayInDays = allData?.gst3BSalesReports?.[gstNo!]?.gst3BSales?.averageDelayInDays;
      return averageDelayInDays ?? "salesTable.noFiling";
    } else {
      const selectedGstNumbers = getSelectedGstNumbers();
      if (selectedGstNumbers.length > 1) {
        const maxGrossAdjustedRevenueSalesReport = getMaxGrossAdjustedRevenueSalesReport();
        return maxGrossAdjustedRevenueSalesReport?.gst3BSales?.averageDelayInDays ?? "salesTable.noFiling";
      } else {
        const averageDelayInDays = allData?.gst3BConsolidatedSalesReport?.gst3BSales?.averageDelayInDays;
        return averageDelayInDays ?? "salesTable.noFiling";
      }
    }
  };

  return (
    <React.Fragment>
      <Snackbar open={showNotification} autoHideDuration={2000} onClose={() => setShowNotification(false)}>
        <Alert
          severity={alertContent.severity}
          variant="filled"
        >
          {alertContent.value}
        </Alert>
      </Snackbar>
      <TableContainer className="mv-10" component={Paper}>
        <Table aria-label="customized table">
          <TableHead>
            <TableRow><TableCell align="center" colSpan={6}>{t("gstinDetails.salesAsPerGstr3b")}</TableCell></TableRow>
            <TableRow>
              <TableCell align="center">{t("salesTable.srNo")}</TableCell>
              <TableCell align="center">
                {t("salesTable.month")}
              </TableCell>
              <TableCell align="center">
                {t("salesTable.gst3bSalesExcludingGst")}
              </TableCell>
              <TableCell align="center">
                {t("salesTable.purchaseExcludingGst")}
              </TableCell>
              <TableCell align="center">
                {t("salesTable.delayInFiling")}
              </TableCell>
              <TableCell align="center">
                {t("salesTable.delayInDays")}
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {rowData?.slice(-12).map((row, index) => (
              <TableRow key={index}>
                <TableCell align="center" component="th" scope="row">
                  {t(String(index + 1))}
                </TableCell>
                <TableCell align="center">{t(row.month)}</TableCell>
                <TableCell align="center">{t(getRoundedOffNumberWithCommas(row.sales))}</TableCell>
                <TableCell align="center">{t(getRoundedOffNumberWithCommas(row.purchase))}</TableCell>
                <TableCell align="center">
                  {t(`salesTable.${row.delayInFiling === true ? "yes"
                    : row.delayInFiling === null ? "noFiling" : "no"}`)}
                </TableCell>
                <TableCell align="center">
                  {t(row.delayedDays !== null ? String(row.delayedDays) : "salesTable.noFiling")}
                </TableCell>
              </TableRow>
            ))}
            <TableRow>
              <TableCell></TableCell>
              <TableCell align="center">{t("salesTable.total")}</TableCell>
              <TableCell align="center">{t(getRoundedOffNumberWithCommas(salesTotal12))}</TableCell>
              <TableCell align="center">{t(getRoundedOffNumberWithCommas(purchaseTotal12))}</TableCell>
              <TableCell align="center">{t("salesTable.averageDelayInDays")}</TableCell>
              <TableCell align="center">{t(getAverageDelayInDays())}</TableCell>
            </TableRow>
            <TableRow>
              <TableCell></TableCell>
              <TableCell align="center">{t("salesTable.totalFirst6Month")}</TableCell>
              <TableCell align="center">{t(getRoundedOffNumberWithCommas(salesTotalF))}</TableCell>
              <TableCell align="center">{t(getRoundedOffNumberWithCommas(purchaseTotalF))}</TableCell>
              <TableCell align="center"></TableCell>
              <TableCell align="center"></TableCell>
            </TableRow>
            <TableRow>
              <TableCell></TableCell>
              <TableCell align="center">{t("salesTable.totalLast6Month")}</TableCell>
              <TableCell align="center">{t(getRoundedOffNumberWithCommas(salesTotalL))}</TableCell>
              <TableCell align="center">{t(getRoundedOffNumberWithCommas(purchaseTotalL))}</TableCell>
              <TableCell align="center"></TableCell>
              <TableCell align="center"></TableCell>
            </TableRow>
            <AnalysisTable
              totalInterStateCustomersAnalysis={totalInterStateCustomersAnalysis}
              totalInterStateSuppliersAnalysis={totalInterStateSuppliersAnalysis}
              customerInterfirm={customerInterfirm}
              supplierInterfirm={supplierInterfirm}
              customerCircularOrOthers={customerCircularOrOthers}
              supplierCircularOrOthers={supplierCircularOrOthers}
              customerNetTotal={customerNetTotal}
              supplierNetTotal={supplierNetTotal}
              customerMonths={customerMonths}
              supplierMonths={supplierMonths}
              supplierMonthlyTo={supplierMonthlyTo}
              customerMonthlyTo={customerMonthlyTo}
              setCustomerCircularOrOthers={setCustomerCircularOrOthers}
              setSupplierCircularOrOthers={setSupplierCircularOrOthers}
              setCustomerMonths={setCustomerMonths}
              setSupplierMonths={setSupplierMonths}
            />
          </TableBody>
        </Table>
      </TableContainer>
      <TableContainer className="mv-10" component={Paper}>
        <Table aria-label="customized table">
          <TableHead>
            <TableRow><TableCell align="center" colSpan={6}>{t("gstinDetails.salesAsPerGstr3b")}</TableCell></TableRow>
            <TableRow>
              <TableCell align="center">{t("salesTable.srNo")}</TableCell>
              <TableCell align="center">
                {t("salesTable.month")}
              </TableCell>
              <TableCell align="center">
                {t("salesTable.gst3bSalesExcludingGst")}
              </TableCell>
              <TableCell align="center">
                {t("salesTable.purchaseExcludingGst")}
              </TableCell>
              <TableCell align="center">
                {t("salesTable.delayInFiling")}
              </TableCell>
              <TableCell align="center">
                {t("salesTable.delayInDays")}
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {rowData?.slice(0, 12).map((row, index) => (
              <TableRow key={index}>
                <TableCell align="center" component="th" scope="row">
                  {t(String(index + 1))}
                </TableCell>
                <TableCell align="center">{t(row.month)}</TableCell>
                <TableCell align="center">{t(getRoundedOffNumberWithCommas(row.sales))}</TableCell>
                <TableCell align="center">{t(getRoundedOffNumberWithCommas(row.purchase))}</TableCell>
                <TableCell align="center">
                  {t(`salesTable.${row.delayInFiling === true ? "yes"
                    : row.delayInFiling === null ? "noFiling" : "no"}`)}
                </TableCell>
                <TableCell align="center">
                  {t(row.delayedDays !== null ? String(row.delayedDays) : "salesTable.noFiling")}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      <ParticularsTable
        particularsDataMap={particularsDataMap}
        setParticularsDataMap={setParticularsDataMap}
        updateInterfirm={updateInterfirm}
      />
      <div className="flex-justify-center m-10">
        <Button
          onClick={handleSubmit}
          variant="contained"
          size="small"
          disabled={!displayIndividualReport && !displayConsolidatedReport}
        >
          {t("salesTable.saveNumericEntries")}
        </Button>
      </div>
    </React.Fragment>
  );
};

export default SalesTable;
