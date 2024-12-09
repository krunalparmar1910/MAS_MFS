import React, {useEffect, useState} from "react";
import {
  Checkbox,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import {useTranslation} from "react-i18next";
import {MonthlyDetailsDTO} from "interfaces/banking";
import {ConsolidateObject} from "pages/Banking";
import TableRowsLoader from "utils/components/TableRowsLoader";
import {getMonthYear} from "utils/dateFormat";

interface RowsData {
  srNo: number,
  name: string,
  bankName: string,
  bankAccNo: string,
  consolidate: boolean,
  masFinId: string,
  perfiosTxnId: string,
  yearMonthFrom: string,
  yearMonthTo: string,
}

interface BankingDetailsProps {
  consolidateCheck: Map<string, ConsolidateObject>;
  setConsolidateCheck: (map: Map<string, ConsolidateObject>) => void;
  allData: MonthlyDetailsDTO | undefined;
  masFinId: string;
  loading: boolean;
}

export function BankAccountDetails(props: Readonly<BankingDetailsProps>) {
  const {
    consolidateCheck,
    setConsolidateCheck,
    allData,
    masFinId,
    loading,
  } = props;

  const [rowsData, setRowsData] = useState<RowsData[]>([]);

  const handleCheckbox = (key: string) => {
    const tempMap: Map<string, ConsolidateObject> = new Map(consolidateCheck);
    tempMap.set(
      key,
      {
        consolidate: !consolidateCheck.get(key)?.consolidate,
        customerTransactionId: consolidateCheck.get(key)?.customerTransactionId || "",
      },
    );
    setConsolidateCheck(tempMap);
  };

  useEffect(() => {
    const temp: RowsData[] = allData?.bankDetailList?.map((item, index) => {
      return {
        srNo: index + 1,
        name: item?.customerDetails.name,
        bankName: item?.bankName,
        bankAccNo: item?.accountNumber,
        consolidate: false,
        masFinId: masFinId,
        perfiosTxnId: item?.perfiosTransactionId,
        yearMonthFrom: item.yearMonthFrom,
        yearMonthTo: item.yearMonthTo,
      };
    }) ?? [];
    setRowsData(temp);
  }, [allData]);

  const {t} = useTranslation();

  return (
    <div>
      <TableContainer className="mv-10" component={Paper}>
        <Table aria-label="customized table">
          <TableHead>
            <TableRow>
              <TableCell align="center">
                {t("bankAccountDetails.columns.srNo")}
              </TableCell>
              <TableCell align="center">
                {t("bankAccountDetails.columns.accHoldername")}
              </TableCell>
              <TableCell align="center">
                {t("bankAccountDetails.columns.bankName")}
              </TableCell>
              <TableCell align="center">
                {t("bankAccountDetails.columns.bankAccNo")}
              </TableCell>
              <TableCell align="center">
                {t("bankAccountDetails.columns.yearMonthFrom")}
              </TableCell>
              <TableCell align="center">
                {t("bankAccountDetails.columns.yearMonthTo")}
              </TableCell>
              <TableCell align="center">
                {t("bankAccountDetails.columns.consolidate")}
              </TableCell>
            </TableRow>
          </TableHead>
          {loading && <TableRowsLoader rowsNum={2} colsNum={7}/>}
          <TableBody>
            {rowsData?.map((row) => (
              <TableRow key={row.srNo} hover={false}>
                <TableCell align="center" component="th" scope="row">
                  {t(String(row.srNo))}
                </TableCell>
                <TableCell align="center">{t(row.name)}</TableCell>
                <TableCell align="center">{t(row.bankName)}</TableCell>
                <TableCell align="center">{t(row.bankAccNo)}</TableCell>
                <TableCell align="center">{t(getMonthYear(row.yearMonthFrom))}</TableCell>
                <TableCell align="center">{t(getMonthYear(row.yearMonthTo))}</TableCell>
                <TableCell align="center">
                  <Checkbox
                    onChange={() => handleCheckbox(row.masFinId+"+"+row.perfiosTxnId+"+"+row.bankAccNo)}
                    checked={consolidateCheck.get(row.masFinId+"+"+row.perfiosTxnId+"+"+row.bankAccNo)?.consolidate}
                  />
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
}
