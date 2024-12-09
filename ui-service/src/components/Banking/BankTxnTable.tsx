import React, {useEffect, useMemo, useRef, useState} from "react";
import {AgGridReact} from "@ag-grid-community/react";
import {ColDef, IDatasource, GridReadyEvent, ModuleRegistry} from "@ag-grid-community/core";
import {InfiniteRowModelModule} from "@ag-grid-community/infinite-row-model";
import "@ag-grid-community/styles/ag-grid.css";
import "@ag-grid-community/styles/ag-theme-quartz.css";
import {useTranslation} from "react-i18next";
import {getTxnData, getTxnTotal, updateTransactionCustomFields} from "utils/api/request";
import {AccountList, TransactionDTO, TransactionReqDTO, UpdateTransactionCustomFieldsRequest} from "interfaces/banking";
import dayjs from "dayjs";
import {ICellRendererParams, IGetRowsParams} from "ag-grid-community";
import {ConsolidateObject} from "pages/Banking";
import {
  Alert,
  AlertColor,
  Button,
  CircularProgress,
  IconButton,
  InputBase,
  Paper,
  Snackbar,
  ToggleButton,
  ToggleButtonGroup,
  Typography,
} from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import {getRoundedOffNumberWithCommas} from "utils/numberFormat";
import {DebitCredit} from "interfaces/bankingConfiguration";
import "styles/bankTxnTable.scss";

ModuleRegistry.registerModules([InfiniteRowModelModule]);

const PAGE_SIZE = 50;

interface FilterModel {
  value: {
    filter: number | undefined,
    filterTo: number | undefined,
    dateFrom: string | undefined,
    dateTo: string | undefined
  }
}

interface statementDuration {
  startDate: string | undefined,
  endDate: string | undefined
}

interface TransactionRow extends TransactionDTO {
  id: number,
}

interface BankTxnTableProps {
  masFinId: string,
  consolidateCheck: Map<string, ConsolidateObject>
}

const getAccountList = (consolidateCheck: Map<string, ConsolidateObject>): AccountList[] => {
  const tempMap: Map<string, string[]> = new Map(); // key: customerTxnId, value: accountNo[]
  const tempAccountList: AccountList[] = [];
  consolidateCheck.forEach((value, key) => {
    if (value.consolidate === true) {
      const tempAccNos = tempMap.get(value.customerTransactionId) || [];
      tempAccNos.push(key.split("+")[2]);
      tempMap.set(value.customerTransactionId, tempAccNos);
    }
  });
  tempMap.forEach((value, key) => {
    tempAccountList.push({
      accountNumberList: value,
      customerTransactionId: key,
    });
  });
  return tempAccountList;
};

const getInitialRequestBody = (consolidateCheck: Map<string, ConsolidateObject>): TransactionReqDTO => {
  const tempAccountList: AccountList[] = getAccountList(consolidateCheck);
  return {
    debitOrCredit: DebitCredit.CREDIT,
    accountList: tempAccountList,
  };
};

export function BankTxnTable(props: BankTxnTableProps) {

  const {t} = useTranslation();

  const {masFinId, consolidateCheck} = props;

  const gridRef = useRef<AgGridReact>(null);

  const [requestBody, setRequestBody] = useState(getInitialRequestBody(consolidateCheck));
  const [gridApi, setGridApi] = useState<any>();
  const [txnType, setTxnType] = useState<DebitCredit>(DebitCredit.CREDIT);
  const [searchInput, setSearchInput] = useState("");
  const [totalTxns, setTotalTxns] = useState<number>();
  const [statementDuration, setStatementDuration] = useState<statementDuration>();

  const requestBodyRef = useRef<TransactionReqDTO>();
  requestBodyRef.current = requestBody;

  const [isTxnTotalLoading, setIsTxnTotalLoading] = useState(false);
  const [showNotification, setShowNotification] = useState(false);
  const [alertContent, setAlertContent] = useState<{
    value: string,
    severity: AlertColor
  }>({value: "", severity: "success"});

  const columnDefs: ColDef[] = [
    {
      headerName: t("bankTxnTable.columns.srNo"),
      headerTooltip: t("bankTxnTable.columns.srNo"),
      field: "id",
      tooltipField: "id",
      width: 70,
      filter: false,
      menuTabs: [],
    },
    {
      headerName: t("bankTxnTable.columns.bank"),
      headerTooltip: t("bankTxnTable.columns.bank"),
      field: "institutionName",
      tooltipField: "institutionName",
      width: 200,
      filter: false,
      sortable: true,
      menuTabs: [],
    },
    {
      headerName: t("bankTxnTable.columns.accNo"),
      headerTooltip: t("bankTxnTable.columns.accNo"),
      field: "accountNumber",
      tooltipField: "accountNumber",
      width: 120,
      filter: false,
      sortable: true,
      menuTabs: [],
    },
    {
      headerName: t("bankTxnTable.columns.date"),
      headerTooltip: t("bankTxnTable.columns.date"),
      field: "date",
      tooltipField: "date",
      width: 120,
      sortable: true,
      filter: "agDateColumnFilter",
      filterParams: {
        filterOptions: ["inRange"],
        suppressAndOrCondition: true,
      },
    },
    {
      headerName: t("bankTxnTable.columns.description"),
      headerTooltip: t("bankTxnTable.columns.description"),
      field: "description",
      tooltipField: "description",
      width: 180,
      filter: "agTextColumnFilter",
      filterParams: {
        suppressAndOrCondition: true,
      },
      sortable: true,
      wrapText: true,
    },
    {
      headerName: t("bankTxnTable.columns.comment"),
      headerTooltip: t("bankTxnTable.columns.comment"),
      field: "comment",
      tooltipField: "comment",
      width: 130,
      filter: "agTextColumnFilter",
      filterParams: {
        suppressAndOrCondition: true,
      },
      sortable: true,
      wrapText: true,
      cellStyle: () => {
        return {
          display: "flex",
          padding: "0px",
        };
      },
      cellRenderer: (params: ICellRendererParams)=>{
        const onChange=(event:any)=>{
          gridRef.current?.api.getRowNode(params.node.id!)?.setDataValue("comment",event.target.value);
        };
        return (
          <>
            {params.value!=="#last#row#" &&
            <InputBase
              onChange={onChange}
              value={params.value}
              sx={{backgroundColor: "white"}}
            />}
          </>
        );
      },
    },
    {
      headerName: t("bankTxnTable.columns.chequeNo"),
      headerTooltip: t("bankTxnTable.columns.chequeNo"),
      field: "chequeNo",
      tooltipField: "chequeNo",
      width: 120,
      filter: "agNumberColumnFilter",
      cellStyle: {textAlign: "center"},
      sortable: true,
    },
    {
      headerName: t("bankTxnTable.columns.credit"),
      headerTooltip: t("bankTxnTable.columns.credit"),
      field: "credit",
      tooltipField: "credit",
      width: 120,
      valueFormatter: p => {
        if (typeof p.value==="string"){
          return p.value;
        }
        return p.value?getRoundedOffNumberWithCommas(p.value,2):"-";
      },
      filter: "agNumberColumnFilter",
      filterParams: {
        filterOptions: ["inRange"],
        suppressAndOrCondition: true,
      },
      cellStyle: {textAlign: "center"},
      sortable: true,
    },
    {
      headerName: t("bankTxnTable.columns.debit"),
      headerTooltip: t("bankTxnTable.columns.debit"),
      field: "debit",
      tooltipField: "debit",
      width: 120,
      valueFormatter: p => {
        if (typeof p.value==="string"){
          return p.value;
        }
        return p.value?getRoundedOffNumberWithCommas(p.value,2):"-";
      },
      filter: "agNumberColumnFilter",
      filterParams: {
        filterOptions: ["inRange"],
        suppressAndOrCondition: true,
      },
      cellStyle: {textAlign: "center"},
      sortable: true,
    },
    {
      headerName: t("bankTxnTable.columns.balance"),
      headerTooltip: t("bankTxnTable.columns.balance"),
      field: "balance",
      tooltipField: "balance",
      width: 120,
      valueFormatter: p => {
        if (typeof p.value==="string"){
          return p.value;
        }
        return p.value?getRoundedOffNumberWithCommas(p.value,2):"-";
      },
      filter: "agNumberColumnFilter",
      filterParams: {
        filterOptions: ["inRange"],
        suppressAndOrCondition: true,
      },
      cellStyle: {textAlign: "center"},
      sortable: true,
    },
    {
      headerName: t("bankTxnTable.columns.transactionType"),
      headerTooltip: t("bankTxnTable.columns.transactionType"),
      field: "transactionType",
      tooltipField: "transactionType",
      width: 140,
      filter: false,
      menuTabs: [],
      sortable: false,
      cellStyle: {textAlign: "center"},
    },
    {
      headerName: t("bankTxnTable.columns.parties"),
      headerTooltip: t("bankTxnTable.columns.parties"),
      field: "parties",
      tooltipField: "parties",
      width: 150,
      filter: false,
      menuTabs: [],
      sortable: false,
      cellStyle: {textAlign: "center"},
    },
    {
      headerName: t("bankTxnTable.columns.category"),
      headerTooltip: t("bankTxnTable.columns.category"),
      field: "category",
      tooltipField: "category",
      width: 120,
      filter: "agTextColumnFilter",
      filterParams: {
        suppressAndOrCondition: true,
      },
      sortable: true,
    },
    {
      headerName: t("bankTxnTable.columns.identifiedCategory"),
      headerTooltip: t("bankTxnTable.columns.identifiedCategory"),
      field: "identifiedCategory",
      tooltipField: "identifiedCategory",
      width: 160,
      filter: false,
      menuTabs: [],
      sortable: false,
      cellStyle: {textAlign: "center"},
    },
    {
      headerName: t("bankTxnTable.columns.transactionFlag"),
      headerTooltip: t("bankTxnTable.columns.transactionFlag"),
      field: "transactionFlag",
      tooltipField: "transactionFlag",
      width: 140,
      filter: false,
      menuTabs: [],
      sortable: false,
      cellStyle: {textAlign: "center"},
    },
  ];

  const defaultColDef = useMemo<ColDef>(() => {
    return {
      sortable: false,
    };
  }, []);

  useEffect(() => {
    if (gridApi) {
      const reqBody:TransactionReqDTO = {
        ...requestBody,
        accountList: getAccountList(consolidateCheck),
        debitOrCredit: txnType,
      };
      setRequestBody(reqBody);
      gridApi.setDatasource(getDataSource());
    }
  }, [consolidateCheck, txnType]);

  const getRequestBody = (params: IGetRowsParams): TransactionReqDTO => {
    const reqBody = requestBodyRef.current!;
    if (!params.filterModel){
      return reqBody;
    }
    const updatedRequestBody: TransactionReqDTO = {
      accountList: getAccountList(consolidateCheck),
      debitOrCredit: reqBody.debitOrCredit,
      searchText: reqBody.searchText,
    };
    const filterModel: FilterModel = params.filterModel;
    for (const [key, value] of Object.entries(filterModel)) {
      if (key === "credit") {
        updatedRequestBody.fromCredit = value.filter;
        updatedRequestBody.toCredit = value.filterTo;
      } else if (key === "debit") {
        updatedRequestBody.fromDebit = value.filter;
        updatedRequestBody.toDebit = value.filterTo;
      } else if (key === "balance") {
        updatedRequestBody.fromBalance = value.filter;
        updatedRequestBody.toBalance = value.filterTo;
      } else if (key === "date") {
        updatedRequestBody.fromDate = dayjs(value.dateFrom, "YYYY-MM-DD HH:mm:ss").format("DD/MM/YYYY");
        updatedRequestBody.toDate = value.dateTo
          ? dayjs(value.dateTo, "YYYY-MM-DD HH:mm:ss").format("DD/MM/YYYY") : undefined;
      } else if (key === "description") {
        updatedRequestBody.description = value.filter;
      } else if (key === "comment") {
        updatedRequestBody.comment = value.filter;
      } else if (key === "category") {
        updatedRequestBody.category = value.filter;
      }
    }
    setRequestBody(updatedRequestBody);
    return updatedRequestBody;
  };

  const getDataSource = (): IDatasource => {
    fetchTxnTotalApiCall();
    return {
      rowCount: undefined,
      getRows: (params: IGetRowsParams) => {
        const page = (params.endRow / PAGE_SIZE) - 1;
        let sortField = params?.sortModel[0]?.colId || "date";
        if (sortField==="credit" || sortField==="debit"){
          sortField = "amount";
        } else if (sortField==="institutionName" || sortField==="accountNumber"){
          sortField = "accountSummary."+sortField;
        }
        let tempIndex = (page * PAGE_SIZE) + 1;
        gridRef.current?.api.showLoadingOverlay();
        getTxnData(masFinId, getRequestBody(params), PAGE_SIZE, page,
          `&sort=${sortField},${params?.sortModel[0]?.sort.toUpperCase() || "ASC"}`)
          .then(resp => resp)
          .then(res => {
            const rowsThisPage: TransactionRow[] = res.data.elements?.map((row:TransactionDTO) => {
              return {
                ...row,
                id: tempIndex++,
              };
            });
            setTotalTxns(res.data.totalElements);
            const lastRow = res.data.totalElements <= params.endRow?res.data.totalElements:-1;
            params.successCallback(rowsThisPage, lastRow);
          }).finally(() => {
            gridRef.current?.api.hideOverlay();
          });
      },
    };
  };

  const getFooterRow = (totalCredit?: number, totalDebit?: number) => {
    const footerRow = columnDefs.reduce((tempFooterRow, col) => {
      tempFooterRow[col.field!]= " ";
      return tempFooterRow;
    },{} as {[key:string]:number | string});
    if (txnType==="CREDIT"){
      footerRow["chequeNo"] = "Total Credit";
      footerRow["credit"]= totalCredit!;
    } else if (txnType==="DEBIT"){
      footerRow["credit"] = "Total Debit";
      footerRow["debit"]= totalDebit!;
    } else {
      footerRow["chequeNo"] = "Total";
      footerRow["credit"]= totalCredit!;
      footerRow["debit"]= totalDebit!;
    }
    footerRow["comment"] = "#last#row#";
    return footerRow;
  };

  const fetchTxnTotalApiCall = async () =>{
    setIsTxnTotalLoading(true);
    setTimeout(()=>{
      const reqBody = requestBodyRef.current!;
      getTxnTotal(masFinId, reqBody)
        .then((res) =>{
          setStatementDuration({
            startDate: res.data.startTransactionDate,
            endDate: res.data.endTransactionDate,
          });
          gridRef.current?.api?.setGridOption("pinnedBottomRowData",
            [getFooterRow(res.data.totalCredit, res.data.totalDebit)]);
        })
        .finally(()=>setIsTxnTotalLoading(false));
    },300);
  };

  const onSearchSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (gridApi) {
      const reqBody = {...requestBody, searchText: searchInput};
      setRequestBody(reqBody);
      gridApi.setDatasource(getDataSource());
    }
  };

  const handleSearchInput = (event: React.ChangeEvent<HTMLInputElement>) => {
    event.preventDefault();
    setSearchInput(event.target.value);
  };

  const onGridReady = (params: GridReadyEvent) => {
    setGridApi(params?.api);
    params?.api?.setDatasource(getDataSource());
  };

  const handleSubmitTransactionComments = () => {
    const requestBody: UpdateTransactionCustomFieldsRequest[] = [];
    gridApi?.getRenderedNodes().forEach((row: any) => row.data && row.data.comment !== null
    && row.data.comment !== undefined && requestBody.push({
      transactionUuid: row.data.transactionUuid,
      comment: row.data.comment,
    }));
    updateTransactionCustomFields(requestBody)
      .then(() => {
        setAlertContent({value: t("bankTxnTable.alert.transactionUpdatedSuccessfully"), severity: "success"});
      })
      .catch((error) => {
        setAlertContent(
          {value: error.response.data.message || t("bankTxnTable.alert.errorWhileUpdating"), severity: "error"});
      })
      .finally(() => setShowNotification(true));
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
      <div className="flex-row-justify-between">
        <Typography variant='h6'>
          {t("bankTxnTable.totalTransactions")} : {totalTxns}
        </Typography>
        <Typography variant='h6'>
          {t("bankTxnTable.statementDuration")} : {isTxnTotalLoading?
            <CircularProgress size={15}/>: `${statementDuration?.startDate} - ${statementDuration?.endDate}`
          }
        </Typography>
      </div>
      <div className="flex-row-justify-between">
        <Paper
          component="form"
          onSubmit={onSearchSubmit}
        >
          <InputBase
            placeholder="Search"
            inputProps={{"aria-label": "search"}}
            onChange={handleSearchInput}
          />
          <IconButton type="submit">
            <SearchIcon />
          </IconButton>
        </Paper>
        <ToggleButtonGroup
          color="primary"
          size="small"
          exclusive
          value={txnType}
          onChange={(e, value)=>{
            if (value) {
              setTxnType(value);
            }
          }}
          aria-label="text formatting"
        >
          <ToggleButton value="CREDIT">{t("bankTxnTable.CREDIT")}</ToggleButton>
          <ToggleButton value="DEBIT">{t("bankTxnTable.DEBIT")}</ToggleButton>
          <ToggleButton value="BOTH">{t("bankTxnTable.BOTH")}</ToggleButton>
        </ToggleButtonGroup>
      </div>
      <br />
      <div className="ag-theme-quartz ag-container">
        <AgGridReact
          ref={gridRef}
          columnDefs={columnDefs}
          defaultColDef={defaultColDef}
          onFilterChanged={fetchTxnTotalApiCall}
          rowSelection={"multiple"}
          rowModelType={"infinite"}
          cacheBlockSize={50}
          cacheOverflowSize={3}
          maxConcurrentDatasourceRequests={1}
          infiniteInitialRowCount={3}
          maxBlocksInCache={2}
          onGridReady={onGridReady}
          tooltipShowDelay={0}
          getRowStyle={(params)=> {
            if (params.node.rowPinned) {
              return {
                "font-weight": "bold",
              };
            } else {
              return undefined;
            }
          }}
        />
      </div>
      <div className="flex-row-justify-center m-10">
        <Button
          onClick={handleSubmitTransactionComments}
          variant="contained"
          size="small"
        >
          {t("bankStatementTable.submit")}
        </Button>
      </div>
    </>
  );
}
