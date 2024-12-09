import React from "react";
import {deleteEmiEntry} from "utils/api/request";
import "styles/cibil.scss";
import {Button} from "@mui/material";

type EmiEntry = {
  interestId: number;
  typeOfLoan: string;
  interest: number;
  tenure: number;
};

const EmiMasterTable: React.FC<{
  entries: EmiEntry[];
  onEdit: (entry: EmiEntry) => void;
}> = ({entries, onEdit}) => {
  const handleDelete = (id: number) => {
    deleteEmiEntry(id);
  };

  return (
    <div className="table-container1">
      <table>
        <thead>
          <tr>
            <th>Type of Loan</th>
            <th>Interest</th>
            <th>Tenure</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {entries.map((entry) => (
            <tr key={entry.interestId}>
              <td>{entry.typeOfLoan}</td>
              <td>{entry.interest}%</td>
              <td>{entry.tenure}</td>
              <td className="action-buttons">
                <Button
                  onClick={() => onEdit(entry)}
                  variant="contained"
                  size="small"
                  style={{marginRight: "10px"}}
                >
                  Edit
                </Button>
                <Button
                  onClick={() => handleDelete(entry.interestId)}
                  variant="contained"
                  size="small"
                  style={{marginRight: "10px"}}
                >
                  Delete
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default EmiMasterTable;
