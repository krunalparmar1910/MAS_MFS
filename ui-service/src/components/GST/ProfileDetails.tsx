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
import {GSTReport, GstReportProfileDetail} from "interfaces/gst";
import {useTranslation} from "react-i18next";
import TableRowsLoader from "utils/components/TableRowsLoader";

interface RowsData {
  srNo: number;
  detailFor: "Applicant" | "Firm";
  legalName: string;
  tradeName: string;
  gstNo: string;
  status: string;
  consolidate: boolean;
}

interface GstDetailsProps {
  consolidateCheck: Map<string, boolean>;
  setConsolidateCheck: React.Dispatch<React.SetStateAction<Map<string, boolean>>>;
  allData?: GSTReport;
  loading: boolean;
}

export function ProfileDetails(props: GstDetailsProps) {
  const {
    consolidateCheck,
    setConsolidateCheck,
    allData,
    loading,
  } = props;

  function createData(
    srNo: number,
    detailFor:
        | "Applicant"
        | "Firm",
    legalName: string,
    tradeName: string,
    gstNo: string,
    status: string,
    consolidate: boolean,
  ) {
    return {srNo, detailFor, legalName, tradeName, gstNo, status, consolidate};
  }

  const [rowsData, setRowsData] = useState<RowsData[]>([]);

  const handleCheckbox = (curGstNo: string, value: boolean) => {
    const tempMap = new Map(consolidateCheck);

    tempMap.set(curGstNo, value);
    setConsolidateCheck(tempMap);
  };

  useEffect(() => {
    if (Object.keys(allData?.gst3BSalesReports || {}).length>0){
      allData?.gstReportProfileDetails
        .filter((item: GstReportProfileDetail) =>
          Object.prototype.hasOwnProperty.call(allData.gst3BSalesReports, item?.gstNumber),
        );
    }
    const temp = [
      ...allData?.gstReportProfileDetails
        .map((item: GstReportProfileDetail, i: number) => {
          return createData(i + 1, "Firm", item?.legalName, item?.tradeName, item?.gstNumber, item?.status, false);
        }) ?? [],
    ];
    setRowsData(temp);
  }, [allData]);

  const {t} = useTranslation();

  return (
    <>
      <TableContainer className="mv-10" component={Paper}>
        <Table aria-label="customized table">
          <TableHead>
            <TableRow>
              <TableCell align="center" colSpan={6}>{t("gstinDetails.profileDetails")}</TableCell>
            </TableRow>
            <TableRow>
              <TableCell align="center">{t("gstDetails.columns.srNo")}</TableCell>
              <TableCell align="center">{t("gstDetails.columns.legalName")}</TableCell>
              <TableCell align="center">{t("gstDetails.columns.tradeName")}</TableCell>
              <TableCell align="center">{t("gstDetails.columns.gstNo")}</TableCell>
              <TableCell align="center">{t("gstDetails.columns.status")}</TableCell>
              <TableCell align="center">{t("gstDetails.columns.consolidate")}</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {loading && <TableRowsLoader rowsNum={2} colsNum={6}/>}
            {rowsData?.map((row) => (
              <TableRow key={row.srNo} hover={false}>
                <TableCell align="center" component="th" scope="row">{t(String(row.srNo))}</TableCell>
                <TableCell align="center">{t(row.legalName)}</TableCell>
                <TableCell align="center">{t(row.tradeName)}</TableCell>
                <TableCell align="center">{t(row.gstNo)}</TableCell>
                <TableCell align="center">{t(row.status)}</TableCell>
                <TableCell align="center">
                  <Checkbox
                    onChange={(value) => handleCheckbox(row.gstNo, value.target.checked)}
                    checked={consolidateCheck.get(row.gstNo)}
                    disabled={Object.keys(allData?.gst3BSalesReports ?? {}).length === 0}
                  />
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </>
  );
}
