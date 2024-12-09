import {Button} from "@mui/material";
import React, {useState, useEffect} from "react";
import "styles/EmiMaster.scss";

interface EmiMasterFormProps {
  initialData?: { typeOfLoan: string; interest: number; tenure: number };
  onSubmit: (data: { typeOfLoan: string; interest: number; tenure: number }) => Promise<void>;
  onCancel?: () => void;
}

const EmiMasterForm: React.FC<EmiMasterFormProps> = ({initialData, onSubmit, onCancel}) => {
  const [typeOfLoan, setType] = useState("");
  const [interest, setInterest] = useState<number | "">("");
  const [tenure, setTenure] = useState<number | "">("");

  useEffect(() => {
    if (initialData) {
      setType(initialData.typeOfLoan);
      setInterest(initialData.interest);
      setTenure(initialData.tenure);
    }
  }, [initialData]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!typeOfLoan || interest === "" || tenure === "") {
      alert("Please fill out all fields.");
      return;
    }
    await onSubmit({typeOfLoan, interest: Number(interest), tenure: Number(tenure)});
    setType("");
    setInterest("");
    setTenure("");
  };

  const handleReset = () => {
    setType("");
    setInterest("");
    setTenure("");
    if (onCancel) {
      onCancel();
    }
  };

  return (
    <form onSubmit={handleSubmit} className="form-container">
      <div>
        <label>Type of Loan</label>
        <input type="text" value={typeOfLoan} onChange={(e) => setType(e.target.value)} required />
      </div>
      <div>
        <label>Interest (%)</label>
        <input
          type="number"
          value={interest}
          onChange={(e) => setInterest(Number(e.target.value))}
          required
        />
      </div>
      <div>
        <label>Tenure (Months)</label>
        <input
          type="number"
          value={tenure}
          onChange={(e) => setTenure(Number(e.target.value))}
          required
        />
      </div>
      <div>
        <Button
          onClick={handleSubmit}
          variant="contained"
          size="small"
          style={{marginRight: "10px"}}
        >
          Save
        </Button>
        <Button
          onClick={handleReset}
          variant="contained"
          size="small"
          style={{marginRight: "10px"}}
        >
          Reset
        </Button>
      </div>
    </form>
  );
};

export default EmiMasterForm;
