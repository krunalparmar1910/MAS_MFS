import {MasterIdentifier, MasterIdentifierMinified} from "interfaces/bankingConfiguration";
import {
  FormControl,
  MenuItem,
  Select,
  SelectChangeEvent,
  TextField,
} from "@mui/material";
import {
  useGridApiContext,
  GridValidRowModel, GridRenderEditCellParams,
} from "@mui/x-data-grid";
import * as React from "react";

// regex to match valid rule expression
// Valid expresion example is : (((transactionType OR category) AND parties) OR identificationValue)
// eslint-disable-next-line max-len
const validRuleQueryRegex = new RegExp("^[ ]*[(]*[ ]*transactionType[ ]*[)]*[ ]*[AND{1}OR{1}]+[ ]*[(]*[ ]*category[ ]*[)]*[ ]*[AND{1}OR{1}]+[ ]*[(]*[ ]*parties[ ]*[)]*[ ]*[AND{1}OR{1}]+[ ]*[(]*[ ]*identificationValue[ ]*[()]*[ ]*");

export const CustomRuleQueryComponent = (params: GridRenderEditCellParams) => {
  const {id, field, row, error} = params;
  const apiRef = useGridApiContext();

  const [value, setValue] = React.useState(row.ruleQuery);

  const createRuleQueryFromRow = (row: GridValidRowModel) => {
    let ruleQuery = row.ruleQuery;
    if (ruleQuery.includes("connector1") || ruleQuery.includes("connector2") || ruleQuery.includes("connector3")) {
      if (row.connector1) {
        ruleQuery = ruleQuery.replace("connector1", row.connector1);
      }
      if (row.connector2) {
        ruleQuery = ruleQuery.replace("connector2", row.connector2);
      }
      if (row.connector3) {
        ruleQuery = ruleQuery.replace("connector3", row.connector3);
      }
    } else if (ruleQuery.includes("AND") || ruleQuery.includes("OR")) {
      const regex = /[AND{1}OR{1}]+/g;
      const found = ruleQuery.match(regex);
      if (found && found.length === 3) {
        let count = 1;
        ruleQuery = ruleQuery.replace(/[AND{1}OR{1}]+/g, function() {
          return row[`connector${count++}`];
        });
      }
    }
    return ruleQuery;
  };

  React.useEffect(() => {
    const {row} = params;
    const ruleQuery = createRuleQueryFromRow(row);
    setValue(ruleQuery);
  }, [params.row.ruleQuery, params.row.connector1, params.row.connector2, params.row.connector3]);

  React.useEffect(() => {
    if (validRuleQueryRegex.test(value)) {
      apiRef.current.setEditCellValue({
        id,
        field,
        value,
      });
    }
  }, [value]);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    if ((["(",")"].includes(((event.nativeEvent as InputEvent).data) || "")) ||
    (["deleteContentBackward", "deleteContentForward"].includes((event.nativeEvent as InputEvent).inputType))) {
      const newValue = event.target.value;
      if (validRuleQueryRegex.test(newValue)) {
        apiRef.current.setEditCellValue({
          id,
          field,
          value: newValue,
        });
      }
    }
  };

  return (
    <TextField
      error={!!error}
      label={error}
      className="grid-custom-input"
      value={value}
      type="text"
      variant="filled"
      onChange={handleChange}
    />
  );
};

interface MultiSelectDropdownPropsType {
  options: { [key: number]: string },
  updateRow: (arr: string[]) => void,
  alreadySelectedValues: MasterIdentifier[] | MasterIdentifierMinified[]
}

export const MultiSelectDropdown = (props: MultiSelectDropdownPropsType) => {
  const {options, updateRow, alreadySelectedValues} = props;

  const [selectedOptions, setSelectedOptions] = React.useState(
    alreadySelectedValues?.map((selected) => selected.id.toString()) ||
    []);

  const handleSelectChange = (event: SelectChangeEvent<unknown>) => {
    setSelectedOptions(event.target.value as string[]);
    updateRow(event.target.value as string[]);
  };

  return (
    <FormControl fullWidth>
      <Select
        multiple
        value={selectedOptions}
        onChange={handleSelectChange}
      >
        {Object.keys(options).map((id: string) => (
          <MenuItem key={id} value={id}>
            {options[parseInt(id)]}
          </MenuItem>
        ))}
      </Select>
    </FormControl>
  );
};