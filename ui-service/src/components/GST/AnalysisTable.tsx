import React from "react";
import {InputBase, TableCell, TableRow} from "@mui/material";
import {getRoundedOffNumberWithCommas} from "utils/numberFormat";
import {useTranslation} from "react-i18next";

const DEFAULT_COLSPAN = 2;

interface AnalysisTableProps {
  totalInterStateCustomersAnalysis: number,
  totalInterStateSuppliersAnalysis: number,
  customerInterfirm: number,
  supplierInterfirm: number,
  customerCircularOrOthers: number,
  supplierCircularOrOthers: number,
  customerNetTotal: number,
  supplierNetTotal: number,
  customerMonths: number,
  supplierMonths: number,
  supplierMonthlyTo: number,
  customerMonthlyTo: number,
  setCustomerCircularOrOthers: React.Dispatch<React.SetStateAction<number>>,
  setSupplierCircularOrOthers: React.Dispatch<React.SetStateAction<number>>,
  setCustomerMonths: React.Dispatch<React.SetStateAction<number>>,
  setSupplierMonths: React.Dispatch<React.SetStateAction<number>>,
}

export function AnalysisTable(props: AnalysisTableProps) {
  const {
    totalInterStateCustomersAnalysis,
    totalInterStateSuppliersAnalysis,
    customerInterfirm,
    supplierInterfirm,
    customerCircularOrOthers,
    supplierCircularOrOthers,
    customerNetTotal,
    supplierNetTotal,
    customerMonths,
    supplierMonths,
    supplierMonthlyTo,
    customerMonthlyTo,
    setCustomerCircularOrOthers,
    setSupplierCircularOrOthers,
    setCustomerMonths,
    setSupplierMonths,
  } = props;
  const {t} = useTranslation();

  return (
    <>
      <TableRow>
        <TableCell></TableCell>
        <TableCell align="center">{t("analysisTable.interstate")}</TableCell>
        <TableCell align="center">{t(getRoundedOffNumberWithCommas(totalInterStateCustomersAnalysis))}</TableCell>
        <TableCell align="center">{t(getRoundedOffNumberWithCommas(totalInterStateSuppliersAnalysis))}</TableCell>
        <TableCell colSpan={DEFAULT_COLSPAN}></TableCell>
      </TableRow>
      <TableRow>
        <TableCell></TableCell>
        <TableCell align="center">{t("analysisTable.circularOrOthers")}</TableCell>
        <TableCell align="center">
          <InputBase
            type="number"
            value={t(String(customerCircularOrOthers))}
            onChange={(e) => setCustomerCircularOrOthers(parseInt(e.target.value))}
            size="small"
            placeholder="0"
          />
        </TableCell>
        <TableCell align="center">
          <InputBase
            type="number"
            value={t(String(supplierCircularOrOthers))}
            onChange={(e) => setSupplierCircularOrOthers(parseInt(e.target.value))}
            size="small"
            placeholder="0"
          />
        </TableCell>
        <TableCell colSpan={DEFAULT_COLSPAN}></TableCell>
      </TableRow>
      <TableRow>
        <TableCell></TableCell>
        <TableCell align="center">{t("analysisTable.interfirm")}</TableCell>
        <TableCell align="center">{t(getRoundedOffNumberWithCommas(customerInterfirm,2))}</TableCell>
        <TableCell align="center">{t(getRoundedOffNumberWithCommas(supplierInterfirm,2))}</TableCell>
        <TableCell colSpan={2}></TableCell>
      </TableRow>
      <TableRow>
        <TableCell></TableCell>
        <TableCell align="center">{t("analysisTable.netTotal")}</TableCell>
        <TableCell align="center">{t(getRoundedOffNumberWithCommas(customerNetTotal))}</TableCell>
        <TableCell align="center">{t(getRoundedOffNumberWithCommas(supplierNetTotal))}</TableCell>
        <TableCell colSpan={DEFAULT_COLSPAN}></TableCell>
      </TableRow>
      <TableRow>
        <TableCell></TableCell>
        <TableCell align="center">{t("analysisTable.totalNumberOfMonths")}</TableCell>
        <TableCell align="center">
          <InputBase
            type="number"
            value={t(String(customerMonths))}
            onChange={(e) => setCustomerMonths(parseInt(e.target.value))}
            size="small"
            placeholder="0"
          />
        </TableCell>
        <TableCell align="center">
          <InputBase
            type="number"
            value={t(String(supplierMonths))}
            onChange={(e) => setSupplierMonths(parseInt(e.target.value))}
            size="small"
            placeholder="0"
          />
        </TableCell>
        <TableCell colSpan={DEFAULT_COLSPAN}></TableCell>
      </TableRow>
      <TableRow>
        <TableCell></TableCell>
        <TableCell align="center">{t("analysisTable.monthlyTO")}</TableCell>
        <TableCell align="center">
          {t(String(!isFinite(customerMonthlyTo) ? "" : getRoundedOffNumberWithCommas(customerMonthlyTo)))}
        </TableCell>
        <TableCell align="center">
          {t(String(!isFinite(supplierMonthlyTo) ? "" : getRoundedOffNumberWithCommas(supplierMonthlyTo)))}
        </TableCell>
        <TableCell colSpan={DEFAULT_COLSPAN}></TableCell>
      </TableRow>
      <TableRow>
        <TableCell></TableCell>
        <TableCell align="center">{t("analysisTable.annualized")}</TableCell>
        <TableCell align="center">
          {t(String(!isFinite(customerMonthlyTo) ? "" : getRoundedOffNumberWithCommas(customerMonthlyTo * 12)))}
        </TableCell>
        <TableCell align="center">
          {t(String(!isFinite(supplierMonthlyTo) ? "" : getRoundedOffNumberWithCommas(supplierMonthlyTo * 12)))}
        </TableCell>
        <TableCell colSpan={DEFAULT_COLSPAN}></TableCell>
      </TableRow>
    </>
  );
}
