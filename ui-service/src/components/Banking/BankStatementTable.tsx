import {Alert, AlertColor, Button, InputBase, Snackbar, TableCell} from "@mui/material";
import Paper from "@mui/material/Paper";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import {useTranslation} from "react-i18next";
import {
  BankDetailDTO,
  CustomEditableFieldDTO,
  CustomFieldMonthlyReportReqDTO,
  CustomFieldReqDTO,
  MonthlyDetailsDTO,
  MonthlyReportDTO,
} from "interfaces/banking";
import React, {useEffect, useState} from "react";
import {getRoundedOffNumberWithCommas} from "utils/numberFormat";
import {updateBankingCustomFields} from "utils/api/request";
import {ConsolidateObject} from "pages/Banking";
import TableRowsLoader from "utils/components/TableRowsLoader";

interface MonthlyReportWithSrNo extends MonthlyReportDTO {
  srNo: number
}

interface TotalValues {
  debit: number,
  credit: number,
  debitInterBank: number,
  debitInterFirm: number,
  debitNonBusinessTransaction: number,
  creditInterBank: number,
  creditInterFirm: number,
  creditNonBusinessTransaction: number,
  iwReturn: number,
  owReturn: number,
  totalLoanDisbursements: number,
  totalEmiTracing: number,
  penalMABCharges: number,
  ccUtilization: number,
}

function getDefaultTotalValues(): TotalValues {
  return {
    debit: 0,
    credit: 0,
    debitInterBank: 0,
    debitInterFirm: 0,
    debitNonBusinessTransaction: 0,
    creditInterBank: 0,
    creditInterFirm: 0,
    creditNonBusinessTransaction: 0,
    iwReturn: 0,
    owReturn: 0,
    totalLoanDisbursements: 0,
    totalEmiTracing: 0,
    penalMABCharges: 0,
    ccUtilization: 0,
  };
}

function getMonthYear(params: Date | string): string {
  const [day, month, year] = (params as string).split("/");
  const formattedDateString = `${month}/${day}/${year}`;

  const dt = new Date(formattedDateString)
    .toLocaleDateString("en-GB", {
      day: "numeric",
      month: "short",
      year: "numeric",
    }).split(" ");
  return `${dt[1]}-${dt[2]}`;
}

interface BankStatementTableProps {
  allData: MonthlyDetailsDTO | undefined;
  consolidateCheck: Map<string, ConsolidateObject>;
  masFinId: string;
  loading: boolean;
  customEditableFieldDTO:CustomEditableFieldDTO | undefined;
}

export function BankStatementTable(props: BankStatementTableProps) {
  const {allData, consolidateCheck, masFinId, loading,customEditableFieldDTO} = props;

  const [isSingleAccSelected, setIsSingleAccSelected] = useState(false);
  const [selectedAccSummaryUuid, setSelectedAccSummaryUuid] = useState<string | null>(null);
  const [selectedBanks, setSelectedBanks] = useState<Set<string>>(new Set());

  const [rowData, setRowData] = useState<MonthlyReportWithSrNo[]>([]);

  const [totalValues, setTotalValues] = useState<TotalValues>(getDefaultTotalValues());

  const [customDebitLoanReceiptsInput, setCustomDebitLoanReceiptsInput] = useState<number>(0);
  const [customCreditLoanReceiptsInput, setCustomCreditLoanReceiptsInput] = useState<number>(0);

  const [showNotification, setShowNotification] = useState(false);
  const [alertContent, setAlertContent] = useState<{
    value: string,
    severity: AlertColor
  }>({value: "", severity: "success"});
  useEffect(() => {
    const fetch = async () => {
      const monthwiseAggregatedData: { [month: string]: MonthlyReportWithSrNo } = {};

      const tempBankNames: Set<string> = new Set();
      let tempCustomDebitLoanReceiptsTotal = 0;
      let tempCustomCreditLoanReceiptsTotal = 0;
      let consolidateCheckCnt = 0;
      let tempSelectedAccSummaryUuid: string | null = null;

      const createFilteredData = (reportToBeDisplayed: BankDetailDTO) => {
        if (reportToBeDisplayed) {
          reportToBeDisplayed.monthlyReportList.forEach((data: MonthlyReportDTO, index: number) => {
            const formattedMonth = getMonthYear(data.monthYear);
            if (!monthwiseAggregatedData[formattedMonth]) {
              monthwiseAggregatedData[formattedMonth] = {
                srNo: index + 1,
                monthwiseDetailUuid: "",
                monthYear: formattedMonth,
                debit: 0,
                credit: 0,
                debitInterFirm: 0,
                debitInterBank: 0,
                creditInterFirm: 0,
                creditInterBank: 0,
                creditNonBusinessTransaction: 0,
                debitNonBusinessTransaction: 0,
                inwardReturn: 0,
                outwardReturn: 0,
                totalLoanDisbursements: 0,
                totalEmiTracing: 0,
                quarterlyBankingTurnover: 0,
                penaltyCharges: 0,
                customCcUtilizationPercentage: null,
              };
            }
            monthwiseAggregatedData[formattedMonth].monthwiseDetailUuid = data.monthwiseDetailUuid;
            monthwiseAggregatedData[formattedMonth].debit += data.debit;
            monthwiseAggregatedData[formattedMonth].debitInterBank += data.debitInterBank;
            monthwiseAggregatedData[formattedMonth].debitInterFirm += data.debitInterFirm;
            monthwiseAggregatedData[formattedMonth].debitNonBusinessTransaction += data.debitNonBusinessTransaction;
            monthwiseAggregatedData[formattedMonth].credit += data?.credit;
            monthwiseAggregatedData[formattedMonth].creditInterBank += data.creditInterBank;
            monthwiseAggregatedData[formattedMonth].creditInterFirm += data.creditInterFirm;
            monthwiseAggregatedData[formattedMonth].creditNonBusinessTransaction += data.creditNonBusinessTransaction;
            monthwiseAggregatedData[formattedMonth].inwardReturn += data.inwardReturn;
            monthwiseAggregatedData[formattedMonth].outwardReturn += data.outwardReturn;
            monthwiseAggregatedData[formattedMonth].totalLoanDisbursements += data.totalLoanDisbursements;
            monthwiseAggregatedData[formattedMonth].totalEmiTracing += data.totalEmiTracing;
            monthwiseAggregatedData[formattedMonth].penaltyCharges += data.penaltyCharges;
            if (data.customCcUtilizationPercentage != null) {
              if (monthwiseAggregatedData[formattedMonth].customCcUtilizationPercentage != null) {
                monthwiseAggregatedData[formattedMonth].customCcUtilizationPercentage! +=
                  data.customCcUtilizationPercentage;
              } else {
                monthwiseAggregatedData[formattedMonth].customCcUtilizationPercentage =
                  data.customCcUtilizationPercentage;
              }
            }
          });
        }
      };

      allData?.bankDetailList?.forEach((bankingDetail: BankDetailDTO) => {
        if (consolidateCheck
          .get(masFinId + "+" + bankingDetail.perfiosTransactionId + "+" + bankingDetail.accountNumber)?.consolidate) {
          tempSelectedAccSummaryUuid = bankingDetail.accountSummaryUuid;
          tempBankNames.add(bankingDetail.bankName);
          tempCustomDebitLoanReceiptsTotal += bankingDetail.customDebitLoanReceipts || 0;
          tempCustomCreditLoanReceiptsTotal += bankingDetail.customCreditLoanReceipts || 0;
          consolidateCheckCnt++;
          createFilteredData(bankingDetail);
        }
      });
      const aggregatedDataArray = Object.entries(monthwiseAggregatedData)
        .sort(([monthA], [monthB]) => {
          return new Date(monthA).getTime() - new Date(monthB).getTime();
        });

      const totalValues = getDefaultTotalValues();

      const tempRowData: MonthlyReportWithSrNo[] = aggregatedDataArray.map(([month, data], index) => {
        totalValues.credit += data?.credit;
        totalValues.debit += data.debit;
        totalValues.debitInterBank += data.debitInterBank;
        totalValues.creditInterBank += data.creditInterBank;
        totalValues.debitInterFirm += data.debitInterFirm;
        totalValues.creditInterFirm += data.creditInterFirm;
        totalValues.debitNonBusinessTransaction += data.debitNonBusinessTransaction;
        totalValues.creditNonBusinessTransaction += data.creditNonBusinessTransaction;
        totalValues.iwReturn += data.inwardReturn;
        totalValues.owReturn += data.outwardReturn;
        totalValues.totalLoanDisbursements += data.totalLoanDisbursements;
        totalValues.totalEmiTracing += data.totalEmiTracing;
        totalValues.penalMABCharges += data.penaltyCharges;
        totalValues.ccUtilization += data.customCcUtilizationPercentage || 0;
        return {
          ...data,
          quarterlyBankingTurnover: 0,
          srNo: index + 1,
        };
      });

      let i = 0;
      while (i < 12) {
        let tempCreditTotal = 0;
        let tempTotalInterfirm = 0, tempTotalInterbank = 0, tempTotalNonBusinessTransactions = 0,
          tempLoanDisbursementTotal = 0;
        for (let j = i; j < i + 3; j++) {
          tempCreditTotal += tempRowData[j]?.credit || 0;
          tempTotalInterfirm += tempRowData[j]?.creditInterFirm;
          tempTotalInterbank += tempRowData[j]?.creditInterBank;
          tempTotalNonBusinessTransactions += tempRowData[j]?.creditNonBusinessTransaction;
          tempLoanDisbursementTotal += tempRowData[j]?.totalLoanDisbursements;
        }
        if (tempRowData[i]) {
          tempRowData[i].quarterlyBankingTurnover = tempCreditTotal - tempTotalInterbank
            - tempTotalInterfirm - tempTotalNonBusinessTransactions - tempLoanDisbursementTotal;
        }
        i += 3;
      }

      setCustomCreditLoanReceiptsInput(tempCustomCreditLoanReceiptsTotal);
      setCustomDebitLoanReceiptsInput(tempCustomDebitLoanReceiptsTotal);
      setSelectedBanks(tempBankNames);
      setSelectedAccSummaryUuid(tempSelectedAccSummaryUuid);
      setIsSingleAccSelected(consolidateCheckCnt === 1);
      setRowData(tempRowData);
      setTotalValues(totalValues);
    };

    fetch();
  }, [consolidateCheck]);
  useEffect(() => {
    setTotalValues(prevState => ({
      ...prevState,
      totalEmiTracing: (customEditableFieldDTO?.customDebitLoanReceipts ?? 0) +
      (customEditableFieldDTO?.totalDebitInterBank ?? 0) + (customEditableFieldDTO?.totalDebitInterFirm ?? 0) +
      (customEditableFieldDTO?.totalDebitNonBusinessTransaction ?? 0),
      credit: (customEditableFieldDTO?.customCreditLoanReceipts ?? 0) +
      (customEditableFieldDTO?.totalCreditInterBank ?? 0)
      + (customEditableFieldDTO?.totalCreditInterFirm ?? 0) +
      (customEditableFieldDTO?.totalCreditNonBusinessTransaction ?? 0),
      debitInterBank: customEditableFieldDTO?.totalDebitInterBank ?? prevState.debitInterBank,
      creditInterBank: customEditableFieldDTO?.totalCreditInterBank ?? prevState.creditInterBank,
      debitInterFirm: customEditableFieldDTO?.totalDebitInterFirm ?? prevState.debitInterFirm,
      creditInterFirm: customEditableFieldDTO?.totalCreditInterFirm ?? prevState.creditInterFirm,
      debitNonBusinessTransaction: customEditableFieldDTO?.totalDebitNonBusinessTransaction ??
      prevState.debitNonBusinessTransaction,
      creditNonBusinessTransaction: customEditableFieldDTO?.totalCreditNonBusinessTransaction ??
      prevState.creditNonBusinessTransaction,
      iwReturn: prevState.iwReturn,
      owReturn: prevState.owReturn,
      totalLoanDisbursements: customEditableFieldDTO?.customCreditLoanReceipts ?? prevState.totalLoanDisbursements,
      penalMABCharges: prevState.penalMABCharges,
      ccUtilization: prevState.ccUtilization,
    }));
  }, [customEditableFieldDTO]);
  function handleCCUtilisationInputChange(row: MonthlyReportWithSrNo, value: string) {
    const updatedRows = rowData.map((r) => {
      if (r.monthwiseDetailUuid === row.monthwiseDetailUuid) {
        return {
          ...row,
          customCcUtilizationPercentage: value ? Number(value) : null,
        };
      } else {
        return r;
      }
    });

    const totalCCUtilization = updatedRows.reduce((total, row) => {
      return total + (row.customCcUtilizationPercentage || 0);
    }, 0);
    setTotalValues({...totalValues, ccUtilization: totalCCUtilization});
    setRowData(updatedRows);
  }
  const [fieldValues, setFieldValues] = useState<CustomEditableFieldDTO>({
    customCreditLoanReceipts: 0,
    customDebitLoanReceipts: 0,
    totalCreditInterBank: 0,
    totalDebitInterBank: 0,
    totalCreditInterFirm: 0,
    totalDebitInterFirm: 0,
    totalCreditNonBusinessTransaction: 0,
    totalDebitNonBusinessTransaction: 0,
    masFinId: masFinId,
  });

  function handleTotalAllRow(field: string, value: string) {
    const parsedValue = parseFloat(value) || 0;
    if (field==="debitInterBank") {
      setTotalValues((prev) => ({
        ...prev,
        debitInterBank: parsedValue,
      }));
      setFieldValues(prev => ({
        ...prev,
        totalDebitInterBank: parsedValue,
      }));
    }
    if (field==="creditInterBank") {
      setTotalValues((prev) => ({
        ...prev,
        creditInterBank: parsedValue,
      }));
      setFieldValues(prev => ({
        ...prev,
        totalCreditInterBank: parsedValue,
      }));
    }
    if (field==="debitInterFirm") {
      setTotalValues((prev) => ({
        ...prev,
        debitInterFirm: parsedValue,
      }));
      setFieldValues(prev => ({
        ...prev,
        totalDebitInterFirm: parsedValue,
      }));
    }
    if (field==="creditInterFirm") {
      setTotalValues((prev) => ({
        ...prev,
        creditInterFirm: parsedValue,
      }));
      setFieldValues(prev => ({
        ...prev,
        totalCreditInterFirm: parsedValue,
      }));
    }
    if (field==="debitNonBusinessTransaction") {
      setTotalValues((prev) => ({
        ...prev,
        debitNonBusinessTransaction: parsedValue,
      }));
      setFieldValues(prev => ({
        ...prev,
        totalDebitNonBusinessTransaction: parsedValue,
      }));
    }
    if (field==="creditNonBusinessTransaction") {
      setTotalValues((prev) => ({
        ...prev,
        creditNonBusinessTransaction: parsedValue,
      }));
      setFieldValues(prev => ({
        ...prev,
        totalCreditNonBusinessTransaction: parsedValue,
      }));
    }
    if (field==="totalEmiTracing") {
      setTotalValues((prev) => ({
        ...prev,
        totalEmiTracing: parsedValue,
      }));
      setFieldValues(prev => ({
        ...prev,
        customDebitLoanReceipts: parsedValue,
      }));
    }
    if (field==="totalLoanDisbursements") {
      setTotalValues((prev) => ({
        ...prev,
        totalLoanDisbursements: parsedValue,
      }));
      setFieldValues(prev => ({
        ...prev,
        customCreditLoanReceipts: parsedValue,
      }));
    }
  }

  const handleSubmitNumericEntries = (): void => {
    const monthsWithUpdatedCCUtilization = rowData
      .filter((row) => row.customCcUtilizationPercentage !== null)
      .map<CustomFieldMonthlyReportReqDTO>((row) => {
        return {
          monthwiseDetailUuid: row.monthwiseDetailUuid ?? "",
          customCcUtilizationPercentage: row.customCcUtilizationPercentage ?? 0,
        };
      });
    const updatedData: CustomFieldReqDTO = {
      accountSummaryUuid: selectedAccSummaryUuid ?? "",
      customCreditLoanReceipts: customCreditLoanReceiptsInput,
      customDebitLoanReceipts: customDebitLoanReceiptsInput,
      monthlyReportFieldList: monthsWithUpdatedCCUtilization,
      customEditableFieldDTO: fieldValues,
    };
    updateBankingCustomFields(updatedData)
      .then(() => {
        setAlertContent({value: t("bankStatementTable.alert.statementUpdatedSuccessfully"), severity: "success"});
      })
      .catch((error) => {
        setAlertContent(
          {value: error.response.data.message || t("bankStatementTable.alert.errorWhileUpdating"), severity: "error"});
      })
      .finally(() => setShowNotification(true));
  };

  const {t} = useTranslation();

  const getAverageAndMedianRows = () => {
    if (!isSingleAccSelected) {
      return <></>;
    }

    let customerTxId: string | null = null;
    consolidateCheck.forEach((value) => {
      if (value.consolidate) {
        customerTxId = value.customerTransactionId;
      }
    });

    const bankDetails = allData?.bankDetailList?.find((value) => value.customerTransactionId === customerTxId);
    if (bankDetails) {
      return (
        <React.Fragment>
          <TableRow>
            <TableCell colSpan={2}>{t("bankStatementTable.rows.averageBankingBalance")}</TableCell>
            <TableCell align="center" colSpan={2}>
              {getRoundedOffNumberWithCommas(bankDetails.averageBankingBalance)}
            </TableCell>
          </TableRow>
          <TableRow>
            <TableCell colSpan={2}>{t("bankStatementTable.rows.medianBankingBalance")}</TableCell>
            <TableCell align="center" colSpan={2}>
              {getRoundedOffNumberWithCommas(bankDetails.medianBankingBalance)}
            </TableCell>
          </TableRow>
        </React.Fragment>
      );
    } else {
      return <></>;
    }
  };

  return (
    <>
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
            <TableRow>
              <TableCell align="left" colSpan={2}>{t("bankStatementTable.rows.nameOfBank")}</TableCell>
              <TableCell align="left" colSpan={15}>{Array.from(selectedBanks).join(" | ")}</TableCell>
            </TableRow>
            <TableRow>
              <TableCell align="center" >{t("bankStatementTable.columns.srNo")}</TableCell>
              <TableCell align="center" >{t("bankStatementTable.columns.particulars")}</TableCell>
              <TableCell align='center' >{t("bankStatementTable.columns.debit")}</TableCell>
              <TableCell align='center' >{t("bankStatementTable.columns.credit")}</TableCell>
              <TableCell align='center' >{t("bankStatementTable.columns.debitInterBank")}</TableCell>
              <TableCell align='center' >{t("bankStatementTable.columns.creditInterBank")}</TableCell>
              <TableCell align='center' >{t("bankStatementTable.columns.debitInterFirm")}</TableCell>
              <TableCell align='center' >{t("bankStatementTable.columns.creditInterFirm")}</TableCell>
              <TableCell align='center' >{t("bankStatementTable.columns.debitNonBusinessTransaction")}</TableCell>
              <TableCell align='center' >{t("bankStatementTable.columns.creditNonBusinessTransaction")}</TableCell>
              <TableCell align="center">{t("bankStatementTable.columns.loanReceipts")}</TableCell>
              <TableCell align="center">{t("bankStatementTable.columns.payments")}</TableCell>
              <TableCell align='center' >{t("bankStatementTable.columns.iwReturn")}</TableCell>
              <TableCell align='center' >{t("bankStatementTable.columns.owReturn")}</TableCell>
              <TableCell align='center' >{t("bankStatementTable.columns.quarterlyTurnover")}</TableCell>
              <TableCell align='center' >{t("bankStatementTable.columns.penalMABCharges")}</TableCell>
              {isSingleAccSelected &&
                <TableCell align="center">{t("bankStatementTable.columns.ccUtilization")}</TableCell>
              }
            </TableRow>
          </TableHead>
          {loading && <TableRowsLoader rowsNum={12} colsNum={15} />}
          <TableBody>
            {rowData.map((row, index) => (
              <TableRow key={row.monthYear}>
                <TableCell align="center">{row.srNo}</TableCell>
                <TableCell component="th" scope="row" align='center'>
                  {row.monthYear}
                </TableCell>
                <TableCell align="center">{getRoundedOffNumberWithCommas(row.debit)}</TableCell>
                <TableCell align="center">{getRoundedOffNumberWithCommas(row.credit)}</TableCell>
                <TableCell align="center">{getRoundedOffNumberWithCommas(row.debitInterBank)}</TableCell>
                <TableCell align="center">{getRoundedOffNumberWithCommas(row.creditInterBank)}</TableCell>
                <TableCell align="center">{getRoundedOffNumberWithCommas(row.debitInterFirm)}</TableCell>
                <TableCell align="center">{getRoundedOffNumberWithCommas(row.creditInterFirm)}</TableCell>
                <TableCell align="center">{getRoundedOffNumberWithCommas(row.debitNonBusinessTransaction)}</TableCell>
                <TableCell align="center">{getRoundedOffNumberWithCommas(row.creditNonBusinessTransaction)}</TableCell>
                <TableCell align="center">{getRoundedOffNumberWithCommas(row.totalLoanDisbursements)}</TableCell>
                <TableCell align="center">{getRoundedOffNumberWithCommas(row.totalEmiTracing)}</TableCell>
                <TableCell align="center">{row.inwardReturn}</TableCell>
                <TableCell align="center">{row.outwardReturn}</TableCell>
                {index % 3 === 0 &&
                  <TableCell align="center" rowSpan={rowData.length >= index + 3 ? 3 : rowData.length - index} >
                    {getRoundedOffNumberWithCommas(row?.quarterlyBankingTurnover || 0)}
                  </TableCell>
                }
                <TableCell align="center">{getRoundedOffNumberWithCommas(row.penaltyCharges)}</TableCell>
                {isSingleAccSelected &&
                  <TableCell align="center">
                    <InputBase
                      type="number"
                      value={t(String(row.customCcUtilizationPercentage || ""))}
                      onChange={(e) => handleCCUtilisationInputChange(row, e.target.value)}
                      size="small"
                      disabled={!isSingleAccSelected}
                      fullWidth
                      placeholder="0.0"
                      inputProps={{max: 100.0, min: 0.0, step: 0.1}}
                    />
                  </TableCell>
                }
              </TableRow>
            ))}
            <TableRow>
              <TableCell colSpan={2}>{t("bankStatementTable.rows.total")}</TableCell>
              <TableCell align="center" >{getRoundedOffNumberWithCommas(totalValues?.debit, 2)}</TableCell>
              <TableCell align="center" >{getRoundedOffNumberWithCommas(totalValues?.credit, 2)}</TableCell>
              <TableCell align="center" >{getRoundedOffNumberWithCommas(totalValues?.debitInterBank, 2)}</TableCell>
              <TableCell align="center" >{getRoundedOffNumberWithCommas(totalValues?.creditInterBank, 2)}</TableCell>
              <TableCell align="center" >
                {getRoundedOffNumberWithCommas(totalValues?.debitNonBusinessTransaction, 2)}
              </TableCell>
              <TableCell align="center" >{getRoundedOffNumberWithCommas(totalValues?.debitInterFirm, 2)}</TableCell>
              <TableCell align="center" >{getRoundedOffNumberWithCommas(totalValues?.creditInterFirm, 2)}</TableCell>
              <TableCell align="center" >
                {getRoundedOffNumberWithCommas(totalValues?.creditNonBusinessTransaction, 2)}
              </TableCell>
              <TableCell align="center">
                {getRoundedOffNumberWithCommas(totalValues?.totalLoanDisbursements, 2)}
              </TableCell>
              <TableCell align="center">
                {getRoundedOffNumberWithCommas(totalValues?.totalEmiTracing, 2)}
              </TableCell>
              <TableCell align="center" >{totalValues?.iwReturn}</TableCell>
              <TableCell align="center" >{totalValues?.owReturn}</TableCell>
              <TableCell align="center" >{getRoundedOffNumberWithCommas(totalValues?.credit, 2)}</TableCell>
              <TableCell align="center" >{getRoundedOffNumberWithCommas(totalValues?.penalMABCharges, 2)}</TableCell>
              {isSingleAccSelected &&
                <TableCell align="center">
                  {getRoundedOffNumberWithCommas((totalValues?.ccUtilization) / rowData.length, 2)}
                </TableCell>
              }
            </TableRow>
            <TableRow>
              <TableCell colSpan={2}>{t("bankStatementTable.rows.totalInterBank")}</TableCell>
              <TableCell align="center">
                <InputBase
                  type="number"
                  value={totalValues.debitInterBank || ""}
                  onChange={(e) => handleTotalAllRow("debitInterBank", e.target.value)}
                  style={{width: "100%"}}
                  size="small"
                  fullWidth
                  placeholder="0.00"
                />

              </TableCell>
              <TableCell align="center">
                <InputBase
                  type="number"
                  value={totalValues.creditInterBank || ""}
                  onChange={(e) =>handleTotalAllRow("creditInterBank",e.target.value)
                  }
                  size="small"
                  fullWidth
                  placeholder="0.00"
                  inputProps={{maxLength: 10}}
                />
              </TableCell>
            </TableRow>
            <TableRow>
              <TableCell colSpan={2}>{t("bankStatementTable.rows.totalInterfirm")}</TableCell>
              <TableCell align="center">
                <InputBase
                  type="number"
                  value={totalValues.debitInterFirm || ""}
                  onChange={(e) =>handleTotalAllRow("debitInterFirm",e.target.value)
                  }
                  size="small"
                  fullWidth
                  placeholder="0.00"
                  inputProps={{maxLength: 10}}
                />
              </TableCell>
              <TableCell align="center">
                <InputBase
                  type="number"
                  value={totalValues.creditInterFirm || ""}
                  onChange={(e) =>handleTotalAllRow("creditInterFirm",e.target.value)
                  }
                  size="small"
                  fullWidth
                  placeholder="0.00"
                  inputProps={{maxLength: 10}}
                />
              </TableCell>
            </TableRow>
            {isSingleAccSelected &&
              <TableRow>
                <TableCell colSpan={2}>{t("bankStatementTable.rows.loanReceiptsPayments")}</TableCell>
                <TableCell align="center">
                  <InputBase
                    type="number"
                    value={totalValues.totalEmiTracing || ""}
                    onChange={(e) =>handleTotalAllRow("totalEmiTracing",e.target.value)
                    }
                    size="small"
                    fullWidth
                    placeholder="0.00"
                    inputProps={{maxLength: 10}}
                  />
                </TableCell>
                <TableCell align="center">
                  <InputBase
                    type="number"
                    value={totalValues.totalLoanDisbursements || ""}
                    onChange={(e) =>handleTotalAllRow("totalLoanDisbursements",e.target.value)
                    }
                    size="small"
                    fullWidth
                    placeholder="0.00"
                    inputProps={{maxLength: 10}}
                  />
                </TableCell>
              </TableRow>
            }
            <TableRow>
              <TableCell colSpan={2}>{t("bankStatementTable.rows.totalNonBusinessTransaction")}</TableCell>
              <TableCell align="center">
                <InputBase
                  type="number"
                  value={totalValues.debitNonBusinessTransaction || ""}
                  onChange={(e) =>handleTotalAllRow("debitNonBusinessTransaction",e.target.value)
                  }
                  size="small"
                  fullWidth
                  placeholder="0.00"
                />
              </TableCell>
              <TableCell align="center">
                <InputBase
                  type="number"
                  value={totalValues.creditNonBusinessTransaction || ""}
                  onChange={(e) => handleTotalAllRow("totalCreditNonBusinessTransaction", e.target.value)}
                  size="small"
                  fullWidth
                  placeholder="0.00"
                />
              </TableCell>
            </TableRow>
            <TableRow>
              <TableCell colSpan={2}>{t("bankStatementTable.rows.netBanking")}</TableCell>
              <TableCell align="center">
                {getRoundedOffNumberWithCommas(
                  totalValues.debit -
                  totalValues.debitInterBank
                  - totalValues.debitInterFirm
                  - totalValues.debitNonBusinessTransaction,
                  2)}
              </TableCell>
              <TableCell align="center">
                {getRoundedOffNumberWithCommas(
                  totalValues.credit
                  - totalValues.creditInterBank
                  - totalValues.creditInterFirm
                  - totalValues.creditNonBusinessTransaction,
                  2)}
              </TableCell>
            </TableRow>
            <TableRow>
              <TableCell colSpan={2}>{t("bankStatementTable.rows.monthlyAverage")}</TableCell>
              <TableCell align="center">
                {getRoundedOffNumberWithCommas(
                  (totalValues.debit
                    - totalValues.debitInterBank
                    - totalValues.debitInterFirm
                    - totalValues.debitNonBusinessTransaction) / rowData.length,
                  2)}
              </TableCell>
              <TableCell align="center">
                {getRoundedOffNumberWithCommas(
                  (totalValues.credit
                    - totalValues.creditInterBank
                    - totalValues.creditInterFirm
                    - totalValues.creditNonBusinessTransaction) / rowData.length, 2)}
              </TableCell>
            </TableRow>
            {getAverageAndMedianRows()}
          </TableBody>
        </Table>
      </TableContainer>
      <div className="flex-row-justify-center m-10">
        <Button
          onClick={handleSubmitNumericEntries}
          variant="contained"
          size="small"
          disabled={!isSingleAccSelected}
        >
          {t("bankStatementTable.submit")}
        </Button>
      </div>
    </>
  );
}
