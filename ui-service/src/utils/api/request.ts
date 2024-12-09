import {
  AccessTokenResponse,
  AuthorizeUserRequestDTO,
  RefreshTokenRequestDTO,
} from "interfaces/auth";
import {
  CustomFieldReqDTO,
  MonthlyDetailsDTO,
  PageResponseDTO,
  TransactionTotalDTO,
  TxnFlagResponse,
  TransactionReqDTO,
  UpdateTransactionCustomFieldsRequest,
  TransactionDTO,
} from "interfaces/banking";
import {TxnFlagSaveRequest} from "interfaces/bankingConfiguration";
import {AccountBorrowingDetails, NewLoanEntryDetails} from "interfaces/cibil";
import {
  GSTReport,
  GetGST3BReportRequestHistoryResponseDTO,
  ProfileDetailResponse,
} from "interfaces/gst";
import {axios} from "utils/api/axios";
import {
  ACCESS_TOKEN,
  ACCESS_TOKEN_EXPIRY_TIME,
  CLIENT_ORDER_IDS_PARAM,
  ENTITY_ID_PARAM,
  REFRESH_TOKEN,
} from "utils/constants/constants";

export interface ErrorResponse {
  message: string;
  errorCode: number;
}

interface CommonResponse<T> {
  message: string;
  data: T;
}

const CLIENT_ID = "mas-auth";

function updateAccessTokenInLocalStorage(response: AccessTokenResponse): void {
  localStorage.setItem(ACCESS_TOKEN, response.access_token);
  localStorage.setItem(REFRESH_TOKEN, response.refresh_token);
  localStorage.setItem(ACCESS_TOKEN_EXPIRY_TIME, String(response.expires_in));
}

export async function getToken(username: string, password: string): Promise<void> {
  const response = await axios.post("/auth/authorize", {
    username: username,
    password: password,
    clientId: CLIENT_ID,
  } as AuthorizeUserRequestDTO);
  updateAccessTokenInLocalStorage(response.data as AccessTokenResponse);

  // redirect
  const url = new URL(window.location.href);
  const navigate = url.searchParams.get("redirect");
  if (navigate) {
    window.location.href = navigate;
  } else {
    window.location.href = "/ui/home";
  }
}

export async function refreshToken(): Promise<void> {
  const response = await axios.post("/auth/refresh-token", {
    clientId: CLIENT_ID,
    refreshToken: localStorage.getItem(REFRESH_TOKEN) || "",
  } as RefreshTokenRequestDTO);
  updateAccessTokenInLocalStorage(response.data as AccessTokenResponse);
}

export async function getGstSalesData(entityId?: string, clientOrderIds?: string): Promise<GSTReport> {
  let request = "/report/gst-3b-report";
  if (entityId && clientOrderIds) {
    request += `?${ENTITY_ID_PARAM}=${entityId}&${CLIENT_ORDER_IDS_PARAM}=${clientOrderIds}`;
  } else if (entityId) {
    request += `?${ENTITY_ID_PARAM}=${entityId}`;
  } else if (clientOrderIds) {
    request += `?${CLIENT_ORDER_IDS_PARAM}=${clientOrderIds}`;
  }
  const response = await axios.get(request);
  return response.data;
}

export async function updateGstDetails(updatedReport: {} | undefined): Promise<GSTReport> {
  const response = await axios.put("/report/update-manual-entries-gst-3b-report", updatedReport);
  return response.data;
}

export async function parseExcelReport(formData: FormData): Promise<ProfileDetailResponse> {
  const response = await axios.post("/report/parse-excel-report", formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
  return response.data;
}

export async function getLoginInfo(): Promise<void> {
  await axios.get("/login/login-info");
}

export function getGST3BReportRequestHistory(): Promise<GetGST3BReportRequestHistoryResponseDTO> {
  return axios.get("/login/get-gst-3b-report-request-history")
    .then((response) => response.data)
    .catch((error) => {
      throw new Error(error);
    });
}

export async function getCibilData(id: string) {
  const request = `/cibil/consumer/profile/${id}`;
  const response = await axios.post(request, {});
  return response.data;
}

export async function updateCibilBorrowingDetails(id: string, row: AccountBorrowingDetails) {
  const request = `/cibil/consumer/cibil/account/${id}`;
  const response = await axios.put(request, row);
  return response.data;
}

export async function getNonCibilData(id: string) {
  const request = `/cibil/consumer/non-cibil/account/${id}`;
  const response = await axios.get(request);
  return response.data;
}

export async function saveNonCibilData(requestId: string, requestBody: NewLoanEntryDetails) {
  const request = `/cibil/consumer/non-cibil/account/${requestId}`;
  const response = await axios.post(request, requestBody);
  return response.data;
}

export async function addNewLoanEntry(requestId: string, row: NewLoanEntryDetails) {
  const request = `/cibil/consumer/non-cibil/account/${requestId}`;
  const response = await axios.post(request, row);
  return response.data;
}

export async function updateNewLoanEntry(requestId: string, row: NewLoanEntryDetails) {
  const request = `/cibil/consumer/non-cibil/account/${requestId}`;
  const response = await axios.put(request, row);
  return response.data;
}

export async function deleteNewLoanEntry(id: string) {
  const request = `/cibil/consumer/non-cibil/account/${id}`;
  const response = await axios.delete(request);
  return response.data;
}

export async function getBankingData(
  masFinId?: string,uniqueFirmId?: string, customerTxnIds?: string[]): Promise<CommonResponse<MonthlyDetailsDTO>> {
  let request = `/perfios/${masFinId}/monthly-details`;
  if (uniqueFirmId) {
    request += `?uniqueFirmId=${encodeURIComponent(uniqueFirmId)}`;
  }
  const response = await axios.post(request, {
    customerTxnIdList: customerTxnIds,
  });
  return response.data;
}

export async function updateBankingCustomFields(updatedReport: CustomFieldReqDTO | undefined): Promise<void> {
  await axios.post("/perfios/update-custom-fields", updatedReport);
}

export async function getTxnData(
  masFinId: string,
  txnRequestBody: TransactionReqDTO,
  pageSize: number,
  pageNo: number,
  sort: string,
): Promise<CommonResponse<PageResponseDTO<TransactionDTO>>> {
  const request = `/perfios/${masFinId}/transactions?page=${pageNo}&size=${pageSize}${sort}`;
  const response = await axios.post(request, txnRequestBody);
  return response.data;
}

export async function getTxnTotal(
  masFinId: string, txnRequestBody: TransactionReqDTO): Promise<CommonResponse<TransactionTotalDTO>> {
  const request = `/perfios/${masFinId}/transactions-total`;
  const response = await axios.post(request, txnRequestBody);
  return response.data;
}

export async function saveTransactionFlag(requestBody: TxnFlagSaveRequest): Promise<TxnFlagResponse> {
  const request = "/perfios/master-rule-transaction-flag";
  const response = await axios.post(request, requestBody);
  return response.data;
}

export async function updateTransactionCustomFields(req: UpdateTransactionCustomFieldsRequest[]): Promise<void> {
  const request = "/perfios/update-transaction-custom-fields";
  await axios.put(request, req);
}

export async function getBankingMasterIdentifiers() {
  const request = "/perfios/master-identifiers";
  const response = await axios.get(request);
  return response.data;
}

export async function updateBankingMasterIdentifiers(requestBody: any) {
  const request = "/perfios/master-identifiers";
  const response = await axios.put(request, requestBody);
  return response.data;
}

export async function deleteBankingMasterIdentifiers(identifierType: string, id:string) {
  const request = `/perfios/${identifierType}/${id}`;
  const response = await axios.delete(request);
  return response.data;
}

export async function getBankingMasterRuleData() {
  const request = "/perfios/master-rule";
  const response = await axios.get(request);
  return response.data;
}

export async function updateBankingMasterRule(requestBody: any) {
  const request = "/perfios/master-rule";
  const response = await axios.put(request, requestBody);
  return response.data;
}

export async function deleteBankingMasterRule(id:number) {
  const request = "/perfios/master-rule/${id}";
  const response = await axios.delete(request);
  return response.data;
}

export async function getCibilDataView(
  id: string,
  searchCriteria: string,
  searchString: string,
  searchFor: string,
): Promise<any> {
  const requestUrl = `/cibil/consumer/profile/view/${encodeURIComponent(id)}?searchCriteria=${encodeURIComponent(
    searchCriteria,
  )}&searchString=${encodeURIComponent(searchString)}&searchFor=${encodeURIComponent(searchFor)}`;
  const response = await axios.post(requestUrl, {});
  return response.data;
}

export const getAllEmiEntries = async () => {
  return axios.get("/emiMaster/getall");
};

export const saveEmiEntry = async (data: { typeOfLoan: string; interest: number; tenure: number }) => {
  return axios.post("/emiMaster/save", data);
};

export const updateEmiEntry = async (id: number, data: { typeOfLoan: string; interest: number; tenure: number }) => {
  return axios.put("/emiMaster/${id}", data);
};

export const deleteEmiEntry = async (id: number) => {
  return axios.delete("/emiMaster/delete/" + id);
};