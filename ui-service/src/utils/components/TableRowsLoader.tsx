import React from "react";
import {TableRow, TableCell, Skeleton} from "@mui/material";

interface LoaderTableRowProps {
  rowsNum: number;
  colsNum: number;
}

const LoaderTableRow: React.FC<LoaderTableRowProps> = ({rowsNum, colsNum}) => {
  const renderTableCells = () => {
    const cells = [];

    for (let i = 0; i < colsNum; i++) {
      cells.push(
        <TableCell key={i}>
          <Skeleton animation="wave" variant="text" />
        </TableCell>,
      );
    }

    return cells;
  };

  const renderTableRows = () => {
    const rows = [];

    for (let i = 0; i < rowsNum; i++) {
      rows.push(
        <TableRow key={i}>
          {renderTableCells()}
        </TableRow>,
      );
    }

    return rows;
  };

  return <>{renderTableRows()}</>;
};

export default LoaderTableRow;
