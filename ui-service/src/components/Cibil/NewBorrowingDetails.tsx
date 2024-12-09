import React, {useMemo, useRef, useState} from "react";
import {ColDef, ModuleRegistry, ValueFormatterParams, ValueGetterParams} from "@ag-grid-community/core";
import {AgGridReact} from "@ag-grid-community/react";
import "@ag-grid-community/styles/ag-grid.css";
import "@ag-grid-community/styles/ag-theme-quartz.css";
import {ICellRendererParams} from "ag-grid-community";
import {AccountBorrowingDetails, BorrowingDetailsRow} from "interfaces/cibil";
import {SnackbarProvider} from "notistack";
import {ClientSideRowModelModule} from "@ag-grid-community/client-side-row-model";
import {useTranslation} from "react-i18next";
import "styles/bankTxnTable.scss";
import {getRoundedOffNumberWithCommas} from "utils/numberFormat";
import "styles/cibil.scss";

ModuleRegistry.registerModules([ClientSideRowModelModule]);

interface PropsType {
  borrowingDetails: AccountBorrowingDetails[]
}

export function NewBorrowingDetails(props: PropsType) {
  const {borrowingDetails} = props;
  const {t} = useTranslation();
  const gridRef = useRef<AgGridReact>(null);
  const [rowData, setRowData] = useState<BorrowingDetailsRow[]>([]);

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
      headerName: t("cibil.borrowingDetails.columns.typeOfLoan"),
      headerTooltip: t("cibil.borrowingDetails.columns.typeOfLoan"),
      field: "typeOfLoan",
      tooltipField: "typeOfLoan",
      filter: "agTextColumnFilter",
      filterParams: {
        maxNumConditions: 1,
      },
      sortable: true,
    },
    {
      headerName: t("cibil.borrowingDetails.columns.sanctionedAmount"),
      headerTooltip: t("cibil.borrowingDetails.columns.sanctionedAmount"),
      field: "sanctionedAmount",
      tooltipField: "sanctionedAmount",
      width: 200,
      cellStyle: {textAlign: "center"},
      valueFormatter: (p)=> p.value?getRoundedOffNumberWithCommas(p.value,2):"-",
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
      valueFormatter: (p)=> p.value?getRoundedOffNumberWithCommas(p.value,2):"-",
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
      valueFormatter: (p)=>p.value||"-",
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
      valueFormatter: (p)=>p.value||"-",
      cellRendererParams: (params: ICellRendererParams)=>{
        return (<>
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
        </>);
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
      valueFormatter: (p)=>p.value||"-",
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
      valueFormatter: (p)=>p.value||"-",
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
      valueFormatter: (p)=>p.value||"-",
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
      valueFormatter: (p)=> p.value?getRoundedOffNumberWithCommas(p.value,2):"-",
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
      width: 100,
      cellStyle: {textAlign: "center"},
      valueFormatter: (p)=>p.value||"-",
      filter: "agNumberColumnFilter",
      filterParams: {
        maxNumConditions: 1,
      },
      sortable: true,
    },
    {
      headerName: t("cibil.borrowingDetails.columns.last12MonthsDPD"),
      headerTooltip: t("cibil.borrowingDetails.columns.last12MonthsDPD"),
      field: "last12MonthsDPD",
      tooltipField: "last12MonthsDPD",
      width: 200,
      cellStyle: {textAlign: "center"},
      valueFormatter: (p)=>p.value||"-",
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
      valueFormatter: (p)=>p.value||"-",
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
      valueFormatter: (p)=>p.value||"-",
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
      width: 130,
      cellStyle: {textAlign: "center"},
      valueFormatter: (p)=> p.value?getRoundedOffNumberWithCommas(p.value,2):"-",
      filter: "agNumberColumnFilter",
      filterParams: {
        maxNumConditions: 1,
      },
      sortable: true,
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
      valueFormatter: (p)=>p.value||"-",
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
      filter: "agTextColumnFilter",
      filterParams: {
        maxNumConditions: 1,
      },
      sortable: true,
    },
  ];

  const defaultColDef = useMemo<ColDef>(() => {
    return {
      sortable: false,
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
  const onCellClicked = (params:any) => {
    if (params.colDef.field === "typeOfLoan") {
      handleTypeOfLoanClick(params.value, params.data);
    }
  };
  const [showAccountSummery, setshowAccountSummery] = useState(false);
  const [accountSummary, setAccountSummary] = useState<any>(null);

  const handleTypeOfLoanClick = (loanType: string, rowData: any) => {
    setshowAccountSummery(true);
    const auccountSummerySegmentDTO = rowData.auccountSummerySegmentDTO;
    setAccountSummary(auccountSummerySegmentDTO);
  };
  const accountDetails = accountSummary ? [
    {field: "Member Name", detail: accountSummary.reportingMemberShortName || "N/A",
      field2: "Payment History Start", detail2: accountSummary.paymentHistoryStartDate || "N/A"},
    {field: "Account Number", detail: accountSummary.accountNumber || "N/A",
      field2: "Payment History End", detail2: accountSummary.paymentHistoryEndDate || "N/A"},
    {field: "Type", detail: accountSummary.accountType || "N/A",
      field2: "Last Payment", detail2: accountSummary.dateOfLastPayment || "N/A"},
    {field: "Ownership", detail: accountSummary.ownershipIndicator || "N/A",
      field2: "Sanctioned Amount", detail2: accountSummary.highCreditOrSanctionedAmount || "N/A"},
    {field: "Opened", detail: accountSummary.dateOpenedOrDisbursed || "N/A",
      field2: "Current Balance", detail2: accountSummary.currentBalance || "N/A"},
    {field: "Reported and Certified", detail: accountSummary.dateReportedAndCertified || "N/A",
      field2: "Payment Frequency", detail2: accountSummary.paymentFrequency || "N/A"},
    {field: "Closed", detail: accountSummary.dateClosed || "N/A",
      field2: "Repayment Tenure", detail2: accountSummary.repaymentTenure || "N/A"},
    {field: "Interest Rate", detail: accountSummary.rateOfInterest || "N/A",
      field2: "", detail2: ""},
  ] : [];

  return (
    <>
      <SnackbarProvider maxSnack={10}>
        <div className="ag-theme-quartz ag-container" style={{height: "300px", width: "100%"}}>
          <AgGridReact
            ref={gridRef}
            rowData={rowData}
            columnDefs={columnDefs}
            defaultColDef={defaultColDef}
            rowSelection={"multiple"}
            cacheBlockSize={5}
            maxConcurrentDatasourceRequests={1}
            infiniteInitialRowCount={5}
            maxBlocksInCache={2}
            onGridReady={onGridReady}
            tooltipShowDelay={0}
            onCellClicked={onCellClicked}
            domLayout="normal"
            rowHeight={40}
            pagination={false}
            enableCellTextSelection={true}
          />
        </div>
      </SnackbarProvider>
      {showAccountSummery && (
        <div className="table-wrapper">
          <div
            style={{
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
              position: "relative", // For independent positioning of the close button
            }}
          >
            <h2 style={{margin: 0}}>Account Summary</h2>
            <button
              onClick={() => setshowAccountSummery(false)}
              style={{
                position: "absolute", // Position it relative to the parent
                right: "10px", // Adjust spacing as needed
                background: "none",
                border: "none",
                fontSize: "20px",
                cursor: "pointer",
                color: "#000",
              }}
            >
              ✕
            </button>
          </div>
          <table className="table-container1">
            <thead>
              <tr>
                <th>Field</th>
                <th>Details</th>
                <th>Field</th>
                <th>Details</th>
              </tr>
            </thead>
            <tbody>
              {accountDetails.map((row, index) => (
                <tr key={index}>
                  <td>{row.field}</td>
                  <td>{row.detail}</td>
                  <td>{row.field2}</td>
                  <td>{row.detail2}</td>
                </tr>
              ))}
            </tbody>
          </table>
          {/* <table className="table-container">
            <tbody>
              <tr>
                <th>Days Past Due/Asset Classification</th>
                <td>{handleDPDData(accountSummary.paymentHistory1)?.join(",")}</td>
              </tr>
            </tbody>
          </table> */}
        </div>
      )}
    </>
  );
}
