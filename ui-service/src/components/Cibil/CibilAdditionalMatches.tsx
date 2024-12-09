import React, {useEffect, useState} from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
} from "@mui/material";
import {CibilReport} from "interfaces/cibil";
import {useTranslation} from "react-i18next";

interface AdditionalDetails {
  Particular: string;
  details: string[];
}

const saveToAdditionalDetails = (fieldName: string, value: string) => {
  console.log(`Saving ${fieldName}: ${value}`);
};

const parseCibilData = (allData: CibilReport): AdditionalDetails[] => {

  const name = allData.secondaryNameSegmentList.map((name) => {
    return `${name.consumerName1 || ""},${name.consumerName2 || ""},
    ${name.consumerName3 || ""},${name.consumerName3 || ""},
    ${name.consumerName4 || ""},${name.consumerName5 || ""}`;
  });

  const dob = allData.secondaryNameSegmentList.map((dob) => {
    if (dob.dateOfBirth) {
      const date = new Date(dob.dateOfBirth);
      const day = String(date.getDate()).padStart(2, "0");
      const month = String(date.getMonth() + 1).padStart(2, "0");
      const year = date.getFullYear();
      return `${day}-${month}-${year}`;
    }
    return "";
  });

  const pan =
    allData.secondaryIdentification
      ?.filter((id) => id.idType === "Income Tax ID Number (PAN)")
      .map((id) => id.idNumber) || [];

  const officePhone = allData.secondaryTelephoneNumbers.map((phoneNumber) => {
    return `${phoneNumber || ""}`;
  });

  const addresses = allData.secondaryAddresses.map((secondaryAddresses) => {
    return `${secondaryAddresses.addressLine1 || ""}, ${
      secondaryAddresses.addressLine2 || ""
    }, ${secondaryAddresses.addressLine3 || ""}, ${
      secondaryAddresses.addressLine4 || ""
    }, ${secondaryAddresses.addressLine5 || ""}, ${
      secondaryAddresses.stateCode || ""
    }, ${secondaryAddresses.pinCode || ""}`;
  });

  const maxColumns = Math.max(
    name.length,
    dob.length,
    pan.length,
    officePhone.length,
    addresses.length,
  );
  const padArray = (arr: string[], length: number) => [
    ...arr,
    ...Array(length - arr.length).fill(""),
  ];

  const formattedPersonalDetails: AdditionalDetails[] = [
    {
      Particular: "Name",
      details: padArray(name, maxColumns),
    },
    {
      Particular: "DOB",
      details: padArray(dob, maxColumns),
    },
    {
      Particular: "Income Tax ID Number (PAN)",
      details: padArray(pan, maxColumns),
    },
    {
      Particular: "Office Phone",
      details: padArray(officePhone, maxColumns),
    },
    {
      Particular: "Address",
      details: padArray(addresses, maxColumns),
    },
  ];

  formattedPersonalDetails.forEach((detail) => {
    saveToAdditionalDetails(detail.Particular, detail.details.join(", "));
  });

  return formattedPersonalDetails;
};

function CibilAdditionalMatches(props: { allData?: CibilReport }) {
  const {t} = useTranslation();
  const {allData} = props;
  const [personalDetails, setPersonalDetails] = useState<AdditionalDetails[]>(
    [],
  );
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (allData) {
      try {
        const details = parseCibilData(allData);
        setPersonalDetails(details);
        setLoading(false);
      } catch (e) {
        setError("Error parsing CIBIL data");
        setLoading(false);
      }
    }
  }, [allData]);

  const maxColumns = personalDetails.reduce(
    (max, row) => Math.max(max, row.details.length),
    0,
  );

  if (!loading && personalDetails.length === 0) {
    return (
      <div style={{display: "flex", justifyContent: "center", alignItems: "center"}}>
        <h4>{t("No Any Additional Details are Available.")}</h4>
      </div>
    );
  }

  return (
    <Table className="mv-10" aria-label="customized table">
      <TableHead>
        <TableRow>
          <TableCell align="center">{t("Particulars")}</TableCell>
          {[...Array(maxColumns)].map((_, index) => (
            <TableCell key={index} align="center">
              {t(`Additional Details ${index + 1}`)}
            </TableCell>
          ))}
        </TableRow>
      </TableHead>
      <TableBody>
        {personalDetails.map((row, rowIndex) => (
          <TableRow key={rowIndex}>
            <TableCell>{row.Particular}</TableCell>
            {row.details.map((detail, colIndex) => (
              <TableCell key={colIndex}>{detail}</TableCell>
            ))}
          </TableRow>
        ))}
      </TableBody>
    </Table>
  );
}

export default CibilAdditionalMatches;
