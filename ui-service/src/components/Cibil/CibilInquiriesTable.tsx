import React from "react";
import {Paper, Table, TableContainer, TableHead, TableRow, TableCell, TableBody} from "@mui/material";
import {AccountInquiriesHistoricalData} from "interfaces/cibil";
import {useTranslation} from "react-i18next";

interface CibilInquiriesTableProps {
  accountInquiriesHistoricalData: AccountInquiriesHistoricalData;
}

interface CibilInquiriesTableRow {
  particular: string,
  inquiries: number,
  accountsOpened: number,
}

export function CibilInquiriesTable(props: CibilInquiriesTableProps) {
  const {t} = useTranslation();
  const {accountInquiriesHistoricalData} = props;

  const rows: CibilInquiriesTableRow[] = [
    {
      particular: "Last 30 days",
      inquiries: accountInquiriesHistoricalData.inquiriesLast30DaysFromCibilReportDate,
      accountsOpened: accountInquiriesHistoricalData.accountsOpenedLast30DaysFromCibilReportDate,
    },
    {
      particular: "Last 60 days",
      inquiries: accountInquiriesHistoricalData.inquiriesLast60DaysFromCibilReportDate,
      accountsOpened: accountInquiriesHistoricalData.accountsOpenedLast60DaysFromCibilReportDate,
    },
    {
      particular: "Last 120 days",
      inquiries: accountInquiriesHistoricalData.inquiriesLast120DaysFromCibilReportDate,
      accountsOpened: accountInquiriesHistoricalData.accountsOpenedLast120DaysFromCibilReportDate,
    },
    {
      particular: "Last 1 year",
      inquiries: accountInquiriesHistoricalData.inquiriesLast365DaysFromCibilReportDate,
      accountsOpened: accountInquiriesHistoricalData.accountsOpenedLast365DaysFromCibilReportDate,
    },
    {
      particular: "Last 2 years",
      inquiries: accountInquiriesHistoricalData.inquiriesLast730DaysFromCibilReportDate,
      accountsOpened: accountInquiriesHistoricalData.accountsOpenedLast730DaysFromCibilReportDate,
    },
  ];

  return (
    <TableContainer component={Paper}>
      <Table aria-label="customized table">
        <TableHead>
          <TableRow>
            <TableCell>{t("cibil.inquiries.columns.particulars")}</TableCell>
            <TableCell align="center">{t("cibil.inquiries.columns.inquiries")}</TableCell>
            <TableCell align="center">{t("cibil.inquiries.columns.accountsOpened")}</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <TableRow key={row.particular}>
              <TableCell>{row.particular}</TableCell>
              <TableCell align="center">{row.inquiries}</TableCell>
              <TableCell align="center">{row.accountsOpened}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
