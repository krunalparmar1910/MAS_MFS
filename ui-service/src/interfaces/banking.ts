import {DebitCredit} from "interfaces/bankingConfiguration";

export interface MonthlyDetailsDTO {
  masFinancialId: string
  bankDetailList: BankDetailDTO[]
  customEditableFieldDTO:CustomEditableFieldDTO
}

export interface BankDetailDTO {
  accountSummaryUuid: string,
  customerTransactionId: string,
  perfiosTransactionId: string,
  bankName: string,
  customerDetails: CustomerDTO,
  accountNumber: string,
  accountType: string,
  customCreditLoanReceipts: number,
  customDebitLoanReceipts: number,
  monthlyReportList: MonthlyReportDTO[],
  yearMonthFrom: string,
  yearMonthTo: string,
  averageBankingBalance: number,
  medianBankingBalance: number,
}

export interface CustomerDTO {
  uuid: string,
  name: string,
  address?: string,
  email?: string,
  pan?: string,
  mobileNumber?: string,
  landline?: string
}

export interface MonthlyReportDTO {
  monthwiseDetailUuid: string,
  monthYear: string,
  credit: number,
  debit: number,
  creditInterBank: number,
  creditInterFirm: number,
  creditNonBusinessTransaction: number,
  debitInterBank: number,
  debitInterFirm: number,
  debitNonBusinessTransaction: number,
  inwardReturn: number,
  outwardReturn: number,
  quarterlyBankingTurnover: number,
  penaltyCharges: number,
  customCcUtilizationPercentage: number | null,
  totalLoanDisbursements: number,
  totalEmiTracing: number,
}

export interface AccountList{
  accountNumberList: string[];
  customerTransactionId: string;
}

export interface TransactionReqDTO {
  debitOrCredit: DebitCredit,
  accountList: AccountList[],
  fromDate?: string,
  toDate?: string,
  fromChqNo?: number,
  toChqNo?: number,
  fromDebit?: number,
  toDebit?: number,
  fromCredit?: number,
  toCredit?: number,
  fromBalance?: number,
  toBalance?: number,
  searchText?: string,
  description?: string,
  comment?: string,
  category?: string,
}

export interface TxnFlagResponse {
  message: string,
  data: {
    id: number,
    transactionFlag: string,
  }
}

export interface TransactionDTO {
  transactionUuid: string,
  date: string,
  chequeNo: number,
  description: string,
  amount: number,
  debit: number,
  credit: number,
  category: string,
  balance: number,
  transactionFlag: string,
  identifiedCategory: string,
  transactionType: string,
  parties: string,
  institutionName: string,
  accountNumber: string,
  comment: string,
}

export interface PageResponseDTO<T> {
  totalElements: number,
  totalPages: number,
  elements: T[],
}

export interface UpdateTransactionCustomFieldsRequest {
  transactionUuid: string,
  comment: string,
}

export interface TransactionTotalDTO {
  totalCredit: number,
  totalDebit: number,
  startTransactionDate: string,
  endTransactionDate: string,
}

export interface CustomFieldMonthlyReportReqDTO {
  monthwiseDetailUuid: string,
  customCcUtilizationPercentage: number,
}

export interface CustomFieldReqDTO {
  accountSummaryUuid: string,
  customCreditLoanReceipts: number,
  customDebitLoanReceipts: number,
  monthlyReportFieldList: CustomFieldMonthlyReportReqDTO[],
  customEditableFieldDTO:CustomEditableFieldDTO
}

export interface CustomEditableFieldDTO{
  customCreditLoanReceipts: number,
  customDebitLoanReceipts: number,
  totalCreditInterBank: number,
  totalDebitInterBank: number,
  totalCreditInterFirm: number,
  totalDebitInterFirm: number,
  totalCreditNonBusinessTransaction: number,
  totalDebitNonBusinessTransaction: number,
  masFinId:string
}