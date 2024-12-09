import {AxiosError} from "axios";
import ConfigureTxnFlag1 from "components/BankingConfiguration/ConfigureTxnFlag";
import ConfigureTxnType from "components/BankingConfiguration/ConfigureTxnType";
import {IdentifierType, MasterIdentifier, MasterRuleData} from "interfaces/bankingConfiguration";
import React, {useEffect, useState} from "react";
import {ErrorResponse, getBankingMasterIdentifiers, getBankingMasterRuleData} from "utils/api/request";

function BankingConfiguration() {
  const [allData, setAllData] = useState<MasterIdentifier[]>([]);
  const [rowsMap, setRowsMap] = useState<Map<IdentifierType, MasterIdentifier[]>>();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<AxiosError<ErrorResponse> | null>(null);

  const [masterRuleData, setMasterRuleData] = useState<MasterRuleData[]>([]);
  const [transactionTypeOptions, setTransactionTypeOptions] = useState<{ [key: number]: string }>();
  const [categoryOptions, setcategoryOptions] = useState<{ [key: number]: string }>();
  const [partiesOptions, setPartiesOptions] = useState<{ [key: number]: string }>();

  const fetchMasterIdentifierData = () => {
    setLoading(true);
    getBankingMasterIdentifiers()
      .then((data) => {
        setAllData(data.data);
      })
      .catch((error)=>setError(error))
      .finally(()=>setLoading(false));
  };

  const fetchMasterRuleData = () => {
    setLoading(true);
    getBankingMasterRuleData()
      .then((data) => {
        setMasterRuleData(data.data);
      })
      .catch((error)=>setError(error))
      .finally(()=>setLoading(false));
  };

  useEffect(() => {
    fetchMasterIdentifierData();
    fetchMasterRuleData();
  }, []);

  useEffect(() => {
    const tempRowsMap = new Map<IdentifierType, MasterIdentifier[]>();
    allData.forEach((data: MasterIdentifier) => {
      const key = data.identifierType;
      if (!tempRowsMap.has(key)) {
        tempRowsMap.set(key, [{...data}]);
      } else {
        tempRowsMap.get(key)?.push({...data});
      }
    });

    const tempTransactionTypeOptions: { [key: number]: string } =
      tempRowsMap?.get(IdentifierType.transactionType)?.reduce((acc: any, item: any) => {
        acc[item.id] = item.identifierName;
        return acc;
      }, {});

    const tempCategoryOptions: { [key: number]: string } =
      tempRowsMap?.get(IdentifierType.category)?.reduce((acc: any, item: any) => {
        acc[item.id] = item.identifierName;
        return acc;
      }, {});

    const tempPartiesOptions: { [key: number]: string } =
      tempRowsMap?.get(IdentifierType.partiesMerchant)?.reduce((acc: any, item: any) => {
        acc[item.id] = item.identifierName;
        return acc;
      }, {});

    setTransactionTypeOptions(tempTransactionTypeOptions || {});
    setcategoryOptions(tempCategoryOptions || {});
    setPartiesOptions(tempPartiesOptions || {});
    setRowsMap(tempRowsMap);
  }, [allData]);

  const updateRowsMap = async (txnType: IdentifierType, newRow: MasterIdentifier) => {
    let found = false;
    let temp = allData;
    temp = allData.map(data => {
      if (data.id === newRow.id) {
        found = true;
        return newRow;
      }
      return data;
    });

    if (!found) {
      temp.push(newRow);
    }
    setAllData(temp);
  };

  const updateMasterRule = (newRow: any) => {
    let found = false;
    let temp = masterRuleData;
    temp = masterRuleData.map(data => {
      if (data.id === newRow.id) {
        found = true;
        return newRow;
      }
      return data;
    });

    if (!found) {
      temp.push(newRow);
    }
    setMasterRuleData(temp);
  };

  const onMasterIdentifierDelete = () => {
    fetchMasterIdentifierData();
  };

  const onMasterRuleDelte = () => {
    fetchMasterRuleData();
  };

  return (
    <>
      {Object.keys(IdentifierType).map((identifier: string, index: number) => (
        <ConfigureTxnType
          key={index}
          identifierData={rowsMap?.get((IdentifierType as any)[identifier])!}
          updateRowsMap={updateRowsMap}
          identifierType={(IdentifierType as any)[identifier]}
          onRowDelete={onMasterIdentifierDelete}
          loading={loading}
        />))}
      <ConfigureTxnFlag1
        masterData={masterRuleData}
        updateMasterRule={updateMasterRule}
        onRowDelete={onMasterRuleDelte}
        transactionTypeOptions={transactionTypeOptions!}
        categoryOptions={categoryOptions!}
        partiesOptions={partiesOptions!}
        loading={loading}
      />
      <br />
      <br />
      <br />
    </>
  );
}

export default BankingConfiguration;