
export enum IdentifierType{
  "transactionType" = "TRANSACTION" ,
  "category" = "CATEGORY",
  "partiesMerchant" = "PARTIES_OR_MERCHANT"
}

export enum DebitCredit {
  DEBIT = "DEBIT",
  CREDIT = "CREDIT",
  BOTH = "BOTH"
}

export interface MasterIdentifiersResponse{
  message: string,
  data: MasterIdentifier[]
}

export interface MasterIdentifier{
  id: number,
  identifierType: IdentifierType,
  identifierName: string,
  identificationValue: string,
  debitCredit: DebitCredit,
  deletable: boolean,
}

export interface MasterRuleRaw{
  message: string,
  data: MasterRuleData[]
}

export interface MasterIdentifierMinified{
  id: number,
  identifierName: string
}

export interface MasterRuleData{
  id: number,
  transactionTypeList: MasterIdentifierMinified[],
  categoryList: MasterIdentifierMinified[],
  partiesOrMerchantList: MasterIdentifierMinified[],
  identificationValue: string,
  debitOrCredit: DebitCredit,
  transactionFlag: string,
  ruleQuery: string | null,
  deletable: boolean,
  completed: boolean
}

export interface TxnFlagSaveRequest {
  transactionFlag: string
}