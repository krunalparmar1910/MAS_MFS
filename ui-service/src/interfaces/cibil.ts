export interface CibilReport {
  name: string,
  age: number,
  dateOfBirth: string // format "14042000"
  gender: string,
  score: number,
  controlNumber: number,
  identification: Identification[],
  telephoneNumbers: number[],
  emails: string[],
  addresses: Address[],
  accountSummary: AccountSummary,
  accountInquiriesHistoricalData: AccountInquiriesHistoricalData,
  accountBorrowingDetailsDTOList: AccountBorrowingDetails[],
  secondaryNameSegmentList: SecodaryName[],
  secondaryIdentification:Identification[],
  secondaryTelephoneNumbers:number[],
  secondaryAddresses:Address[],
  losDetailsDTO?: {
    name: string,
    dateOfBirth: string
    gender: string,
    losIdentificationDTOList: Identification[],
    telephoneNumbers: number[],
    emails: string[],
    losAddressInfoDTOList: Address[],
  }
}
export interface SecodaryName{
  consumerName1?:string,
  consumerName2?:string,
  consumerName3?:string,
  consumerName4?:string,
  consumerName5?:string,
  gender?:string,
  dateOfBirth?:string,
}

export interface Identification {
  idType: string
  idNumber: string
}

export interface Address {
  addressLine1?: string
  addressLine2?: string
  addressLine3?: string,
  addressLine4?: string,
  addressLine5?: string,
  stateCode?: string,
  pinCode?: string,
  category?: string
  dateReported?: string
}

export interface AccountSummary {
  totalLoansOpened: number;
  liveLoans: number;
  closedLoans: number;
  sanctionedAmountLiveLoans: number;
  posLiveLoans: number;
  overdueAmountLiveLoans: number;
  stdAllAccounts: number;
  smaAllAccounts: number;
  dbtAllAccounts: number;
  subAllAccounts: number;
  lssAllAccounts: number;
  notReportedAllAccounts: number;
  stdLiveAccounts: number;
  smaLiveAccounts: number;
  dbtLiveAccounts: number;
  subLiveAccounts: number;
  lssLiveAccounts: number;
  notReportedLiveAccounts: number;
  stdClosedAccounts: number;
  smaClosedAccounts: number;
  dbtClosedAccounts: number;
  subClosedAccounts: number;
  lssClosedAccounts: number;
  notReportedClosedAccounts: number;

  dpd0AllAccountsInd: number;
  dpd1to30AllAccountsInd: number;
  dpd31to90AllAccountsInd: number;
  dpdGreaterThan90AllAccountsInd: number;

  dpd0LiveAccountsInd: number;
  dpd1to30LiveAccountsInd: number;
  dpd31to90LiveAccountsInd: number;
  dpdGreaterThan90LiveAccountsInd: number;

  dpd0ClosedAccountsInd: number;
  dpd1to30ClosedAccountsInd: number;
  dpd31to90ClosedAccountsInd: number;
  dpdGreaterThan90ClosedAccountsInd: number;

  dpd0AllAccountsJoint: number;
  dpd1to30AllAccountsJoint: number;
  dpd31to90AllAccountsJoint: number;
  dpdGreaterThan90AllAccountsJoint: number;

  dpd0LiveAccountsJoint: number;
  dpd1to30LiveAccountsJoint: number;
  dpd31to90LiveAccountsJoint: number;
  dpdGreaterThan90LiveAccountsJoint: number;

  dpd0ClosedAccountsJoint: number;
  dpd1to30ClosedAccountsJoint: number;
  dpd31to90ClosedAccountsJoint: number;
  dpdGreaterThan90ClosedAccountsJoint: number;

  dpd0AllAccountsGuarantor: number;
  dpd1to30AllAccountsGuarantor: number;
  dpd31to90AllAccountsGuarantor: number;
  dpdGreaterThan90AllAccountsGuarantor: number;

  dpd0LiveAccountsGuarantor: number;
  dpd1to30LiveAccountsGuarantor: number;
  dpd31to90LiveAccountsGuarantor: number;
  dpdGreaterThan90LiveAccountsGuarantor: number;

  dpd0ClosedAccountsGuarantor: number;
  dpd1to30ClosedAccountsGuarantor: number;
  dpd31to90ClosedAccountsGuarantor: number;
  dpdGreaterThan90ClosedAccountsGuarantor: number;

  individual: Map<string, Map<string, any>>;
  joint: Map<string, Map<string, any>>;
  guarantor: Map<string, Map<string, any>>;

  settled:number;
  resturctured:number;
  subStd:number;
  dubDebt:number;
}

export interface AccountInquiriesHistoricalData {
  inquiriesLast30DaysFromCurrentDate: number,
  inquiriesLast60DaysFromCurrentDate: number,
  inquiriesLast120DaysFromCurrentDate: number,
  inquiriesLast365DaysFromCurrentDate: number,
  inquiriesLast730DaysFromCurrentDate: number,
  inquiriesLast30DaysFromCibilReportDate: number,
  inquiriesLast60DaysFromCibilReportDate: number,
  inquiriesLast120DaysFromCibilReportDate: number,
  inquiriesLast365DaysFromCibilReportDate: number,
  inquiriesLast730DaysFromCibilReportDate: number,
  accountsOpenedLast30DaysFromCurrentDate: number,
  accountsOpenedLast60DaysFromCurrentDate: number,
  accountsOpenedLast120DaysFromCurrentDate: number,
  accountsOpenedLast365DaysFromCurrentDate: number,
  accountsOpenedLast730DaysFromCurrentDate: number,
  accountsOpenedLast30DaysFromCibilReportDate: number,
  accountsOpenedLast60DaysFromCibilReportDate: number,
  accountsOpenedLast120DaysFromCibilReportDate: number,
  accountsOpenedLast365DaysFromCibilReportDate: number,
  accountsOpenedLast730DaysFromCibilReportDate: number
}

export interface AccountBorrowingDetails {
  typeOfLoan: string,
  sanctionedAmount: number,
  outstandingAmount: number,
  sanctionedDate: string, // format "14042000"
  status?: string,
  suitFiled?: number,
  bankNbfc: string,
  ownership: string,
  overdue: number,
  tenure?: number,
  last12MonthsDPD: string,
  overallDPD: string,
  delayInMonths: string,
  reportedDate: string,
  emi: number,
  interestRate?: number,
  comment?: string,
  duplicate: boolean,
  addInTotal: boolean
  accountId: number
  emiSystem: number,

}

export interface NewLoanEntryDetails{
    id: string,
    sanctionedDate: string,
    typeOfLoan: string,
    sanctionedAmount: number,
    outstanding: number,
    bankNbfc: string,
    emiAmount: number,
    emiSystem: number,
    tenure: number,
    lastPaymentDate: string,
    comment: string,
    duplicate: boolean,
    addInTotal: boolean,
    requestId: string
}
export interface BorrowingDetailsRow extends AccountBorrowingDetails {
  id: number,
  isNew: boolean,
  isEdited: boolean,
}

export interface BorrowingDetailAmountsDTO{
  sanctionedAmount: number,
  outstandingAmount: number,
  emi: number,
  emiSystem: number | 0,
  duplicate: boolean,
  addInTotal: boolean,
}
export interface AccountSummarySegmentDTO {
  reportingMemberShortName?: string;
  accountType?: string;
  ownershipIndicator?: string;
  dateOpenedOrDisbursed?: string; // ISO 8601 format for date
  dateReportedAndCertified?: string;
  highCreditOrSanctionedAmount?: number;
  currentBalance?: number;
  paymentHistoryEndDate?: string;
  creditLimit?: number;
  cashLimit?: number;
  writtenOffAmountTotal?: number;
  paymentFrequency?: string;
  dateOfEntryForCibilRemarksCode?: string;
  dateOfEntryForErrorDisputeRemarksCode?: string;
  errorDisputeRemarksCode1?: string;
  errorDisputeRemarksCode2?: string;
  fid?: string;
  sNo?: string;
  suppressFlag?: string;
  dateOfSuppression?: string;
  paymentHistory1?: string;
  liveAccount?: boolean;
  suitFiledOrWilfulDefault?: string;
  typeOfCollateral?: string;
  repaymentTenure?: number;
  emiAmount?: number;
  actualPaymentAmount?: number;
  dateOfEntryForErrorCode?: string;
  accountNumber?: string;
  dateOfLastPayment?: string;
  dateClosed?: string;
  amountOverdue?: number;
  paymentHistory2?: string;
  paymentHistoryStartDate?: string;
  creditFacility?: string;
  valueOfCollateral?: string;
  writtenOffAmountPrincipal?: string;
  settlementAmount?: string;
  errorCode?: string;
  cibilRemarksCode?: string;
  rateOfInterest?: number;
}
