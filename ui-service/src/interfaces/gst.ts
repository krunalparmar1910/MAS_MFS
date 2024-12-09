export interface GSTReport {
    gstReportProfileDetails: GstReportProfileDetail[],
    gst3BSalesReports: {[key: string]: Gst3BSalesReport},
    gst3BConsolidatedSalesReport: Gst3BSalesReport,
}

//for profile details
export interface GstReportProfileDetail {
    legalName: string
    tradeName: string
    panNumber: string
    gstNumber: string
    placeOfBusiness: string
    state: string
    status: string
}

//for sales details
export interface Gst3BSalesDetails {
    month: string,
    sales: number,
    purchase: number,
    delayInFiling: boolean | null,
    delayedDays: number | null,
}

export interface Gst3BSales {
    gst3BSalesDetails: Gst3BSalesDetails[],
    totalSales: number,
    totalPurchase: number,
    totalDelayedDays: number,
    averageDelayInDays: string | null,
}

export interface Gst3BCustomerDetails {
    customerName: string;
    adjustedRevenue: number;
    adjustedRevenuePercent: string;
    numericEntryAsPerBanking: number;
    numericEntryAddToInterfirm: boolean | null;
}

export interface Gst3BSupplierDetails {
    supplierName: string;
    adjustedPurchaseAndExpenses: number;
    adjustedPurchaseAndExpensesPercent: string;
    numericEntryAsPerBanking: number;
    numericEntryAddToInterfirm: boolean | null;
}

export interface Gst3BSuppliers {
    gst3BSuppliersDetails: Gst3BSupplierDetails[],
    adjustedPurchaseAndExpensesTotal: number
}

export interface Gst3BCustomers {
    gst3BCustomersDetails: Gst3BCustomerDetails[],
    adjustedRevenueTotal: number
}

export interface Gst3BSalesReport {
    entityId: string,
    clientOrderId: string,
    tradeName: string,
    gst3BSales: Gst3BSales,
    interStateCustomersAnalysis: number,
    interStateSuppliersAnalysis: number,
    grossAdjustedRevenue: string | null,
    gst3BSuppliers: Gst3BSuppliers,
    gst3BCustomers: Gst3BCustomers,
    circularOrOthersCustomersAnalysis: number,
    circularOrOthersSuppliersAnalysis: number,
    totalNumberOfMonthsCustomersAnalysis: number,
    totalNumberOfMonthsSuppliersAnalysis: number,
}

export interface ParticularsData {
    customers: {
        gst3BCustomersDetails: Gst3BCustomerDetails[];
        adjustedRevenueTotal: number;
    };
    suppliers: {
        gst3BSuppliersDetails: Gst3BSupplierDetails[];
        adjustedPurchaseAndExpensesTotal: number;
    };
}

export interface ProfileDetailResponseData {
    uuid: string,
    panNumber: string,
    gstNumber: string,
    legalName: string,
    tradeName: string,
    constitutionOfBusiness: string,
    natureOfBusiness: string,
    natureOfCoreBusinessActivity: string,
    status: string,
    taxPayerType: string,
    address: string,
    placeOfBusiness: string,
    state: string,
    stateJurisdiction: string,
    stateJurisdictionCode: string,
    centerJurisdiction: string,
    centerJurisdictionCode: string,
    latitude: string,
    longitude: string,
    dateOfRegistration: string,
    dateOfCancellation: string,
    lastUpdatedDate: string,
}

export interface ProfileDetailResponse {
    clientOrderId: string,
    entityId: string,
    profileDetails: ProfileDetailResponseData[],
}

export interface GetGST3BReportRequestResponseInfo {
    entityId: string | null;
    clientOrderIds: string[] | null;
    companyName: string | null;
}

export interface GetGST3BReportRequestHistoryResponseDTO {
    getGST3BReportRequestResponseInfoList: GetGST3BReportRequestResponseInfo[],
}
