package com.pf.perfios.constant;

public final class PerfiosUrlConstants {
    public static final String ORGANISATION_NAME = "masFinancial";
    public static final String PREFIX_URL = "/KuberaVault/api/v3/organisations/" + ORGANISATION_NAME;
    public static final String INITIATE_TRANSACTION_URL = PREFIX_URL + "/transactions";
    public static final String CANCEL_TRANSACTION_URL = PREFIX_URL + "/transactions/{transactionId}?cancel";
    public static final String DELETE_TRANSACTION_URL = PREFIX_URL + "/transactions/{transactionId}";
    public static final String FILE_UPLOAD_URL = PREFIX_URL + "/transactions/{transactionId}/files";
    public static final String PROCESS_STATEMENT_URL = PREFIX_URL + "/transactions/{transactionId}/bank-statements";
    public static final String RE_PROCESS_STATEMENT_URL = PREFIX_URL + "/transactions/{transactionId}/bank-statements/{fileId}";
    public static final String GENERATE_REPORT_URL = PREFIX_URL + "/transactions/{transactionId}/reports";
    public static final String RETRIEVE_REPORT_URL = PREFIX_URL + "/transactions/{transactionId}/reports?types={types}";

    public static final String TRANSACTION_STATUS_URL = PREFIX_URL + "/transactions/{transactionId}/status";
    public static final String BULK_TRANSACTION_STATUS_URL = PREFIX_URL + "/transactions/status?startTime={startTime}&endTime={endTime}";
    public static final String LIST_INSTITUTIONS_URL = PREFIX_URL + "/institutions?processingType={processingType}&dataSource={dataSource}";
    public static final String REPROCESS_TRANSACTION_URL = PREFIX_URL + "/transactions/reprocess";

    private PerfiosUrlConstants() {
        // constants
    }
}
