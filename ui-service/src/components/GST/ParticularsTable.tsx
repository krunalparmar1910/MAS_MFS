import {
  Checkbox,
  InputBase,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import React, {useEffect, useState} from "react";
import {getRoundedOffNumberWithCommas} from "utils/numberFormat";
import {ParticularsDataMap} from "components/GST/SalesTable";
import {useTranslation} from "react-i18next";
import i18next from "utils/i18n";

type Row = [
  boolean | undefined,
  string | undefined,
  number | undefined,
  string | undefined,
  number | undefined,
  boolean | undefined,
  string | undefined,
  number | undefined,
  string | undefined,
  number | undefined
];

interface Column {
  id: number;
  type:
  | "topSuppliers"
  | "asPerGST"
  | "addToInterfirmCheck"
  | "asPerGSTPercent"
  | "asPerBanking"
  | "topCustomers"
  | "density";
  label: React.ReactNode;
  align?: "right" | "left" | "center";
  format?: (value: number) => string;
}

interface CombinedObject {
  srNo: number,
  supplierName?: string,
  addSupplierToInterfirm?: boolean,
  supplierAmountAsPerGst?: number,
  supplierAmountPercentAsPerGst?: string,
  supplierAmountAsPerBanking?: number
  customerName?: string
  addCustomerToInterfirm?: boolean,
  customerAmountAsPerGst?: number,
  customerAmountPercentAsPerGst?: string,
  customerAmountAsPerBanking?: number
}

const columns: Column[] = [
  {
    id: 1,
    type: "addToInterfirmCheck",
    align: "left",
    label: i18next.t("particularsTable.columns.add"),
  },
  {
    id: 2,
    type: "topSuppliers",
    align: "left",
    label: i18next.t("particularsTable.columns.topSuppliers"),
    format: (value: number) => i18next.t(value.toString()),
  },
  {
    id: 3,
    type: "asPerGST",
    align: "left",
    label: i18next.t("particularsTable.columns.asPerGst"),
    format: (value: number) => getRoundedOffNumberWithCommas(value),
  },
  {
    id: 4,
    type: "asPerGSTPercent",
    align: "left",
    label: i18next.t("particularsTable.columns.asPerGSTPercent"),
  },
  {
    id: 5,
    type: "asPerBanking",
    label: i18next.t("particularsTable.columns.asPerBanking"),
    align: "center",
    format: (value: number) => i18next.t(value.toString()),
  },
  {
    id: 6,
    type: "addToInterfirmCheck",
    label: i18next.t("particularsTable.columns.add"),
    align: "left",
    format: (value: number) => getRoundedOffNumberWithCommas(value),
  },
  {
    id: 7,
    type: "topCustomers",
    label: i18next.t("particularsTable.columns.topCustomers"),
    align: "left",
    format: (value: number) => i18next.t(value.toString()),
  },
  {
    id: 8,
    type: "asPerGST",
    label: i18next.t("particularsTable.columns.asPerGst"),
    align: "left",
    format: (value: number) => getRoundedOffNumberWithCommas(value),
  },
  {
    id: 9,
    type: "asPerGSTPercent",
    align: "left",
    label: i18next.t("particularsTable.columns.asPerGSTPercent"),
  },
  {
    id: 10,
    type: "asPerBanking",
    label: i18next.t("particularsTable.columns.asPerBanking"),
    align: "center",
    format: (value: number) => i18next.t(value.toString()),
  },
];

const ParticularsTable = (props: {
  particularsDataMap: ParticularsDataMap,
  setParticularsDataMap: React.Dispatch<React.SetStateAction<ParticularsDataMap>>,
  updateInterfirm: (asPerGst:number, newCheckedValue: boolean, isSupplier: boolean)=>void
 }) => {

  const {particularsDataMap, setParticularsDataMap, updateInterfirm} = props;

  const [suppliersBankingTotal, setSuppliersBankingTotal] = useState(0);
  const [customersBankingTotal, setCustomersBankingTotal] = useState(0);

  const [suppliersGstTotal, setSuppliersGstTotal] = useState(0);
  const [customersGstTotal, setCustomersGstTotal] = useState(0);

  const createDataNew = (combinedObject: CombinedObject): Row => {
    return [
      combinedObject.addSupplierToInterfirm,
      combinedObject.supplierName,
      combinedObject.supplierAmountAsPerGst,
      combinedObject.supplierAmountPercentAsPerGst,
      combinedObject.supplierAmountAsPerBanking,
      combinedObject.addCustomerToInterfirm,
      combinedObject.customerName,
      combinedObject.customerAmountAsPerGst,
      combinedObject.customerAmountPercentAsPerGst,
      combinedObject.customerAmountAsPerBanking];
  };

  const createCombinedObjectArray = () => {
    const combinedObjectArray: CombinedObject[] = [];
    let srNo = 1;
    if (particularsDataMap?.suppliers) {
      const map: ParticularsDataMap = particularsDataMap;
      for (const [key, value] of Object.entries(map.suppliers)) {
        const combinedObject: CombinedObject = {
          supplierName: key,
          addSupplierToInterfirm: value.numericEntryAddToInterfirm || false,
          supplierAmountAsPerGst: value.adjustedPurchaseAndExpenses,
          supplierAmountPercentAsPerGst: value.adjustedPurchaseAndExpensesPercent,
          supplierAmountAsPerBanking: value.numericEntryAsPerBanking || 0,
          srNo: srNo++,
        };
        combinedObjectArray.push(combinedObject);
      }
    }
    let i = 0;
    if (particularsDataMap?.customers) {

      const map: ParticularsDataMap = particularsDataMap;
      for (const [key, value] of Object.entries(map.customers)) {
        combinedObjectArray[i] = {
          ...combinedObjectArray[i],
          customerName: key,
          addCustomerToInterfirm: value.numericEntryAddToInterfirm || false,
          customerAmountAsPerGst: value.adjustedRevenue,
          customerAmountPercentAsPerGst: value.adjustedRevenuePercent,
          customerAmountAsPerBanking: value.numericEntryAsPerBanking || 0,
        };
        i++;
      }
    }
    return combinedObjectArray;
  };

  const [rowsData, setRowsData] = useState<Row[]>([]);

  const getRowsData = () => {
    let tempSupplierGstTotal = 0;
    let tempCustomerGstTotal = 0;
    const tempRowsData = [
      ...createCombinedObjectArray().map((item: CombinedObject) => {
        tempSupplierGstTotal += item.supplierAmountAsPerGst ?? 0;
        tempCustomerGstTotal += item.customerAmountAsPerGst ?? 0;
        return createDataNew(item);
      }),
    ];
    setSuppliersGstTotal(tempSupplierGstTotal);
    setCustomersGstTotal(tempCustomerGstTotal);
    return tempRowsData;
  };

  useEffect(() => {
    setRowsData(() => getRowsData());
    let suppliersTotal = 0;
    for (const [, value] of Object.entries(particularsDataMap?.suppliers)) {
      suppliersTotal += Number(value.numericEntryAsPerBanking) || 0;
    }
    setSuppliersBankingTotal(suppliersTotal);
    let customersTotal = 0;

    for (const [, value] of Object.entries(particularsDataMap?.customers)) {
      customersTotal += Number(value.numericEntryAsPerBanking) || 0;
    }
    setCustomersBankingTotal(customersTotal);
  }, [particularsDataMap]);

  const handleInput = async (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
    row: Row, column: Column) => {
    const inputObject = {
      name: column.id === 5 ? row[1] : row[6],
      value: Number(e.target.value),
    };
    if (column.id === 5) {
      const tMap: ParticularsDataMap = {
        ...particularsDataMap,
        suppliers: {
          ...particularsDataMap.suppliers,
          [inputObject.name!]: {...particularsDataMap.suppliers[inputObject.name!],
            numericEntryAsPerBanking: inputObject.value},
        },
      };
      setParticularsDataMap(() => tMap);
    } else {
      const tMap: ParticularsDataMap = {
        ...particularsDataMap,
        customers: {
          ...particularsDataMap.customers,
          [inputObject.name!]: {...particularsDataMap.customers[inputObject.name!],
            numericEntryAsPerBanking: inputObject.value},
        },
      };
      setParticularsDataMap(() => tMap);
    }
  };

  const handleCheckBoxInput = async (e:React.ChangeEvent<HTMLInputElement>, row: Row,
    column: Column) => {
    const inputObject = {
      name: column.id === 1 ? row[1] : row[6],
      value: e.target.checked,
    };
    if (column.id === 1) {
      updateInterfirm(row[2] ?? 0, inputObject.value, true);
      const tMap: ParticularsDataMap = {
        ...particularsDataMap,
        suppliers: {
          ...particularsDataMap.suppliers,
          [inputObject.name!]: {...particularsDataMap.suppliers[inputObject.name!],
            numericEntryAddToInterfirm: inputObject.value},
        },
      };
      setParticularsDataMap(() => tMap);
    } else {
      updateInterfirm(row[7] || 0, inputObject.value, false);
      const tMap: ParticularsDataMap = {
        ...particularsDataMap,
        customers: {
          ...particularsDataMap.customers,
          [inputObject.name!]: {...particularsDataMap.customers[inputObject.name!],
            numericEntryAddToInterfirm: inputObject.value},
        },
      };
      setParticularsDataMap(() => tMap);
    }
  };

  const getAmtAsPerBanking = (row: Row, isSupplier: boolean) => {
    if (isSupplier) {
      return particularsDataMap.suppliers[row[1]!]?.numericEntryAsPerBanking || "";
    } else {
      return particularsDataMap.customers[row[6]!]?.numericEntryAsPerBanking || "";
    }
  };

  const getCheckedStatus = (row: Row, isSupplier: boolean) => {
    if (isSupplier) {
      return particularsDataMap.suppliers[row[1]!]?.numericEntryAddToInterfirm || false;
    } else {
      return particularsDataMap.customers[row[6]!]?.numericEntryAddToInterfirm || false;
    }
  };

  const {t} = useTranslation();

  return (
    <TableContainer className="mv-10" component={Paper}>
      <Table aria-label="customized table">
        <TableHead>
          <TableRow>
            <TableCell align="center" colSpan={2} >{t("salesTable.particulars")}</TableCell>
            <TableCell align="center" colSpan={3}>{t("particularsTable.amount")}</TableCell>
            <TableCell align="center" colSpan={2}>{t("salesTable.particulars")}</TableCell>
            <TableCell align="center" colSpan={3}>{t("particularsTable.amount")}</TableCell>
          </TableRow>
          <TableRow>
            {columns.map((column) => (
              <TableCell
                key={column.id}
                align={column.align}
              >
                {column.label}
              </TableCell>
            ))}
          </TableRow>
        </TableHead>
        <TableBody>
          {rowsData.map((row, i) => {
            return (
              <TableRow key={i}>
                {columns.map((column, index) => {
                  const value = row[index];
                  if (value !== undefined && column?.type === "asPerBanking"){
                    return (
                      // eslint-disable-next-line react/jsx-key
                      <TableCell align="center">
                        <InputBase
                          type="number"
                          value={t(String(getAmtAsPerBanking(rowsData[i], index <= columns.length/2)))}
                          onChange={(e) => handleInput(e, row, column)}
                          size="small"
                          placeholder="0"
                        />
                      </TableCell>
                    );
                  } else if (value !== undefined &&
                    column?.type === "addToInterfirmCheck"){
                    return (
                      // eslint-disable-next-line react/jsx-key
                      <TableCell align="center">
                        <Checkbox
                          checked={getCheckedStatus(rowsData[i], index===0)}
                          onChange={(e) => handleCheckBoxInput(e, row, column)}
                        />
                      </TableCell>
                    );
                  } else {
                    return (
                      <TableCell key={column.id} align={column.align}>
                        {column.format && typeof value === "number"
                          ? column.format(value)
                          : value}
                      </TableCell>
                    );
                  }

                })}
              </TableRow>
            );
          })}
          <TableRow >
            <TableCell align="right" colSpan={2}>{t("particularsTable.total")}</TableCell>
            <TableCell align="left">{t(getRoundedOffNumberWithCommas(suppliersGstTotal))}</TableCell>
            <TableCell align="left">{t("100.00%")}</TableCell>
            <TableCell align="center">
              {t(getRoundedOffNumberWithCommas(suppliersBankingTotal))}
            </TableCell>
            <TableCell align="right" colSpan={2}>{t("particularsTable.total")}</TableCell>
            <TableCell align="left">{t(getRoundedOffNumberWithCommas(customersGstTotal))}</TableCell>
            <TableCell align="left">{t("100.00%")}</TableCell>
            <TableCell align="center">
              {t(getRoundedOffNumberWithCommas(customersBankingTotal))}
            </TableCell>
          </TableRow>
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default ParticularsTable;
