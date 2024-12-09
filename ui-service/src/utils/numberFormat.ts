export function getRoundedOffNumberWithCommas(input: number, decimalDigits = 2): string {
  // Round off the number to the specified number of decimal digits
  const roundedNumber = Math.round(input * Math.pow(10, decimalDigits)) / Math.pow(10, decimalDigits);

  // Convert the rounded number to a string with commas according to the Indian numbering system
  const formattedNumber = roundedNumber.toLocaleString("en-IN", {
    maximumFractionDigits: decimalDigits,
    minimumFractionDigits: decimalDigits,
  });

  return formattedNumber;
}

export function getPercentageFormat(percent: number): string {
  return percent.toFixed(2) + "%";
}
