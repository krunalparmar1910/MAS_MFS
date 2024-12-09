import AddIcon from "@mui/icons-material/Add";
import CancelIcon from "@mui/icons-material/Close";
import DeleteIcon from "@mui/icons-material/DeleteOutlined";
import EditIcon from "@mui/icons-material/Edit";
import SaveIcon from "@mui/icons-material/Save";
import {Alert, AlertColor, Checkbox, Snackbar, Typography, Box, Button} from "@mui/material";
import useEnhancedEffect from "@mui/material/utils/useEnhancedEffect";
import {
  DataGrid,
  GridActionsCellItem,
  GridColDef,
  GridEventListener,
  GridRenderCellParams,
  GridRenderEditCellParams,
  GridRowEditStopReasons,
  GridRowId,
  GridRowModes,
  GridRowModesModel,
  GridToolbarContainer,
  useGridApiContext,
  useGridApiRef,
} from "@mui/x-data-grid";
import {GridApiCommunity} from "@mui/x-data-grid/internals";
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs";
import {DatePicker} from "@mui/x-date-pickers/DatePicker";
import {LocalizationProvider} from "@mui/x-date-pickers/LocalizationProvider";
import dayjs, {Dayjs} from "dayjs";
import {BorrowingDetailAmountsDTO, NewLoanEntryDetails} from "interfaces/cibil";
import React, {useState} from "react";
import {useTranslation} from "react-i18next";
import {deleteNewLoanEntry, saveNonCibilData, updateNewLoanEntry} from "utils/api/request";
import {v4 as uuidv4} from "uuid";

const DateColumn = (props: {params:GridRenderEditCellParams, currentApiRef: GridApiCommunity}) =>{
  const {params, currentApiRef} = props;
  const value = params.field==="lastPaymentDate"?
    params.value?dayjs(params.value, "DD-MM-YYYY"):null:
    dayjs(params.value, "DD-MM-YYYY");
  const minDate:Dayjs = params.field==="lastPaymentDate"?
    currentApiRef.getRowWithUpdatedValues(params.id, "sanctionedDate")["sanctionedDate"]||undefined:undefined;
  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <DatePicker
        slotProps={{textField: {placeholder: "DD-MM-YYYY"}}}
        value={value}
        onChange={(value) => {
          currentApiRef.setEditCellValue(
            {
              id: params.id,
              field: params.field,
              value: dayjs(value).format("DD-MM-YYYY"),
            },
          );
        }}
        views={["year", "month", "day"]}
        format="DD-MM-YYYY"
        minDate={minDate?dayjs(minDate, "DD-MM-YYYY"):undefined}
      />
    </LocalizationProvider>
  );
};

const tempRow = {
  id: "-1",
  sanctionedDate: "",
  typeOfLoan: "",
  sanctionedAmount: 0,
  outstanding: 0,
  bankNbfc: "",
  emiAmount: 0,
  emiSystem: 0,
  tenure: 36,
  comment: "",
  lastPaymentDate: "",
  duplicate: false,
  addInTotal: true,
  isEdited: false,
  isNew: true,
};

interface PropsType {
    nonCibilData: NewLoanEntryDetails[],
    requestId: string,
    onUpdateRow: () => void
    onCheckBoxUpdate: (row: BorrowingDetailAmountsDTO, columnField: string, value: boolean)=> void
}

interface NewLoanEntryRow extends NewLoanEntryDetails {
    isEdited: boolean,
    isNew: boolean
}

const getBorrowingDetailAmountsDTO = (row: NewLoanEntryRow):BorrowingDetailAmountsDTO => {
  return {
    addInTotal: row.addInTotal,
    duplicate: row.duplicate,
    sanctionedAmount: row.sanctionedAmount,
    outstandingAmount: row.outstanding,
    emi: row.emiAmount,
    emiSystem: row.emiSystem,
  };
};

export function CibilNewEntryTable(props: PropsType) {

  const {t} = useTranslation();

  const apiRef = useGridApiRef();

  const {nonCibilData, requestId, onUpdateRow, onCheckBoxUpdate} = props;

  const [rows, setRows] = React.useState<NewLoanEntryRow[]>([]);
  const [rowModesModel, setRowModesModel] = React.useState<GridRowModesModel>({});

  const [showNotification, setShowNotification] = useState(false);
  const [alertContent, setAlertContent] = useState<{
        value: string,
        severity: AlertColor
    }>({value: "", severity: "success"});

  React.useEffect(() => {
    const tempRows = nonCibilData?.map((details: NewLoanEntryDetails) => {
      return {
        ...details,
        isEdited: false,
        isNew: false,
      };
    });
    setRows(tempRows || []);
  }, [nonCibilData]);

  const handleRowEditStop: GridEventListener<"rowEditStop"> = (params, event) => {
    if (params.reason === GridRowEditStopReasons.rowFocusOut) {
      event.defaultMuiPrevented = true;
    }
  };

  const handleEditClick = (id: GridRowId) => () => {
    setRowModesModel({...rowModesModel, [id]: {mode: GridRowModes.Edit}});
  };

  const areAllCompulsoryFieldsPresent = (id: GridRowId): boolean => {
    if (
      apiRef.current.getRowWithUpdatedValues(id, "sanctionedDate")["sanctionedDate"] &&
      apiRef.current.getRowWithUpdatedValues(id, "typeOfLoan")["typeOfLoan"]!=="" &&
      apiRef.current.getRowWithUpdatedValues(id, "sanctionedAmount")["sanctionedAmount"]!==0){
      return true;
    }
    return false;
  };

  const isLastPaymentDateValid = (id: GridRowId): boolean => {
    if (apiRef.current.getRowWithUpdatedValues(id, "lastPaymentDate")["lastPaymentDate"]!==""){
      const lastPaymentDate = apiRef.current.getRowWithUpdatedValues(id, "lastPaymentDate")["lastPaymentDate"];
      const sanctionedDate = apiRef.current.getRowWithUpdatedValues(id, "sanctionedDate")["sanctionedDate"];
      if (dayjs(lastPaymentDate, "DD-MM-YYYY").isBefore(dayjs(sanctionedDate, "DD-MM-YYYY"))){
        return false;
      }
      return true;
    }
    return true;
  };

  const handleSaveClick = (id: GridRowId) => () => {
    if (areAllCompulsoryFieldsPresent(id)){
      if (!isLastPaymentDateValid(id)){
        setAlertContent({
          value: t("cibil.newLoanEntryTable.notification.rowValidationDateInvalidFailure"),
          severity: "error",
        });
        setShowNotification(true);
      } else {
        if (Object.keys(rowModesModel).length===0 ||(rowModesModel[id] && rowModesModel[id].mode===GridRowModes.View)){
          processRowUpdate(rows.find(row=>row.id===id.toString())!);
        }
        setRowModesModel({...rowModesModel, [id]: {mode: GridRowModes.View}});
      }
    } else {
      setAlertContent({
        value: t("cibil.newLoanEntryTable.notification.rowValidationMandatoryFieldsFailure"),
        severity: "error",
      });
      setShowNotification(true);
    }
  };

  const handleDeleteClick = (id: GridRowId) => () => {
    deleteNewLoanEntry(id.toString())
      .then(() => {
        onUpdateRow();
        setAlertContent({value: t("cibil.newLoanEntryTable.notification.rowDeleteSuccess"), severity: "success"});
      })
      .catch(() => {
        setAlertContent({value: t("cibil.newLoanEntryTable.notification.rowDeleteFailure"), severity: "error"});
      })
      .finally(() => setShowNotification(true));
  };

  const handleCancelClick = (id: GridRowId) => () => {
    setRowModesModel({
      ...rowModesModel,
      [id]: {mode: GridRowModes.View, ignoreModifications: true},
    });

    const editedRow = rows.find((row) => row.id === id)!;
    if (editedRow!.isNew) {
      setRows(rows.filter((row) => row.id !== id));
    }
  };

  const processRowUpdate = (newRow: NewLoanEntryDetails) => {
    if (newRow.id === "-1") {
      const updatedRow = {...newRow, isNew: false, id: uuidv4()};
      const body = {...updatedRow, isEdited: undefined, isNew: undefined};
      let isRowUpdated = false;
      saveNonCibilData(requestId, body)
        .then((data) => {
          isRowUpdated = true;
          onUpdateRow();
          setAlertContent({value: t("cibil.newLoanEntryTable.notification.rowSaveSuccess"), severity: "success"});
        })
        .catch(() => {
          apiRef.current.startRowEditMode({id: newRow.id.toString()});
          setAlertContent({value: t("cibil.newLoanEntryTable.notification.rowSaveFailure"), severity: "error"});
        })
        .finally(() => setShowNotification(true));
      return isRowUpdated ? updatedRow : newRow;
    } else {
      const updatedRow = {...newRow, isNew: false, requestId: requestId};
      const body = {...updatedRow, isEdited: undefined, isNew: undefined};
      let isRowUpdated = false;
      updateNewLoanEntry(requestId, body)
        .then(() => {
          isRowUpdated = true;
          onUpdateRow();
          setAlertContent({value: t("cibil.newLoanEntryTable.notification.rowUpdateSuccess"), severity: "success"});
        })
        .catch(() => {
          apiRef.current.startRowEditMode({id: newRow.id.toString()});
          setAlertContent({value: t("cibil.newLoanEntryTable.notification.rowUpdateFailure"), severity: "error"});
        })
        .finally(() => setShowNotification(true));
      return isRowUpdated ? updatedRow : newRow;
    }
  };

  const handleRowModesModelChange = (newRowModesModel: GridRowModesModel) => {
    setRowModesModel(newRowModesModel);
  };

  function RatingEditInputCell(props: {params: GridRenderCellParams<NewLoanEntryRow, boolean>, isInEditMode:boolean}) {
    const {id, value, field, hasFocus, row: curRow} = props.params;
    const apiRef = useGridApiContext();
    const ref = React.useRef<HTMLElement>();

    const handleChange = (event: React.SyntheticEvent, newValue: boolean) => {
      apiRef.current.setEditCellValue({id, field, value: newValue});
    };

    const handleSavedChange = (event: React.SyntheticEvent, newValue: boolean) => {
      onCheckBoxUpdate(getBorrowingDetailAmountsDTO(curRow), field, newValue);
      const tempRows = rows.map(row=>row.id===curRow.id?({...curRow, [field]: newValue, isEdited: true}):row);
      setRows(tempRows);
    };

    useEnhancedEffect(() => {
      if (hasFocus && ref.current) {
        const input = ref.current.querySelector<HTMLInputElement>(
          `input[value="${value}"]`,
        );
        input?.focus();
      }
    }, [hasFocus, value]);

    return (
      <Box>
        <Checkbox
          name="addInTotal"
          checked={value}
          onChange={props.isInEditMode?handleChange:handleSavedChange}
        />
      </Box>
    );
  }

  const renderCheckBox = (params: GridRenderCellParams<NewLoanEntryRow, boolean>, isInEditMode: boolean) => {
    return <RatingEditInputCell params={params} isInEditMode={isInEditMode} />;
  };

  const columns: GridColDef[] = [
    {
      field: "sanctionedDate",
      type: "string",
      width: 160,
      headerName: t("cibil.newLoanEntryTable.columns.sanctionedDate"),
      renderEditCell(params) {
        return (<DateColumn params={params} currentApiRef={apiRef.current}/>);
      },
      editable: true,
      disableColumnMenu: true,
      hideSortIcons: true,
      align: "center",
    },
    {
      field: "typeOfLoan",
      headerAlign: "center",
      flex: 2,
      type: "singleSelect",
      valueOptions: [
        {
          label: "Personal Loan",
          value: "PERSONAL_LOAN",
        },
        {
          label: "Business Loan",
          value: "BUSINESS_LOAN",
        },
        {
          label: "Home Loan",
          value: "HOME_LOAN",
        },
        {
          label: "Loan Against Property",
          value: "LOAN_AGAINST_PROPERTY",
        },
        {
          label: "Credit Card",
          value: "CREDIT_CARD",
        },
        {
          label: "Unsecured Business Loan",
          value: "UNSECURED_BUSINESS_LOAN",
        },
        {
          label: "Others",
          value: "OTHERS",
        },
      ],
      headerName: t("cibil.newLoanEntryTable.columns.typeOfLoan"),
      editable: true,
      disableColumnMenu: true,
      hideSortIcons: true,
      align: "center",
    },
    {
      field: "sanctionedAmount",
      headerAlign: "center",
      flex: 2,
      type: "number",
      headerName: t("cibil.newLoanEntryTable.columns.sanctionedAmount"),
      editable: true,
      disableColumnMenu: true,
      hideSortIcons: true,
      align: "center",
    },
    {
      field: "outstanding",
      headerAlign: "center",
      type: "number",
      flex: 1.75,
      headerName: t("cibil.newLoanEntryTable.columns.outstanding"),
      editable: true,
      disableColumnMenu: true,
      hideSortIcons: true,
      align: "center",
    },
    {
      field: "bankNbfc",
      headerAlign: "center",
      headerName: t("cibil.newLoanEntryTable.columns.bankNbfc"),
      type: "string",
      flex: 1,
      editable: true,
      disableColumnMenu: true,
      hideSortIcons: true,
      align: "center",
    },
    {
      field: "emiAmount",
      headerAlign: "center",
      headerName: t("cibil.newLoanEntryTable.columns.emi"),
      type: "number",
      flex: 1,
      editable: true,
      disableColumnMenu: true,
      hideSortIcons: true,
      align: "center",
    },
    {
      field: "tenure",
      headerAlign: "center",
      type: "number",
      flex: 1.3,
      headerName: t("cibil.newLoanEntryTable.columns.tenure"),
      editable: true,
      disableColumnMenu: true,
      hideSortIcons: true,
      align: "center",
    },
    {
      field: "lastPaymentDate",
      headerAlign: "center",
      type: "string",
      width: 175,
      headerName: t("cibil.newLoanEntryTable.columns.lastPaymentDate"),
      renderEditCell(params) {
        return (<DateColumn params={params} currentApiRef={apiRef.current}/>);
      },
      editable: true,
      disableColumnMenu: true,
      hideSortIcons: true,
      align: "center",
    },
    {
      field: "comment",
      headerAlign: "center",
      type: "string",
      flex: 1.5,
      minWidth: 200,
      headerName: t("cibil.newLoanEntryTable.columns.comment"),
      editable: true,
      disableColumnMenu: true,
      hideSortIcons: true,
      align: "center",
    },
    {
      field: "duplicate",
      headerAlign: "center",
      align: "center",
      flex: 1.5,
      headerName: t("cibil.newLoanEntryTable.columns.duplicate"),
      renderCell: (params)=>renderCheckBox(params,false),
      renderEditCell: (params)=>renderCheckBox(params, true),
      editable: true,
      disableColumnMenu: true,
      hideSortIcons: true,
    },
    {
      field: "addInTotal",
      headerAlign: "center",
      align: "center",
      flex: 1.8,
      headerName: t("cibil.newLoanEntryTable.columns.addInTotal"),
      renderCell: (params)=>renderCheckBox(params, false),
      renderEditCell: (params)=>renderCheckBox(params, true),
      editable: true,
      disableColumnMenu: true,
      hideSortIcons: true,
    },
    {
      field: "actions",
      headerAlign: "center",
      flex: 1.5,
      type: "actions",
      headerName: t("cibil.newLoanEntryTable.columns.actions"),
      cellClassName: "actions",
      getActions: (params) => {
        const isInEditMode = rowModesModel[params.row.id]?.mode === GridRowModes.Edit;
        const editModeActions = [
          <GridActionsCellItem
            key={1}
            icon={<SaveIcon />}
            label={t("cibil.newLoanEntryTable.columns.saveLabel")}
            onClick={handleSaveClick(params.row.id)}
          />,
          <GridActionsCellItem
            key={2}
            icon={<CancelIcon />}
            label={t("cibil.newLoanEntryTable.columns.cancelLabel")}
            className="textPrimary"
            onClick={handleCancelClick(params.row.id)}
            color="inherit"
          />,
        ];

        const saveModeActions = [
          <GridActionsCellItem
            key={3}
            icon={<EditIcon />}
            label={t("cibil.newLoanEntryTable.columns.editLabel")}
            className="textPrimary"
            onClick={handleEditClick(params.row.id)}
            color="inherit"
          />,
          <GridActionsCellItem
            key={4}
            icon={<DeleteIcon />}
            label={t("cibil.newLoanEntryTable.columns.deleteLabel")}
            onClick={handleDeleteClick(params.row.id)}
            color="inherit"
          />,
        ];

        if (isInEditMode) {
          return editModeActions;
        }
        if (params.row.isEdited){
          return [editModeActions[0], ...saveModeActions];
        }
        return saveModeActions;
      },
    },
  ];

  const handleAddNewRow = () => {
    const id = "-1";
    if (rows.find((row) => row.id === "-1")) {
      return;
    } else {
      setRows((oldRows: NewLoanEntryRow[]) => [{...tempRow,requestId: requestId}, ...oldRows]);
      setRowModesModel((oldModel) => ({
        [id]: {mode: GridRowModes.Edit, fieldToFocus: "name"},
        ...oldModel,
      }));
    }
  };

  return (
    <>
      <Snackbar open={showNotification} autoHideDuration={4000} onClose={() => setShowNotification(false)}>
        <Alert
          severity={alertContent.severity}
          variant="filled"
        >
          {alertContent.value}
        </Alert>
      </Snackbar>
      <Typography variant="h6">{t("cibil.newLoanEntryTable.addBorrowingEntries")}</Typography>
      <DataGrid
        sx={{
          "& .MuiDataGrid-columnHeader:last-child .MuiDataGrid-columnSeparator": {
            display: "none",
          },
        }}
        showColumnVerticalBorder
        showCellVerticalBorder
        apiRef={apiRef}
        rows={rows}
        columns={columns}
        editMode="row"
        rowModesModel={rowModesModel}
        onRowModesModelChange={handleRowModesModelChange}
        onRowEditStop={handleRowEditStop}
        processRowUpdate={processRowUpdate}
        initialState={{
          pagination: {paginationModel: {pageSize: 5}},
        }}
        pageSizeOptions={[5, 10, 25]}
        slots={{
          toolbar: () => {
            return (
              <GridToolbarContainer>
                <Button color="primary" startIcon={<AddIcon />} onClick={handleAddNewRow}>
                  {t("cibil.newLoanEntryTable.addNewRecord")}
                </Button>
              </GridToolbarContainer>
            );
          },
        }}
        slotProps={{
          toolbar: {setRows, setRowModesModel},
        }}
      />
    </>
  );
}
