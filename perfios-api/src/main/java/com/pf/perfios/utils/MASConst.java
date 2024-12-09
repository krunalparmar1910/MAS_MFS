package com.pf.perfios.utils;

import java.math.BigDecimal;

public final class MASConst {

    public static final String PROCESSING_TYPE = "STATEMENT";
    public static final BigDecimal LOAN_AMOUNT = BigDecimal.valueOf(200000.00);
    public static final Long LOAN_DURATION = 35L;
    public static final String LOAN_TYPE = "Home";
    public static final String ACCEPTANCE_POLICY = "atLeastOneTransactionInRange";
    public static final String REPORT_TYPE_JSON = "json";
    public static final String REPORT_TYPE_XLSX = "xlsx";

    private MASConst(){
        //do nothing
    }
}
