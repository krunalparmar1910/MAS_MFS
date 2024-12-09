import React, {useState, useEffect} from "react";
import EmiMasterForm from "components/EmiMaster/EmiMasterForm";
import EmiMasterTable from "components/EmiMaster/EmiMasterTable";
import {updateEmiEntry,getAllEmiEntries, saveEmiEntry} from "utils/api/request";

type EmiEntry = {
    interestId: number;
    typeOfLoan: string;
    interest: number;
    tenure: number;
  };

const EmiMaster: React.FC = () => {
  const [entries, setEntries] = useState<EmiEntry[]>([]);
  const [editEntry, setEditEntry] = useState<any>(null);

  const fetchEntries = async () => {
    const response = await getAllEmiEntries();
    setEntries(response.data);
  };

  const handleSave = async (data: { typeOfLoan: string; interest: number; tenure: number }) => {
    if (editEntry) {

      await updateEmiEntry(editEntry.id, data);
      setEditEntry(null);
    } else {
      await saveEmiEntry(data);
    }
    fetchEntries();
  };

  const handleEdit = (entry :EmiEntry) => {
    setEditEntry(entry);
  };

  const handleCancelEdit = () => {
    setEditEntry(null);
  };

  useEffect(() => {
    fetchEntries();
  }, []);

  return (
    <div>
      <h1>System EMI MASTER</h1>
      <EmiMasterForm
        initialData={editEntry}
        onSubmit={handleSave}
        onCancel={handleCancelEdit}
      />
      <EmiMasterTable entries={entries} onEdit={handleEdit} />
    </div>
  );
};

export default EmiMaster;