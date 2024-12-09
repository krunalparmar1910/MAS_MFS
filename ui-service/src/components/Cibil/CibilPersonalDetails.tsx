import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
} from "@mui/material";
import {Address, CibilReport, Identification} from "interfaces/cibil";
import React, {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";

const getPersonalDetails = (allData?: CibilReport): PersonalDetails => {
  let index = 0;
  const identificationMap =
    allData?.losDetailsDTO?.losIdentificationDTOList?.reduce(
      (acc: Map<string, Identification>, curr: Identification) => {
        return acc.set(curr.idType, curr);
      },
      new Map(),
    );
  return {
    "Consumer Information": [
      {
        id: index++,
        title: "Name",
        value: allData?.name,
        losValue: allData?.losDetailsDTO?.name,
      },
      {
        id: index++,
        title: "DOB",
        value: allData?.dateOfBirth,
        losValue: allData?.losDetailsDTO?.dateOfBirth,
      },
      {
        id: index++,
        title: "Gender",
        value: allData?.gender,
        losValue: allData?.losDetailsDTO?.gender,
      },
    ],
    Identification: allData?.identification
      ? allData.identification.map((id) => {
        return {
          id: index++,
          title: id.idType,
          value: id.idNumber,
          losValue: identificationMap?.get(id.idType)?.idNumber,
        };
      })
      : [{id: index++, title: "", value: "", losValue: ""}],
    Telephone: [
      {
        id: index++,
        title: "Telephone number",
        value: allData?.telephoneNumbers?.join(", ") || "",
        losValue: allData?.losDetailsDTO?.telephoneNumbers?.join(", ") || "",
      },
    ],
    Email: [
      {
        id: index++,
        title: "Email",
        value: allData?.emails?.join(", ") || "",
        losValue: allData?.losDetailsDTO?.emails?.join(", ") || "",
      },
    ],
  };
};

const normalizeAddressPart = (address: string): string =>
  address
    .replace(/[\s,;]+/g, "") // Remove spaces, commas, semicolons
    .replace(/[^a-zA-Z0-9/-]/g, "") // Remove any character that isn't alphanumeric, hyphen, or slash
    .toLowerCase();

const getMatchingWordsCount = (address1: Address, address2: Address): number => {
  const addressKeys: (keyof Address)[] = [
    "addressLine1",
    "addressLine2",
    "addressLine3",
    "addressLine4",
    "addressLine5",
    "stateCode",
    "pinCode",
  ];

  let matchCount = 0;

  addressKeys.forEach((key) => {
    const part1Words = (address1[key] || "").split(/\s+/).map(normalizeAddressPart);
    const part2Words = (address2[key] || "").split(/\s+/).map(normalizeAddressPart);

    const matchingWords = part1Words.filter((word) => part2Words.includes(word));
    matchCount += matchingWords.length;
  });

  return matchCount;
};

const isAddressMatched = (address1: Address, address2: Address): string => {
  const addressKeys: (keyof Address)[] = [
    "addressLine1",
    "addressLine2",
    "addressLine3",
    "addressLine4",
    "addressLine5",
    "stateCode",
    "pinCode",
  ];

  return addressKeys
    .map((key) => {
      const part1 = address1[key] || "";
      const part2 = address2[key] || "";

      const part1Words = part1.split(/\s+/);
      const part2Words = part2.split(/\s+/).map(normalizeAddressPart);

      return part1Words
        .map((word) =>
          part2Words.includes(normalizeAddressPart(word)) ?
            `<span style="text-decoration: underline; font-weight: bold;">${word}</span>` : word,
        )
        .join(" ");
    })
    .join(" ");
};

const compareMultipleAddresses = (
  address1List: Address[],
  address2List: Address[],
): string => {
  let maxMatchCount = 0;
  let highlightedAddress = "";

  for (const address1 of address1List) {
    for (const address2 of address2List) {
      const matchCount = getMatchingWordsCount(address1, address2);

      if (matchCount > maxMatchCount) {
        maxMatchCount = matchCount;
        highlightedAddress = isAddressMatched(address1, address2);

        if (matchCount === Object.keys(address1).length * 5) {
          return highlightedAddress;
        }
      }
    }
  }
  return highlightedAddress;
};

const getAddresses = (allData?: CibilReport): { title: string; value: any }[] => {
  if (!allData) {
    return [];
  }

  const addresses = allData?.addresses || [];
  const losAddressList: Address[] = allData?.losDetailsDTO?.losAddressInfoDTOList || [];

  return addresses.map((address: Address, index: number) => {
    const title = index === 0 ? "Address" : `Address${index + 1}`;
    const losAddress = losAddressList[index] || [] as Address;

    const formattedAddress = [
      address?.addressLine1,
      address?.addressLine2,
      address?.addressLine3,
      address?.addressLine4,
      address?.addressLine5,
      address?.stateCode,
      address?.pinCode,
    ]
      .filter(Boolean)
      .join("");

    const formattedLosAddress = [
      losAddress?.addressLine1,
      losAddress?.addressLine2,
      losAddress?.addressLine3,
      losAddress?.addressLine4,
      losAddress?.addressLine5,
      losAddress?.stateCode,
      losAddress?.pinCode,
    ]
      .filter(Boolean)
      .join("");

    const highlightedAddresses = compareMultipleAddresses([address], losAddressList);

    return {
      title,
      value: {
        address: highlightedAddresses.length > 0 ? highlightedAddresses : formattedAddress,
        category: address.category || "",
        dateReported: address.dateReported || "",
        losAddress: formattedLosAddress || "",
        losCategory: losAddress?.category || "",
        losDateReported: losAddress?.dateReported || "",
      },
    };
  });
};

interface PersonalDetails {
  [key: string]: {
    id: number;
    title: string;
    value: string | undefined;
    losValue: string | undefined;
  }[];
}

interface AddressForDisplay {
  title: string;
  value: {
    address: string;
    category: string | undefined;
    dateReported: string | undefined;
    losAddress: string;
    losCategory: string | undefined;
    losDateReported: string | undefined;
  };
}

function CibilPersonalDetail(props: { allData?: CibilReport }) {
  const {t} = useTranslation();

  const {allData} = props;

  const [personalDetails, setPersonalDetails] = useState<PersonalDetails>({});
  const [addresses, setAddresses] = useState<AddressForDisplay[]>();

  const compareValues = (
    value1: string | undefined,
    value2: string | undefined,
  ) => {
    if (!value1 || !value2) {
      return "mismatch";
    }

    const values1 = value1.split(",").map((v) => v.trim());
    const values2 = value2.split(",").map((v) => v.trim());

    const isMatch = values1.some((val1) => values2.includes(val1));
    return isMatch ? "match" : "mismatch";
  };

  useEffect(() => {
    setPersonalDetails(getPersonalDetails(allData));
    if (allData?.addresses) {
      const addresses = getAddresses(allData);
      setAddresses(
        addresses.map((address) => ({
          title: address.title,
          value: address.value,
        })),
      );
    }
  }, [allData]);

  return (
    <>
      <Table className="mv-10" aria-label="customized table">
        <TableHead>
          <TableRow>
            <TableCell align="center"></TableCell>
            <TableCell align="center">
              {t("cibil.personalDetails.columns.particulars")}
            </TableCell>
            <TableCell align="center">
              {t("cibil.personalDetails.columns.detailsAsPerBureau")}
            </TableCell>
            <TableCell align="center">
              {t("cibil.personalDetails.columns.detailsInputtedLOS")}
            </TableCell>
            <TableCell align="center">{t("match")}</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {Object.entries(personalDetails).map(([key, value], index) => (
            <React.Fragment key={index}>
              <TableRow>
                <TableCell rowSpan={value.length + 1}>{key}</TableCell>
              </TableRow>
              {value.map((detail, index) => {
                const match = compareValues(detail.value, detail.losValue);
                return (
                  <TableRow key={index}>
                    <TableCell>{detail.title}</TableCell>
                    <TableCell>{detail.value}</TableCell>
                    <TableCell>{detail.losValue}</TableCell>
                    <TableCell
                      style={{color: match === "match" ? "green" : "red"}}
                    >
                      {match === "match" ? "✓" : "✘"}
                    </TableCell>
                  </TableRow>
                );
              })}
            </React.Fragment>
          ))}
          <>
            {addresses?.map((address, index) => {
              return (
                <React.Fragment key={index}>
                  <TableRow>
                    <TableCell rowSpan={4}>{address.title}</TableCell>
                  </TableRow>
                  <TableRow>
                    <TableCell>
                      {" "}
                      {t("cibil.personalDetails.columns.address.address")}{" "}
                    </TableCell>
                    <TableCell
                      dangerouslySetInnerHTML={{
                        __html: address.value.address,
                      }}
                    />
                    <TableCell>{address.value?.losAddress || ""}</TableCell>
                  </TableRow>
                  <TableRow>
                    <TableCell>
                      {t("cibil.personalDetails.columns.address.category")}
                    </TableCell>
                    <TableCell>{address.value?.category || ""}</TableCell>
                    <TableCell>{address.value?.losCategory || ""}</TableCell>
                  </TableRow>
                  <TableRow>
                    <TableCell>
                      {t("cibil.personalDetails.columns.address.dateReported")}
                    </TableCell>
                    <TableCell>{address.value?.dateReported || ""}</TableCell>
                    <TableCell>
                      {address.value?.losDateReported || ""}
                    </TableCell>
                  </TableRow>
                </React.Fragment>
              );
            })}
          </>
        </TableBody>
      </Table>
    </>
  );
}

export default CibilPersonalDetail;
