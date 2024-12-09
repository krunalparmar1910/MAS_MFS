/*eslint-disable*/
import AddIcon from "@mui/icons-material/Add";
import CancelIcon from "@mui/icons-material/Close";
import DeleteIcon from "@mui/icons-material/DeleteOutlined";
import EditIcon from "@mui/icons-material/Edit";
import SaveIcon from "@mui/icons-material/Save";
import {Alert, AlertColor, Button, Snackbar, TextField, Typography} from "@mui/material";
import {
  DataGrid,
  GridActionsCellItem,
  GridColDef,
  GridEventListener,
  GridRowEditStopReasons,
  GridRowId,
  GridRowModes,
  GridRowModesModel,
  GridToolbarContainer,
  useGridApiRef,
} from "@mui/x-data-grid";
import {AxiosError} from "axios";
import {DebitCredit, IdentifierType, MasterIdentifier} from "interfaces/bankingConfiguration";
import * as React from "react";
import {useState} from "react";
import { useTranslation } from "react-i18next";
import {deleteBankingMasterIdentifiers, updateBankingMasterIdentifiers} from "utils/api/request";

interface PropsType {
  identifierData: MasterIdentifier[],
  updateRowsMap: (identifier: IdentifierType, newRow: MasterIdentifier) => void,
  identifierType: IdentifierType
  onRowDelete: () => void
  loading: boolean
}

interface MasterIdentifierRow extends MasterIdentifier {
  isNew: boolean
}

export default function ConfigureTxnType(props: PropsType) {
  const {t} = useTranslation();

  const apiRef = useGridApiRef();

  const [rows, setRows] = React.useState<MasterIdentifierRow[]>([]);
  const [rowModesModel, setRowModesModel] = React.useState<GridRowModesModel>({});
  const [error, setError] = useState<AxiosError | null>();
  const [showNotification, setShowNotification] = useState(false);
  const [alertContent, setAlertContent] = useState<{
    value: string,
    severity: AlertColor
  }>({value: "", severity: "success"});

  const {identifierData, updateRowsMap, identifierType, onRowDelete, loading} = props;

  React.useEffect(() => {
    const tempRows = identifierData?.map((details: MasterIdentifier) => {
      return {
        ...details,
        isNew: false,
      };
    });
    setRows(tempRows || []);
  }, [identifierData]);

  const handleRowEditStop: GridEventListener<"rowEditStop"> = (params, event) => {
    if (params.reason === GridRowEditStopReasons.rowFocusOut) {
      event.defaultMuiPrevented = true;
    }
  };

  const handleEditClick = (id: GridRowId) => () => {
    setRowModesModel({...rowModesModel, [id]: {mode: GridRowModes.Edit}});
  };

  const handleSaveClick = (id: GridRowId) => () => {
    setRowModesModel({...rowModesModel, [id]: {mode: GridRowModes.View}});
  };

  const handleDeleteClick = (id: GridRowId) => () => {

    let temp = "";
    if (identifierType === IdentifierType.transactionType) {
      temp = "transaction-type-identifier";
    } else if (identifierType === IdentifierType.category) {
      temp = "category-identifier";
    } else {
      temp = "parties-identifier";
    }

    deleteBankingMasterIdentifiers(temp, id.toString())
      .then((data) => {
        onRowDelete();
        setAlertContent({value: t("configureMasterIdentifiers.alert.rowDeletedSuccessfully"), severity: "success"});
      })
      .catch((error) => {
        setAlertContent(
          {
            value: error.response.data.message || t("configureMasterIdentifiers.alert.errorWhileUpdating"),
            severity: "error"
          });
      })
      .finally(() => setShowNotification(true));

  };

  const handleCancelClick = (id: GridRowId) => () => {
    setRowModesModel({
      ...rowModesModel,
      [id]: {mode: GridRowModes.View, ignoreModifications: true},
    });

    const editedRow = rows.find((row: MasterIdentifierRow) => row.id === id);
    if (editedRow!.isNew) {
      setRows(rows.filter((row: MasterIdentifierRow) => row.id !== id));
    }
  };

  const processRowUpdate = (updatedRow: MasterIdentifierRow, oldRow: MasterIdentifierRow) => {
    let tempUpdatedRow = {...updatedRow, isNew: false};
    let isRowUpdated = false;
    updateBankingMasterIdentifiers({...updatedRow, identifierType: identifierType})
      .then((data: any) => {
        if (data?.message === "Success") {
          isRowUpdated = true;
          tempUpdatedRow = data?.data;
          updateRowsMap(identifierType, data.data);
          setError(null);
          setAlertContent({value: t("configureMasterIdentifiers.alert.rowUpdatedSuccessfully"), severity: "success"});
        }
      })
      .catch((error) => {
        apiRef.current.startRowEditMode({id: updatedRow.id});
        setAlertContent(
          {
            value: error.response.data.message || t("configureMasterIdentifiers.alert.errorWhileUpdating"),
            severity: "error"
          });
      })
      .finally(() => setShowNotification(true));
    return isRowUpdated ? tempUpdatedRow : oldRow;
  };

  const handleRowModesModelChange = (newRowModesModel: GridRowModesModel) => {
    setRowModesModel(newRowModesModel);
  };

  const handleAddNewRow = () => {
    if (rows.find(row => row.id === -1)) {
      return;
    } else {
      const id = -1;
      const newRow: MasterIdentifierRow = {
        id: id,
        isNew: true,
        identifierType: identifierType,
        identificationValue: "",
        identifierName: "",
        debitCredit: DebitCredit.DEBIT,
        deletable: true,
      };
      setRows((oldRows) => [newRow, ...oldRows]);
      setRowModesModel((oldModel) => ({
        ...oldModel,
        [id]: {mode: GridRowModes.Edit, fieldToFocus: "identifierName"},
      }));
    }
  };

  const columns: GridColDef[] = [
    {
      field: "identifierName",
      headerName: identifierType==="PARTIES_OR_MERCHANT"?"Parties/Merchant":identifierType,
      flex: 1,
      align: "center",
      headerAlign: "center",
      editable: true,
      filterable: false,
      sortable: false,
      disableColumnMenu: true,
      renderEditCell(params) {
        const {id,field} = params;
        return( 
        <TextField 
          style={{width: "100%"}}
          onChange={(e)=>{
            const newValue = e.target.value;
            apiRef.current.setEditCellValue({id, field, value: newValue });
          }}
          value={params.value}
          disabled={!params.row.deletable}
        />)
      },
    },
    {
      field: "identificationValue",
      headerName: t("configureMasterIdentifiers.columns.identificationValue"),
      flex: 1,
      align: "center",
      headerAlign: "center",
      editable: true,
      filterable: false,
      sortable: false,
      disableColumnMenu: true,
    },
    {
      field: "debitCredit",
      headerName: t("configureMasterIdentifiers.columns.debitCredit"),
      type: "singleSelect",
      valueOptions: [
        {value: DebitCredit.DEBIT, label: "Debit"},
        {value: DebitCredit.CREDIT, label: "Credit"},
        {value: DebitCredit.BOTH, label: "Both"}],
      flex: 1,
      align: 'center',
      headerAlign: "center",
      editable: true,
      filterable: false,
      sortable: false,
      disableColumnMenu: true,
    },
    {
      field: "actions",
      type: "actions",
      headerName: t("configureMasterIdentifiers.columns.actions"),
      cellClassName: "actions",
      flex: 0.75,
      getActions: (params) => {
        const {row, id} = params;
        const isInEditMode = rowModesModel[id]?.mode === GridRowModes.Edit;
        if (isInEditMode) {
          return [
            <GridActionsCellItem
              key={1}
              icon={<SaveIcon />}
              label={t("configureMasterIdentifiers.saveLabel")}
              sx={{
                color: "primary.main",
              }}
              onClick={handleSaveClick(id)}
            />,
            <GridActionsCellItem
              key={2}
              icon={<CancelIcon />}
              label={t("configureMasterIdentifiers.cancelLabel")}
              className="textPrimary"
              onClick={handleCancelClick(id)}
              color="inherit"
            />,
          ];
        }

        return [
          <GridActionsCellItem
            key={3}
            icon={<EditIcon />}
            label={t("configureMasterIdentifiers.editLabel")}
            className="textPrimary"
            onClick={handleEditClick(id)}
            color="inherit"
          />,
          <GridActionsCellItem
            key={4}
            icon={<DeleteIcon />}
            label={t("configureMasterIdentifiers.deleteLabel")}
            onClick={handleDeleteClick(id)}
            color="inherit"
            disabled={!row.deletable}
          />,
        ];
      },
    },
  ];
  let intlHeading: string = "configureTxnFlag.transactionType";
  if (identifierType === IdentifierType.category) {
    intlHeading = "configureTxnFlag.category";
  } else if (identifierType === IdentifierType.partiesMerchant) {
    intlHeading = "configureTxnFlag.partiesMerchant";
  }
  return (
    <>
      <Snackbar open={showNotification} autoHideDuration={5000} onClose={() => setShowNotification(false)}>
        <Alert
          severity={alertContent.severity}
          variant="filled"
        >
          {alertContent.value}
        </Alert>
      </Snackbar>
        <DataGrid
          className="mv-10"
          showCellVerticalBorder
          showColumnVerticalBorder
          columnHeaderHeight={30}
          apiRef={apiRef}
          rows={rows}
          columns={columns}
          editMode="row"
          initialState={{
            pagination: {paginationModel: {pageSize: 5}},
          }}
          pageSizeOptions={[5, 10, 25]}
          autoHeight
          loading={loading}
          getRowHeight={() => "auto"}
          rowModesModel={rowModesModel}
          onRowModesModelChange={handleRowModesModelChange}
          onRowEditStop={handleRowEditStop}
          processRowUpdate={processRowUpdate}
          slotProps={{
            toolbar: {setRows, setRowModesModel},
          }}
          slots={{
            toolbar: () => {
              return (
                <GridToolbarContainer className="flex-row-justify-between ph-10">
                  <Typography className="flex-row-justify-center" variant="h5">{t(intlHeading)}</Typography>
                  <Button variant="outlined" color="primary" startIcon={<AddIcon />} onClick={handleAddNewRow}>
                    {t("configureMasterIdentifiers.addRecord")}
                  </Button>
                </GridToolbarContainer>
              );
            },
          }}
        />
    </>
  );
}