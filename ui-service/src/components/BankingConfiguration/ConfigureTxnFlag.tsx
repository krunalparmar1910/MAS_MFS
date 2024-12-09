import AddIcon from "@mui/icons-material/Add";
import CancelIcon from "@mui/icons-material/Close";
import EditIcon from "@mui/icons-material/Edit";
import SaveIcon from "@mui/icons-material/Save";
import {
  Alert,
  AlertColor,
  Button,
  Chip,
  FormControl,
  IconButton,
  InputBase,
  MenuItem,
  Paper,
  Select,
  SelectChangeEvent,
  Snackbar, Typography,
} from "@mui/material";
import Box from "@mui/material/Box";
import {
  DataGrid,
  GridActionsCellItem,
  GridColDef,
  GridEventListener,
  GridRowEditStopReasons,
  GridRowId,
  GridRowModes,
  GridRowModesModel,
  GridToolbarContainer, useGridApiRef, GridPreProcessEditCellProps,
} from "@mui/x-data-grid";
import {AxiosError} from "axios";
import {DebitCredit, MasterIdentifier, MasterIdentifierMinified, MasterRuleData} from "interfaces/bankingConfiguration";
import * as React from "react";
import {useState} from "react";
import {useTranslation} from "react-i18next";
import {deleteBankingMasterRule, saveTransactionFlag, updateBankingMasterRule} from "utils/api/request";
import {isValidParentheses} from "utils/utilFunctions";
import {CustomRuleQueryComponent, MultiSelectDropdown} from "./CustomComponent";

interface MasterRuleRow extends MasterRuleData {
  isNew: boolean
}

interface ConfigureTxnFlagPropsType {
  masterData: MasterRuleData[],
  updateMasterRule: (newRow: MasterIdentifier) => void,
  onRowDelete: () => void,
  transactionTypeOptions: { [key: number]: string }
  categoryOptions: { [key: number]: string }
  partiesOptions: { [key: number]: string }
  loading: boolean
}
interface TxnFlagDetails {
  id: number,
  isDeletable: boolean,
  isComplete: boolean
}

const defaultRuleQuery = "transactionType connector1 category connector2 parties connector3 identificationValue";

export default function ConfigureTxnType(props: ConfigureTxnFlagPropsType) {
  const {t} = useTranslation();
  const {masterData,onRowDelete,transactionTypeOptions,
    categoryOptions, partiesOptions, updateMasterRule, loading} = props;

  const apiRef = useGridApiRef();

  const [rows, setRows] = React.useState<MasterRuleRow[]>([]);
  const [rowModesModel, setRowModesModel] = React.useState<GridRowModesModel>({});
  const [error, setError] = useState<AxiosError | null>();
  const [showNotification, setShowNotification] = useState(false);
  const [newTxnFlag, setNewTxnFlag] = useState("");
  const [txnFlags, setTxnFlags] = useState<Map<string, TxnFlagDetails>>(new Map());
  const [txnFlagOptions, setTxnFlagOptions] = useState<{ id: number, label: string, value: string }[]>([]);
  const [alertContent, setAlertContent] = useState<{
    value: string,
    severity: AlertColor
  }>({value: "", severity: "success"});

  const retrieveConnectors = (details: MasterRuleData) => {
    let connectors = {};
    if (details.ruleQuery) {
      const regex = /[AND{1}OR{1}]+/g;
      const found = details.ruleQuery.match(regex);
      if (found && found.length === 3) {
        const connector1 = found[0];
        const connector2 = found[1];
        const connector3 = found[2];
        connectors = {connector1, connector2, connector3};
      }
    }
    return connectors;
  };
  React.useEffect(() => {
    const tempRows = masterData?.filter((data) => data.completed).map((details: MasterRuleData) => {
      const connectorObj = retrieveConnectors(details);
      return {
        ...details,
        isNew: false,
        ...connectorObj,
      };
    });
    const tempTxnFlags = new Map<string, TxnFlagDetails>();
    masterData?.forEach((data) => {
      tempTxnFlags.set(data.transactionFlag, {
        id: data.id,
        isComplete: data.completed,
        isDeletable: data.deletable,
      });
    });
    const tempTxnFlagOptions = masterData.map((data) => {
      return ({
        id: data.id,
        label: data.transactionFlag,
        value: data.transactionFlag,
      });
    });
    setTxnFlagOptions(tempTxnFlagOptions);
    setTxnFlags(tempTxnFlags);
    setRows(tempRows || []);
  }, [masterData]);

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

  const handleDeleteClick = (id: number) => () => {
    deleteBankingMasterRule(id)
      .then((data) => {
        onRowDelete();
        setAlertContent({value: data.data.message ||
          t("configureTxnFlag.alert.rowDeletedSuccessfully"), severity: "success"});
      })
      .catch((error) => {
        setAlertContent({
          value: error.response.data.message || t("configureTxnFlag.alert.errorWhileDeleting"), severity: "error"});
      })
      .finally(() => setShowNotification(true));
  };

  const handleCancelClick = (id: GridRowId) => () => {
    setRowModesModel({
      ...rowModesModel,
      [id]: {mode: GridRowModes.View, ignoreModifications: true},
    });

    const editedRow = rows.find((row: MasterRuleRow) => row.id === id);
    if (editedRow!.isNew) {
      setRows(rows.filter((row: MasterRuleRow) => row.id !== id));
    }
  };

  const processRowUpdate = async (updatedRow: MasterRuleRow, oldRow: MasterRuleRow) => {
    let tempUpdatedRow: MasterRuleRow = {...updatedRow, isNew: false};
    let isRowUpdated = false;
    updateBankingMasterRule({
      id: updatedRow.id===-1? txnFlags.get(updatedRow.transactionFlag)?.id : updatedRow.id,
      transactionTypeIdList: updatedRow.transactionTypeList.map((obj) => obj.id),
      categoryIdList: updatedRow.categoryList.map((obj) => obj.id),
      partiesMerchantIdList: updatedRow.partiesOrMerchantList.map((obj) => obj.id),
      identificationValue: updatedRow.identificationValue,
      debitCredit: updatedRow.debitOrCredit,
      transactionFlag: updatedRow.transactionFlag,
      ruleQuery: updatedRow.ruleQuery,
    })
      .then((data) => {
        if (data?.message === "Success") {
          isRowUpdated = true;
          tempUpdatedRow = data?.data;
          updateMasterRule(data.data);
          setError(null);
          setAlertContent({value: t("configureTxnFlag.alert.rowUpdatedSuccessfully"), severity: "success"});
        }
      })
      .catch((error) => {
        apiRef.current.startRowEditMode({id: updatedRow.id});
        setAlertContent({
          value: error.response.data.message ||
            t("configureTxnFlag.alert.errorWhileUpdating"), severity: "error",
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
      const newRow: MasterRuleRow = {
        id: id,
        isNew: true,
        transactionTypeList: [],
        categoryList: [],
        partiesOrMerchantList: [],
        identificationValue: "",
        debitOrCredit: DebitCredit.DEBIT,
        transactionFlag: "",
        deletable: true,
        ruleQuery: defaultRuleQuery,
        completed: false,
      };
      setRows((oldRows) => [newRow, ...oldRows]);
      setRowModesModel((oldModel) => ({
        ...oldModel,
        [id]: {mode: GridRowModes.Edit, fieldToFocus: "identifierName"},
      }));
    }
  };

  const handleTxnFlagInput = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    setNewTxnFlag(e.target.value);
  };

  const handleAddNewTxnFlag = () => {
    if (txnFlags.has(newTxnFlag)) {
      return;
    }
    saveTransactionFlag({
      transactionFlag: newTxnFlag,
    })
      .then((data) => {
        const tempMap: Map<string, TxnFlagDetails> = new Map(txnFlags);
        tempMap.set(newTxnFlag, {
          id: data.data.id,
          isDeletable: true,
          isComplete: true,
        });
        setTxnFlags(tempMap);
        setTxnFlagOptions([...txnFlagOptions, {
          id: data.data.id,
          label: data.data.transactionFlag,
          value: data.data.transactionFlag,
        }]);
      })
      .catch((error) => {
        setAlertContent({value: error.response.data.message || "Error while saving new txn flag.", severity: "error"});
      })
      .finally(() => setShowNotification(true));
  };

  const handleDeleteTxnFlag = (txnFlagId: number, txnFlagName: string) => {
    deleteBankingMasterRule(txnFlagId)
      .then(() => {
        setAlertContent({value: "Txn Flag Deleted successfully", severity: "success"});
        const tempMap: Map<string, TxnFlagDetails> = new Map(txnFlags);
        tempMap.delete(txnFlagName);
        setTxnFlags(tempMap);
        onRowDelete();
      })
      .catch((error) => {
        setAlertContent({
          value: error.response.data.message ||
            "Error while deleting the txn flag.", severity: "error",
        });
      })
      .finally(() => setShowNotification(true));
  };

  const preProcesRuleQuery = async (params: GridPreProcessEditCellProps) => {
    const errorMessage = t("configureTxnFlag.expressionError");
    return {...params.props, error: isValidParentheses(params.props.value!.toString()) ? null : errorMessage};
  };

  const columns: GridColDef[] = [
    {
      field: "transactionFlag",
      headerName: t("configureTxnFlag.columns.transactionFlag"),
      type: "singleSelect",
      valueOptions: txnFlagOptions,
      renderCell(params) {
        return (params.value);
      },
      flex: 1.5,
      editable: true,
      filterable: false,
      sortable: false,
      disableColumnMenu: true,
    },
    {
      field: "debitOrCredit",
      headerName: t("configureTxnFlag.columns.debitOrCredit"),
      type: "singleSelect",
      valueOptions: [
        {value: DebitCredit.DEBIT, label: "Debit"},
        {value: DebitCredit.CREDIT, label: "Credit"},
        {value: DebitCredit.BOTH, label: "Both"}],
      flex: 1,
      editable: true,
      filterable: false,
      sortable: false,
      disableColumnMenu: true,
    },
    {
      field: "transactionTypeList",
      headerName: t("configureTxnFlag.columns.transactionType"),
      flex: 1,
      align: "center",
      headerAlign: "center",
      editable: true,
      filterable: false,
      sortable: false,
      disableColumnMenu: true,
      renderCell(params) {
        return (
          params.value.map((value: MasterIdentifierMinified) => value.identifierName)
            .join(",")
        );
      },
      renderEditCell: (params) => (
        <MultiSelectDropdown
          options={transactionTypeOptions}
          updateRow={(selected: string[]) => {
            apiRef.current.setEditCellValue({
              id: params.id,
              field: params.field,
              value: selected.map((id: string) => {
                return {
                  id: parseInt(id),
                  identifierName: transactionTypeOptions[parseInt(id)],
                };
              },
              ),
            });
          }}
          alreadySelectedValues={params.value}
        />
      ),
    },
    {
      field: "connector1",
      headerName: t("configureTxnFlag.columns.connector"),
      type: "singleSelect",
      valueOptions: ["AND", "OR"],
      flex: 1,
      editable: true,
      filterable: false,
      sortable: false,
      disableColumnMenu: true,
    },
    {
      field: "categoryList",
      headerName: t("configureTxnFlag.columns.categoryList"),
      flex: 1,
      align: "center",
      headerAlign: "center",
      editable: true,
      filterable: false,
      sortable: false,
      disableColumnMenu: true,
      renderCell(params) {
        return (
          params.value.map((value: MasterIdentifierMinified) => value.identifierName)
            .join(",")
        );
      },
      renderEditCell: (params) => (
        <MultiSelectDropdown
          options={categoryOptions}
          updateRow={(selected: string[]) => {
            apiRef.current.setEditCellValue({
              id: params.id,
              field: params.field,
              value: selected.map((id: string) => {
                return {
                  id: parseInt(id),
                  identifierName: categoryOptions[parseInt(id)],
                };
              },
              ),
            });
          }}
          alreadySelectedValues={params.value}
        />
      ),
    },
    {
      field: "connector2",
      headerName: t("configureTxnFlag.columns.connector"),
      type: "singleSelect",
      valueOptions: ["AND", "OR"],
      flex: 1,
      editable: true,
      filterable: false,
      sortable: false,
      disableColumnMenu: true,
    },
    {
      field: "partiesOrMerchantList",
      headerName: t("configureTxnFlag.columns.partiesOrMerchantList"),
      flex: 1,
      align: "center",
      headerAlign: "center",
      editable: true,
      filterable: false,
      sortable: false,
      disableColumnMenu: true,
      renderCell(params) {
        return (
          params.value.map((value: MasterIdentifierMinified) => value.identifierName)
            .join(",")
        );
      },
      renderEditCell: (params) => (
        <MultiSelectDropdown
          options={partiesOptions}
          updateRow={(selected: string[]) => {
            apiRef.current.setEditCellValue({
              id: params.id,
              field: params.field,
              value: selected.map((id: string) => {
                return {
                  id: parseInt(id),
                  identifierName: partiesOptions[parseInt(id)],
                };
              },
              ),
            });
          }}
          alreadySelectedValues={params.value}
        />
      ),
    },
    {
      field: "connector3",
      headerName: t("configureTxnFlag.columns.connector"),
      type: "singleSelect",
      valueOptions: ["AND", "OR"],
      flex: 1,
      editable: true,
      filterable: false,
      sortable: false,
      disableColumnMenu: true,
    },
    {
      field: "identificationValue",
      headerName: t("configureTxnFlag.columns.identificationValue"),
      flex: 1,
      editable: true,
      filterable: false,
      sortable: false,
      disableColumnMenu: true,
      valueFormatter: (value: string) => {
        return value;
      },
    },
    {
      field: "ruleQuery",
      headerName: t("configureTxnFlag.columns.ruleQuery"),
      flex: 5,
      editable: true,
      filterable: false,
      sortable: false,
      disableColumnMenu: true,
      renderEditCell: (params) => <CustomRuleQueryComponent {...params} />,
      preProcessEditCellProps: preProcesRuleQuery,
    },
    {
      field: "actions",
      type: "actions",
      headerName: t("configureTxnFlag.columns.actions"),
      flex: 0.5,
      cellClassName: "actions",
      getActions: ({id, row}) => {
        const isInEditMode = rowModesModel[id]?.mode === GridRowModes.Edit;
        if (isInEditMode) {
          return [
            <GridActionsCellItem
              key={1}
              icon={<SaveIcon />}
              label={t("configureTxnFlag.saveLabel")}
              sx={{
                color: "primary.main",
              }}
              onClick={handleSaveClick(id)}
            />,
            <GridActionsCellItem
              key={2}
              icon={<CancelIcon />}
              label={t("configureTxnFlag.cancelLabel")}
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
            label={t("configureTxnFlag.editLabel")}
            className="textPrimary"
            onClick={handleEditClick(id)}
            color="inherit"
          />,
        ];
      },
    },
  ];

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
      <Typography variant="h5">Transaction flags</Typography>
      <div className="flex-row-justify-between">
        <Paper>
          <InputBase
            error={txnFlags.has(newTxnFlag)}
            placeholder="Txn Flag"
            onChange={handleTxnFlagInput}
          />
          <IconButton type="submit" onClick={handleAddNewTxnFlag}>
            <AddIcon />
          </IconButton>
        </Paper>
      </div>
      {Array.from(txnFlags.entries()).map((value) => {
        return (
          <Chip
            key={value[0]}
            label={value[0]}
            variant="outlined"
            onDelete={value[1].isDeletable ? () => handleDeleteTxnFlag(value[1].id || 0, value[0]) : undefined}
          />
        );
      })}
      <Typography variant="h5">{t("configureTxnFlag.masterRules")}</Typography>
      <Box
        sx={{
          width: "100%",
          "& .actions": {
            color: "text.secondary",
          },
          "& .textPrimary": {
            color: "text.primary",
          },
        }}
      >
        <DataGrid
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
          loading={loading}
          autoHeight
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
                <GridToolbarContainer>
                  <Button color="primary" startIcon={<AddIcon />} onClick={handleAddNewRow}>
                    {t("configureTxnFlag.addRecord")}
                  </Button>
                </GridToolbarContainer>
              );
            },
          }}
        />
      </Box>
    </>
  );
}