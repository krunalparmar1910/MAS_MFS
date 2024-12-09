import React, {useMemo, useRef, useState} from "react";
import {ColDef, ModuleRegistry, ValueFormatterParams, ValueGetterParams} from "@ag-grid-community/core";
import {AgGridReact} from "@ag-grid-community/react";
import "@ag-grid-community/styles/ag-grid.css";
import "@ag-grid-community/styles/ag-theme-quartz.css";
import {Button, Checkbox, InputBase} from "@mui/material";
import {ICellEditorParams, ICellRendererParams} from "ag-grid-community";
import {AccountBorrowingDetails, BorrowingDetailsRow, CibilReport} from "interfaces/cibil";
import {SnackbarProvider, enqueueSnackbar} from "notistack";
import {ClientSideRowModelModule} from "@ag-grid-community/client-side-row-model";
import {useTranslation} from "react-i18next";
import "styles/bankTxnTable.scss";
import {updateCibilBorrowingDetails} from "utils/api/request";
import {getRoundedOffNumberWithCommas} from "utils/numberFormat";
import * as XLSX from "xlsx";

ModuleRegistry.registerModules([ClientSideRowModelModule]);

interface PropsType {
  borrowingDetails: AccountBorrowingDetails[]
  onUpdateRow: () => void
  onCheckBoxUpdate: (row: BorrowingDetailsRow, columnField: string, value: boolean) => void
}

export function CibilBorrowingDetailsTable(props: PropsType) {
  const {borrowingDetails, onUpdateRow, onCheckBoxUpdate} = props;
  const {t} = useTranslation();
  const gridRef = useRef<AgGridReact>(null);
  const editedRows = useRef<Set<number>>(new Set<number>());
  const [rowData, setRowData] = useState<BorrowingDetailsRow[]>([]);

  const handleInputChange = (
    row: BorrowingDetailsRow, columnField: string, value: boolean | string, rowNodeId: string) => {
    typeof value === "boolean" && onCheckBoxUpdate(row, columnField, value);
    const newSet = new Set(editedRows.current);
    newSet.add(row.id);
    editedRows.current = newSet;
    gridRef.current?.api.getRowNode(rowNodeId)?.setDataValue(columnField, value);
  };

  const columnDefs: ColDef[] = [
    {
      headerName: t("bankTxnTable.columns.srNo"),
      headerTooltip: t("bankTxnTable.columns.srNo"),
      field: "id",
      tooltipField: "id",
      width: 80,
      filter: false,
      sortable: true,
      pinned: "left",
    },
    {
      headerName: t("cibil.borrowingDetails.columns.addInTotal"),
      headerTooltip: t("cibil.borrowingDetails.columns.addInTotal"),
      field: "addInTotal",
      tooltipField: "addInTotal",
      width: 140,
      cellStyle: {textAlign: "center"},
      valueFormatter: (p) => p.value || "-",
      cellRenderer: (params: ICellRendererParams) => {
        return (
          <Checkbox
            checked={params.value}
            onChange={(event) =>
              handleInputChange(params.data, params.colDef?.field!, event.target.checked, params.node.id!)}
          />
        );
      },
      menuTabs: [],
    },
    {
      headerName: t("cibil.borrowingDetails.columns.typeOfLoan"),
      headerTooltip: t("bankTxnTable.columns.srNo"),
      field: "typeOfLoan",
      cellRenderer: (params: ICellRendererParams) => (
        <InputBase
          value={params.value || ""}
          onChange={(e) =>
            handleInputChange(params.data, params.colDef?.field || "", e.target.value, params.node.id!)
          }
          placeholder="Enter loan type"
          size="small"
          multiline
          fullWidth
          sx={{backgroundColor: "white"}}
        />
      ),
      cellStyle: {display: "flex", alignItems: "center", padding: "0px"},
      filter: "agTextColumnFilter",
      filterParams: {maxNumConditions: 1},
      sortable: true,
    },
    {
      headerName: t("cibil.borrowingDetails.columns.sanctionedAmount"),
      headerTooltip: t("cibil.borrowingDetails.columns.sanctionedAmount"),
      field: "sanctionedAmount",
      tooltipField: "sanctionedAmount",
      width: 200,
      cellStyle: {textAlign: "center"},
      valueFormatter: (p) => p.value ? getRoundedOffNumberWithCommas(p.value, 2) : "-",
      // pinned: "left",
      filter: "agNumberColumnFilter",
      filterParams: {
        maxNumConditions: 1,
      },
      sortable: true,
    },
    {
      headerName: t("cibil.borrowingDetails.columns.outstandingAmount"),
      headerTooltip: t("cibil.borrowingDetails.columns.outstandingAmount"),
      field: "outstandingAmount",
      tooltipField: "outstandingAmount",
      width: 170,
      cellStyle: {textAlign: "center"},
      valueFormatter: (p) => p.value ? getRoundedOffNumberWithCommas(p.value, 2) : "-",
      // pinned: "left",
      filter: "agNumberColumnFilter",
      filterParams: {
        maxNumConditions: 1,
      },
      sortable: true,
    },
    {
      headerName: t("cibil.borrowingDetails.columns.sanctionedDate"),
      headerTooltip: t("cibil.borrowingDetails.columns.sanctionedDate"),
      field: "sanctionedDate",
      tooltipField: "sanctionedDate",
      width: 180,
      cellStyle: {textAlign: "center"},
      valueFormatter: (p) => p.value || "-",
      // pinned: "left",
      filter: "agDateColumnFilter",
      filterParams: {
        maxNumConditions: 1,
      },
      sortable: true,
    },
    {
      headerName: t("cibil.borrowingDetails.columns.status"),
      headerTooltip: t("cibil.borrowingDetails.columns.status"),
      field: "cibilStatus",
      tooltipField: "status",
      width: 130,
      cellStyle: {textAlign: "center"},
      valueFormatter: (p) => p.value || "-",
      // pinned: "left",
      cellRendererParams: (params: ICellRendererParams) => {
        return (
          <>
            {((params.value["suitFiledOrWilfulDefault"] &&
              params.value["suitFiledOrWilfulDefault"] !== "No Suit Filed") &&
              <div>
                {`${t("cibil.suitFiledWilfulDefault")} : ${params.value["suitFiledOrWilfulDefault"]}`}
              </div>)}
            {(params.value["creditFacilityStatus"] &&
              <div>{`${t("cibil.creditFacilityStatus")} : ${params.value["creditFacilityStatus"]}`}</div>)}
            {(params.value["creditFacilityStatus"] &&
              <div>
                {`${t("cibil.writtenOffAmtPrincipal")} : ${params.value["writtenOffAmountPrincipal"]}`}
              </div>)}
          </>
        );
      },
      filter: "agTextColumnFilter",
      filterParams: {
        maxNumConditions: 1,
      },
      sortable: true,
    },
    {
      headerName: t("cibil.borrowingDetails.columns.suitFiledStatus"),
      headerTooltip: t("cibil.borrowingDetails.columns.suitFiledStatus"),
      field: "suitFiledOrWilfulDefault",
      tooltipField: "suitFiledOrWilfulDefault",
      width: 140,
      cellStyle: {textAlign: "center"},
      valueFormatter: (p) => p.value || "-",
      // pinned: "left",
      cellRendererParams: (params: ICellRendererParams) => params.value["creditFacilityStatus"],
      filter: "agTextColumnFilter",
      filterParams: {
        maxNumConditions: 1,
      },
      sortable: true,
    },
    {
      headerName: t("cibil.borrowingDetails.columns.bankNbfc"),
      headerTooltip: t("cibil.borrowingDetails.columns.bankNbfc"),
      field: "bankNbfc",
      tooltipField: "bankNbfc",
      width: 160,
      cellStyle: {textAlign: "center"},
      valueFormatter: (p) => p.value || "-",
      filter: "agTextColumnFilter",
      filterParams: {
        maxNumConditions: 1,
      },
      sortable: true,
    },
    {
      headerName: t("cibil.borrowingDetails.columns.ownership"),
      headerTooltip: t("cibil.borrowingDetails.columns.ownership"),
      field: "ownership",
      tooltipField: "ownership",
      width: 150,
      cellStyle: {textAlign: "center"},
      valueFormatter: (p) => p.value || "-",
      filter: "agTextColumnFilter",
      filterParams: {
        maxNumConditions: 1,
      },
      sortable: true,
    },
    {
      headerName: t("cibil.borrowingDetails.columns.overdue"),
      headerTooltip: t("cibil.borrowingDetails.columns.overdue"),
      field: "overdue",
      tooltipField: "overdue",
      width: 150,
      cellStyle: {textAlign: "center"},
      valueFormatter: (p) => p.value ? getRoundedOffNumberWithCommas(p.value, 2) : "-",
      filter: "agNumberColumnFilter",
      filterParams: {
        maxNumConditions: 1,
      },
      sortable: true,
    },
    {
      headerName: t("cibil.borrowingDetails.columns.tenure"),
      headerTooltip: t("cibil.borrowingDetails.columns.tenure"),
      field: "tenure",
      tooltipField: "tenure",
      cellRenderer: (params: ICellRendererParams) => {
        return (
          <>
            <InputBase
              value={params.value}
              onChange={(e) =>
                handleInputChange(params.data, params?.colDef?.field!, e.target.value, params.node.id!)}
              size="small"
              placeholder="-"
              fullWidth
              multiline
              sx={{backgroundColor: "white"}}
            />
          </>
        );
      },
      width: 100,
      cellStyle: () => {
        return {
          display: "flex",
          padding: "0px",
        };
      },
      valueFormatter: (p) => p.value || "-",
      filter: "agNumberColumnFilter",
      filterParams: {
        maxNumConditions: 1,
      },
      sortable: true,
      editable: true,
    },
    {
      headerName: t("cibil.borrowingDetails.columns.last12MonthsDPD"),
      headerTooltip: t("cibil.borrowingDetails.columns.last12MonthsDPD"),
      field: "last12MonthsDPD",
      tooltipField: "last12MonthsDPD",
      width: 200,
      cellStyle: {textAlign: "center"},
      valueFormatter: (p) => p.value || "-",
      filter: "agNumberColumnFilter",
      filterParams: {
        maxNumConditions: 1,
      },
      sortable: true,
    },
    {
      headerName: t("cibil.borrowingDetails.columns.overallDPD"),
      headerTooltip: t("cibil.borrowingDetails.columns.overallDPD"),
      field: "overallDPD",
      tooltipField: "overallDPD",
      width: 150,
      cellStyle: {textAlign: "center"},
      valueFormatter: (p) => p.value || "-",
      filter: "agNumberColumnFilter",
      filterParams: {
        maxNumConditions: 1,
      },
      sortable: true,
    },
    {
      headerName: t("cibil.borrowingDetails.columns.delayInMonths"),
      headerTooltip: t("cibil.borrowingDetails.columns.delayInMonths"),
      field: "delayInMonthsLast12Months",
      tooltipField: "delayInMonthsLast12Months",
      width: 200,
      cellStyle: {textAlign: "center"},
      valueFormatter: (p) => p.value || "-",
      filter: "agNumberColumnFilter",
      filterParams: {
        maxNumConditions: 1,
      },
      sortable: true,
    },
    {
      headerName: t("cibil.borrowingDetails.columns.reportedDate"),
      headerTooltip: t("cibil.borrowingDetails.columns.reportedDate"),
      field: "reportedDate",
      tooltipField: "reportedDate",
      width: 170,
      cellStyle: {textAlign: "center"},
      valueGetter: (params: ValueGetterParams<BorrowingDetailsRow>) => {
        if (params.data?.reportedDate) {
          const newDate = params.data.reportedDate.split("-");
          return new Date(Number(newDate[2]), Number(newDate[1]) - 1, Number(newDate[0]), 0, 0, 0, 0);
        } else {
          return "-";
        }
      },
      valueFormatter: (params: ValueFormatterParams<BorrowingDetailsRow>) => {
        return params.data?.reportedDate || "-";
      },
      filter: "agDateColumnFilter",
      filterParams: {
        maxNumConditions: 1,
      },
      sortable: true,
    },
    {
      headerName: t("cibil.borrowingDetails.columns.emi"),
      headerTooltip: t("cibil.borrowingDetails.columns.emi"),
      field: "emi",
      tooltipField: "emi",
      cellRenderer: (params: ICellRendererParams) => {
        return (
          <>
            <InputBase
              value={params.value}
              onChange={(e) =>
                handleInputChange(params.data, params?.colDef?.field!, e.target.value, params.node.id!)}
              size="small"
              placeholder="-"
              fullWidth
              multiline
              sx={{backgroundColor: "white"}}
            />
          </>
        );
      },
      width: 130,
      cellStyle: () => {
        return {
          display: "flex",
          padding: "0px",
        };
      },
      valueFormatter: (p) => p.value ? getRoundedOffNumberWithCommas(p.value, 2) : "-",
      filter: "agNumberColumnFilter",
      filterParams: {
        maxNumConditions: 1,
      },
      sortable: true,
      editable: true,
    },
    {
      headerName: t("cibil.borrowingDetails.columns.emiSystem"),
      headerTooltip: t("cibil.borrowingDetails.columns.emiSystem"),
      field: "emiSystem",
      tooltipField: "emiSystem",
      width: 150,
      cellStyle: {textAlign: "center"},
      valueGetter: (params: ValueGetterParams<BorrowingDetailsRow>) => {
        return params.data?.emi ?? params.data?.emiSystem ?? "-";
      },
      valueFormatter: (p) => p.value ? getRoundedOffNumberWithCommas(p.value, 0) : "-",
      filter: "agNumberColumnFilter",
      filterParams: {
        maxNumConditions: 1,
      },
      sortable: true,
    },
    {
      headerName: t("cibil.borrowingDetails.columns.interestRate"),
      headerTooltip: t("cibil.borrowingDetails.columns.interestRate"),
      field: "interestRate",
      tooltipField: "interestRate",
      width: 150,
      cellStyle: {textAlign: "center"},
      valueFormatter: (p) => p.value || "-",
      filter: "agNumberColumnFilter",
      filterParams: {
        maxNumConditions: 1,
      },
      sortable: true,
    },
    {
      headerName: t("cibil.borrowingDetails.columns.comment"),
      headerTooltip: t("cibil.borrowingDetails.columns.comment"),
      field: "comment",
      tooltipField: "comment",
      cellStyle: () => {
        return {
          display: "flex",
          padding: "0px",
        };
      },
      cellRenderer: (params: ICellRendererParams) => {
        return (
          <>
            <InputBase
              value={params.value}
              onChange={(e) =>
                handleInputChange(params.data, params?.colDef?.field!, e.target.value, params.node.id!)}
              size="small"
              placeholder="-"
              fullWidth
              multiline
              sx={{backgroundColor: "white"}}
            />
          </>
        );
      },
      filter: "agTextColumnFilter",
      filterParams: {
        maxNumConditions: 1,
      },
      sortable: true,
    },
    {
      headerName: t("cibil.borrowingDetails.columns.duplicate"),
      headerTooltip: t("cibil.borrowingDetails.columns.duplicate"),
      field: "duplicate",
      tooltipField: "duplicate",
      width: 120,
      cellStyle: {textAlign: "center"},
      valueFormatter: (p) => p.value || "-",
      cellRenderer: (params: ICellRendererParams) => {
        return (
          <Checkbox
            checked={params.value}
            onChange={(event) =>
              handleInputChange(params.data, params.colDef?.field!, event.target.checked, params.node.id!)}
          />
        );
      },
      menuTabs: [],
    },
  ];

  const defaultColDef = useMemo<ColDef>(() => {
    return {
      sortable: true,
      resizable: true,
      editable: false,
    };
  }, []);

  const onGridReady = () => {
    setRowData(borrowingDetails?.map((row, index) => {
      return {
        ...row,
        id: index + 1,
        isNew: true,
        isEdited: false,
      };
    }) || []);
  };

  const handleSubmitTransactionComments = () => {
    editedRows.current.forEach((rowId) => {
      let updatedRow: any | null = null;
      gridRef.current?.api.forEachNode(tempRow => tempRow.data.id === rowId && (updatedRow = tempRow.data));
      const accountId = updatedRow.accountId;
      let updatedRowNew: BorrowingDetailsRow | null = null;
      gridRef.current?.api.forEachNode(tempRow => tempRow.data.id === rowId && (updatedRowNew = tempRow.data));
      updateCibilBorrowingDetails(accountId, updatedRowNew!)
        .then(() => {
          enqueueSnackbar(`Row id: ${rowId} updated successfully`, {variant: "success"});
        })
        .catch(() => {
          enqueueSnackbar(`Error while updating Row id: ${rowId}`, {variant: "error"});
        })
        .finally(() => {
          onUpdateRow();
          editedRows.current = new Set<number>();
        });
    });
  };
  const handleDownload = () => {
    const worksheet = XLSX.utils.json_to_sheet(borrowingDetails.map(detail => ({
      "Loan Type": detail.typeOfLoan,
      "Sanctioned Amount": detail.sanctionedAmount,
      "Outstanding Amount": detail.outstandingAmount,
      "Sanctioned Date": detail.sanctionedDate,
      "Status": detail.status || "N/A",
      "Suit Filed": detail.suitFiled || "N/A",
      "Bank/NBFC": detail.bankNbfc,
      "Ownership": detail.ownership,
      "Overdue": detail.overdue,
      "EMI": detail.emi,
      "EMI(System)": detail.emiSystem,
      "Interest Rate": detail.interestRate || "N/A",
      "Comment": detail.comment || "N/A",
      "Tenure": detail.tenure || "N/A",
      "Last 12 Months DPD": detail.last12MonthsDPD || "N/A",
      "Overall DPD": detail.overallDPD || "N/A",
      "Delay in Months": detail.delayInMonths || "N/A",
      "Reported Date": detail.reportedDate,
    })));
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, "Borrowing Details");
    const fileName = "BorrowingDetails.xlsx";
    XLSX.writeFile(workbook, fileName);
  };

  return (
    <>
      <SnackbarProvider maxSnack={10}>
        <div className="ag-theme-quartz ag-container">
          <AgGridReact
            suppressBrowserResizeObserver={true}
            ref={gridRef}
            rowData={rowData}
            columnDefs={columnDefs}
            defaultColDef={defaultColDef}
            rowSelection={"multiple"}
            cacheBlockSize={50}
            cacheOverflowSize={3}
            maxConcurrentDatasourceRequests={1}
            infiniteInitialRowCount={3}
            maxBlocksInCache={2}
            onGridReady={onGridReady}
            tooltipShowDelay={0}
          />
        </div>
        <div className="flex-row-justify-center m-10">
          <Button
            onClick={handleSubmitTransactionComments}
            variant="contained"
            size="small"
            style={{marginRight: "10px"}}
          >
            {t("bankStatementTable.submit")}
          </Button>
          <Button
            onClick={handleDownload}
            variant="contained"
            size="small"
          >
            {t("bankStatementTable.download")}
          </Button>
        </div>
      </SnackbarProvider>
    </>
  );
}