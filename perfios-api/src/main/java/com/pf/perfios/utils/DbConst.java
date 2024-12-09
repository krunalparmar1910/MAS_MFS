package com.pf.perfios.utils;

public final class DbConst {
    public static final String SCHEMA_NAME = "mas_datastore_perfios";
    public static final String CREDIT_INTER_BANK_TRANSACTION_FLAG = "Credit Inter Bank";
    public static final String CREDIT_INTER_FIRM_TRANSACTION_FLAG = "Credit Inter Firm";
    public static final String CREDIT_NON_BUSINESS_TRANSACTION_FLAG = "Credit Non-Business Transaction";

    public static final String DEBIT_INTER_BANK_TRANSACTION_FLAG = "Debit Inter Bank";
    public static final String DEBIT_INTER_FIRM_TRANSACTION_FLAG = "Debit Inter Firm";
    public static final String DEBIT_NON_BUSINESS_TRANSACTION_FLAG = "Debit Non-Business Transaction";
    public static final String LOAN_DISBURSEMENT_TRANSACTION_TYPE = "Loan Disbursement";
    public static final String EMI_TRACING_TRANSACTION_TYPE = "Emi Tracing";

    public static final String IW_RETURN_IDENTIFIER = "insufficient funds, exceed arrangement, I/W return";

    private DbConst() {
        // do nothing
    }

}
