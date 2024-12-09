package com.pf.mas.utils.cibil;

import com.pf.mas.model.dto.cibil.enums.AssetClassificationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Getter
@NoArgsConstructor
public class MappingRegistry {

    public static final Map<AssetClassificationStatus, Integer> CIBIL_STATUS_PRIORITY_MAP = new HashMap<>();

    private static final Map<String, String> ACCOUNT_ERROR_CODES = new HashMap<>();
    private static final Map<String, String> HEADER_CRI_SCORE_TYPE = new HashMap<>();
    private static final Map<String, String> HEADER_CRI_OUTPUT_FORMAT = new HashMap<>();
    private static final Map<String, String> HEADER_CRI_MEDIA_TYPE = new HashMap<>();
    private static final Map<String, String> HEADER_CRI_AUTHENTICATION_METHOD = new HashMap<>();

    private static final Map<String, String> ADDRESS_CRI_ADDRESS_CATEGORY = new HashMap<>();
    private static final Map<String, String> ADDRESS_CRI_RESIDENCE_CODE = new HashMap<>();

    private static final Map<String, String> NAME_SEGMENT_RESPONSE_GENDER = new HashMap<>();
    private static final Map<String, String> ID_SEGMENT_RESPONSE_ID_TYPE = new HashMap<>();
    private static final Map<String, String> TELEPHONE_SEGMENT_RESPONSE_TELEPHONE_TYPE = new HashMap<>();
    private static final Map<String, String> EMPLOYMENT_SEGMENT_RESPONSE_OCCUPATION_CODE = new HashMap<>();
    private static final Map<String, String> EMPLOYMENT_SEGMENT_RESPONSE_NET_GROSS_INC_INDICATOR = new HashMap<>();
    private static final Map<String, String> EMPLOYMENT_SEGMENT_RESPONSE_MONTHLY_ANNUAL_INC_INDICATOR = new HashMap<>();
    private static final Map<String, String> EMPLOYMENT_SEGMENT_RESPONSE_ERROR_CODE = new HashMap<>();
    private static final Map<String, String> SCORE_SEGMENT_RESPONSE_SCORE_CARD_NAME = new HashMap<>();
    private static final Map<String, String> SCORE_SEGMENT_ERROR_CODE = new HashMap<>();
    private static final Map<String, String> CREDIT_REPORT_ADDRESS_CATEGORY = new HashMap<>();
    private static final Map<String, String> CREDIT_REPORT_ADDRESS_RESIDENCE_CODE = new HashMap<>();
    private static final Map<String, String> STATE_CODE_MAP = new HashMap<>();

    private static final Map<String, String> ACCOUNT_AND_LOAN_TYPE = new HashMap<>();
    private static final Map<String, String> ACCOUNT_OWNERSHIP_INDICATOR = new HashMap<>();
    private static final Map<String, String> ACCOUNT_SUIT_FILED_WILFUL_DEFAULT = new HashMap<>();
    private static final Map<String, String> ACCOUNT_CREDIT_FACILITY_STATUS = new HashMap<>();
    private static final Map<String, String> ACCOUNT_TYPE_OF_COLLATERAL = new HashMap<>();
    private static final Map<String, String> ACCOUNT_PAYMENT_FREQUENCY = new HashMap<>();

    private static final Map<String, String> ACCOUNT_SEGMENT_SUMMARY_ACCOUNT_GROUP = new HashMap<>();
    private static final Map<String, String> ACCOUNT_SEGMENT_SUMMARY_LIVE_CLOSED_INDICATOR = new HashMap<>();
    private static final Map<String, String> LOAN_SECURITY_STATUS_MAP = new HashMap<>();
    private static final Map<String, String> CIBIL_REMARKS_CODES_MAP = new HashMap<>();

    static {
        CIBIL_STATUS_PRIORITY_MAP.put(AssetClassificationStatus.XXX, -1);
        CIBIL_STATUS_PRIORITY_MAP.put(AssetClassificationStatus.STD, 0);
        CIBIL_STATUS_PRIORITY_MAP.put(AssetClassificationStatus.SMA, 1);
        CIBIL_STATUS_PRIORITY_MAP.put(AssetClassificationStatus.SUB, 2);
        CIBIL_STATUS_PRIORITY_MAP.put(AssetClassificationStatus.DBT, 3);
        CIBIL_STATUS_PRIORITY_MAP.put(AssetClassificationStatus.LSS, 4);
    }

    static {
        ACCOUNT_ERROR_CODES.put("000", "No Disputes");
        ACCOUNT_ERROR_CODES.put("003", "Account Number in Dispute");
        ACCOUNT_ERROR_CODES.put("004", "Account Type in Dispute");
        ACCOUNT_ERROR_CODES.put("005", "Ownership Indicator in Dispute");
        ACCOUNT_ERROR_CODES.put("008", "Date Opened/Disbursed in Dispute");
        ACCOUNT_ERROR_CODES.put("009", "Date of Last Payment in Dispute");
        ACCOUNT_ERROR_CODES.put("010", "Date Closed in Dispute");
        ACCOUNT_ERROR_CODES.put("011", "Date Reported and Certified in Dispute");
        ACCOUNT_ERROR_CODES.put("012", "High Credit/Sanctioned Amount in Dispute");
        ACCOUNT_ERROR_CODES.put("013", "Current Balance in Dispute");
        ACCOUNT_ERROR_CODES.put("014", "Amount Overdue in Dispute");
        ACCOUNT_ERROR_CODES.put("030", "Payment History Start Date in Dispute");
        ACCOUNT_ERROR_CODES.put("031", "Payment History End Date in Dispute");
        ACCOUNT_ERROR_CODES.put("032", "Suit Filed / Wilful Default in Dispute");
        ACCOUNT_ERROR_CODES.put("033", "Credit Facility Status in Dispute");
        ACCOUNT_ERROR_CODES.put("034", "Value of Collateral in Dispute");
        ACCOUNT_ERROR_CODES.put("035", "Type of Collateral in Dispute");
        ACCOUNT_ERROR_CODES.put("036", "Credit Limit in Dispute");
        ACCOUNT_ERROR_CODES.put("037", "Cash Limit in Dispute");
        ACCOUNT_ERROR_CODES.put("038", "Rate Of Interest in Dispute");
        ACCOUNT_ERROR_CODES.put("039", "Repayment Tenure in Dispute");
        ACCOUNT_ERROR_CODES.put("040", "EMI Amount in Dispute");
        ACCOUNT_ERROR_CODES.put("041", "Written-off Amount (Total) in Dispute");
        ACCOUNT_ERROR_CODES.put("042", "Written-off Amount (Principal) in Dispute");
        ACCOUNT_ERROR_CODES.put("043", "Settlement Amount in Dispute");
        ACCOUNT_ERROR_CODES.put("044", "Payment Frequency in Dispute");
        ACCOUNT_ERROR_CODES.put("045", "Actual Payment Amount in Dispute");
        ACCOUNT_ERROR_CODES.put("101", "Payment History 1 in Dispute");
        ACCOUNT_ERROR_CODES.put("102", "Payment History 2 in Dispute");
        ACCOUNT_ERROR_CODES.put("103", "Payment History 3 in Dispute");
        ACCOUNT_ERROR_CODES.put("104", "Payment History 4 in Dispute");
        ACCOUNT_ERROR_CODES.put("105", "Payment History 5 in Dispute");
        ACCOUNT_ERROR_CODES.put("106", "Payment History 6 in Dispute");
        ACCOUNT_ERROR_CODES.put("107", "Payment History 7 in Dispute");
        ACCOUNT_ERROR_CODES.put("108", "Payment History 8 in Dispute");
        ACCOUNT_ERROR_CODES.put("109", "Payment History 9 in Dispute");
        ACCOUNT_ERROR_CODES.put("110", "Payment History 10 in Dispute");
        ACCOUNT_ERROR_CODES.put("111", "Payment History 11 in Dispute");
        ACCOUNT_ERROR_CODES.put("112", "Payment History 12 in Dispute");
        ACCOUNT_ERROR_CODES.put("113", "Payment History 13 in Dispute");
        ACCOUNT_ERROR_CODES.put("114", "Payment History 14 in Dispute");
        ACCOUNT_ERROR_CODES.put("115", "Payment History 15 in Dispute");
        ACCOUNT_ERROR_CODES.put("116", "Payment History 16 in Dispute");
        ACCOUNT_ERROR_CODES.put("117", "Payment History 17 in Dispute");
        ACCOUNT_ERROR_CODES.put("118", "Payment History 18 in Dispute");
        ACCOUNT_ERROR_CODES.put("119", "Payment History 19 in Dispute");
        ACCOUNT_ERROR_CODES.put("120", "Payment History 20 in Dispute");
        ACCOUNT_ERROR_CODES.put("121", "Payment History 21 in Dispute");
        ACCOUNT_ERROR_CODES.put("122", "Payment History 22 in Dispute");
        ACCOUNT_ERROR_CODES.put("123", "Payment History 23 in Dispute");
        ACCOUNT_ERROR_CODES.put("124", "Payment History 24 in Dispute");
        ACCOUNT_ERROR_CODES.put("125", "Payment History 25 in Dispute");
        ACCOUNT_ERROR_CODES.put("126", "Payment History 26 in Dispute");
        ACCOUNT_ERROR_CODES.put("127", "Payment History 27 in Dispute");
        ACCOUNT_ERROR_CODES.put("128", "Payment History 28 in Dispute");
        ACCOUNT_ERROR_CODES.put("129", "Payment History 29 in Dispute");
        ACCOUNT_ERROR_CODES.put("130", "Payment History 30 in Dispute");
        ACCOUNT_ERROR_CODES.put("131", "Payment History 31 in Dispute");
        ACCOUNT_ERROR_CODES.put("132", "Payment History 32 in Dispute");
        ACCOUNT_ERROR_CODES.put("133", "Payment History 33 in Dispute");
        ACCOUNT_ERROR_CODES.put("134", "Payment History 34 in Dispute");
        ACCOUNT_ERROR_CODES.put("135", "Payment History 35 in Dispute");
        ACCOUNT_ERROR_CODES.put("136", "Payment History 36 in Dispute");
        ACCOUNT_ERROR_CODES.put("203", "Account Number - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("204", "Account Type - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("205", "Ownership Indicator - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("208", "Date Opened/Disbursed - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("209", "Date of Last Payment - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("210", "Date Closed - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("211", "Date Reported and Certified - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("212", "High Credit/Sanctioned Amount - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("213", "Current Balance - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("214", "Amount Overdue - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("230", "Payment History Start Date - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("231", "Payment History End Date - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("232", "Suit Filed / Wilful Default - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("233", "Credit Facility Status - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("234", "Value of Collateral - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("235", "Type of Collateral - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("236", "Credit Limit - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("237", "Cash Limit - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("238", "Rate Of Interest - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("239", "Repayment Tenure - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("240", "EMI Amount - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("241", "Written-off Amount (Total) - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("242", "Written-off Amount (Principal) - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("243", "Settlement Amount - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("244", "Payment Frequency - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("245", "Actual Payment Amount - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("301", "Payment History 1 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("302", "Payment History 2 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("303", "Payment History 3 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("304", "Payment History 4 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("305", "Payment History 5 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("306", "Payment History 6 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("307", "Payment History 7 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("308", "Payment History 8 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("309", "Payment History 9 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("310", "Payment History 10 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("311", "Payment History 11 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("312", "Payment History 12 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("313", "Payment History 13 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("314", "Payment History 14 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("315", "Payment History 15 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("316", "Payment History 16 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("317", "Payment History 17 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("318", "Payment History 18 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("319", "Payment History 19 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("320", "Payment History 20 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("321", "Payment History 21 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("322", "Payment History 22 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("323", "Payment History 23 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("324", "Payment History 24 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("325", "Payment History 25 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("326", "Payment History 26 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("327", "Payment History 27 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("328", "Payment History 28 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("329", "Payment History 29 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("330", "Payment History 30 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("331", "Payment History 31 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("332", "Payment History 32 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("333", "Payment History 33 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("334", "Payment History 34 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("335", "Payment History 35 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("336", "Payment History 36 - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("885", "Duplicate Account - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("886", "Duplicate Account");
        ACCOUNT_ERROR_CODES.put("887", "Account Ownership Error - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("888", "Account Ownership Error");
        ACCOUNT_ERROR_CODES.put("998", "Multiple Disputes - Dispute accepted - Pending corrections by the Member");
        ACCOUNT_ERROR_CODES.put("999", "Multiple Disputes");

        HEADER_CRI_SCORE_TYPE.put("00", "Not requesting any record.");
        HEADER_CRI_SCORE_TYPE.put("01", "CIBILTUSCR");
        HEADER_CRI_SCORE_TYPE.put("02", "PLSCORE)");
        HEADER_CRI_SCORE_TYPE.put("03", "CIBILTUSCR+PLSCORE");
        HEADER_CRI_SCORE_TYPE.put("04", "CIBILTUSC2)");
        HEADER_CRI_SCORE_TYPE.put("06", "CIBILTUSC2+PLSCORE");
        HEADER_CRI_SCORE_TYPE.put("08", "CIBILTUSC3");
        HEADER_CRI_SCORE_TYPE.put("10", "CIBILTUSC3+PLSCORE");
        HEADER_CRI_SCORE_TYPE.put("12", "CIBILTUIE2");
        HEADER_CRI_SCORE_TYPE.put("13", "CIBILTUIE2+CIBILTUSC3");
        HEADER_CRI_SCORE_TYPE.put("15", "CIBILTUIE2+CIBILTUSC3+PLSCORE");

        HEADER_CRI_OUTPUT_FORMAT.put("01", "Machine-Readable Formatted Response Record");
        HEADER_CRI_OUTPUT_FORMAT.put("02", "Print Image Report");
        HEADER_CRI_OUTPUT_FORMAT.put("04", "Tab-delimited Report");

        HEADER_CRI_MEDIA_TYPE.put("CC", "CPU-to-CPU");
        HEADER_CRI_MEDIA_TYPE.put("TT", "Tape-to-Tape");


        HEADER_CRI_AUTHENTICATION_METHOD.put("L", "Legacy");
        HEADER_CRI_AUTHENTICATION_METHOD.put("A", "Advanced");

        ADDRESS_CRI_ADDRESS_CATEGORY.put("01", "Permanent Address");
        ADDRESS_CRI_ADDRESS_CATEGORY.put("02", "Residence Address");
        ADDRESS_CRI_ADDRESS_CATEGORY.put("03", "Office Address");
        ADDRESS_CRI_ADDRESS_CATEGORY.put("04", "Not Categorized");
        ADDRESS_CRI_ADDRESS_CATEGORY.put("05", "Mortgage Property address");

        ADDRESS_CRI_RESIDENCE_CODE.put("01", "Owned");
        ADDRESS_CRI_RESIDENCE_CODE.put("02", "Rented");

        NAME_SEGMENT_RESPONSE_GENDER.put("1", "Female");
        NAME_SEGMENT_RESPONSE_GENDER.put("2", "Male");
        NAME_SEGMENT_RESPONSE_GENDER.put("3", "Transgender");

        ID_SEGMENT_RESPONSE_ID_TYPE.put("01", "Income Tax ID Number (PAN)");
        ID_SEGMENT_RESPONSE_ID_TYPE.put("02", "Passport Number");
        ID_SEGMENT_RESPONSE_ID_TYPE.put("03", "Voter ID Number");
        ID_SEGMENT_RESPONSE_ID_TYPE.put("04", "Driver’s License Number");
        ID_SEGMENT_RESPONSE_ID_TYPE.put("05", "Ration Card Number");
        ID_SEGMENT_RESPONSE_ID_TYPE.put("06", "Universal ID Number (UID)");
        ID_SEGMENT_RESPONSE_ID_TYPE.put("07", "Additional ID #1 (For Future Use)");
        ID_SEGMENT_RESPONSE_ID_TYPE.put("08", "Additional ID #2 (For Future Use)");
        ID_SEGMENT_RESPONSE_ID_TYPE.put("09", "CKYC");
        ID_SEGMENT_RESPONSE_ID_TYPE.put("10", "NREGA Card Number");

        TELEPHONE_SEGMENT_RESPONSE_TELEPHONE_TYPE.put("00", "Not Classified");
        TELEPHONE_SEGMENT_RESPONSE_TELEPHONE_TYPE.put("01", "Mobile Phone");
        TELEPHONE_SEGMENT_RESPONSE_TELEPHONE_TYPE.put("02", "Home Phone");
        TELEPHONE_SEGMENT_RESPONSE_TELEPHONE_TYPE.put("03", "Office Phone");

        EMPLOYMENT_SEGMENT_RESPONSE_OCCUPATION_CODE.put("01", "Salaried");
        EMPLOYMENT_SEGMENT_RESPONSE_OCCUPATION_CODE.put("02", "Self Employed Professional");
        EMPLOYMENT_SEGMENT_RESPONSE_OCCUPATION_CODE.put("03", "Self Employed");
        EMPLOYMENT_SEGMENT_RESPONSE_OCCUPATION_CODE.put("04", "Others");

        EMPLOYMENT_SEGMENT_RESPONSE_NET_GROSS_INC_INDICATOR.put("G", "Gross Income");
        EMPLOYMENT_SEGMENT_RESPONSE_NET_GROSS_INC_INDICATOR.put("N", "Net Income");
        EMPLOYMENT_SEGMENT_RESPONSE_MONTHLY_ANNUAL_INC_INDICATOR.put("M", "Monthly");
        EMPLOYMENT_SEGMENT_RESPONSE_MONTHLY_ANNUAL_INC_INDICATOR.put("A", "Annual");

        EMPLOYMENT_SEGMENT_RESPONSE_ERROR_CODE.put("000", "No Dispute");
        EMPLOYMENT_SEGMENT_RESPONSE_ERROR_CODE.put("003", "Occupation Code in Dispute");
        EMPLOYMENT_SEGMENT_RESPONSE_ERROR_CODE.put("004", "Income in Dispute");
        EMPLOYMENT_SEGMENT_RESPONSE_ERROR_CODE.put("005", "Net/Gross Income Indicator in Dispute");
        EMPLOYMENT_SEGMENT_RESPONSE_ERROR_CODE.put("006", "Monthly/Annual Income Indicator in Dispute");
        EMPLOYMENT_SEGMENT_RESPONSE_ERROR_CODE.put("203", "Occupation Code - Dispute accepted - Pending corrections by the Member");
        EMPLOYMENT_SEGMENT_RESPONSE_ERROR_CODE.put("204", "Income - Dispute accepted - Pending corrections by the Member");
        EMPLOYMENT_SEGMENT_RESPONSE_ERROR_CODE.put("205", "Net/Gross Income Indicator - Dispute accepted - Pending corrections by the Member");
        EMPLOYMENT_SEGMENT_RESPONSE_ERROR_CODE.put("206", "Monthly/Annual Income Indicator - Dispute accepted - Pending corrections by the Member");
        EMPLOYMENT_SEGMENT_RESPONSE_ERROR_CODE.put("998", "Multiple Disputes - Dispute accepted - Pending corrections by the Member");
        EMPLOYMENT_SEGMENT_RESPONSE_ERROR_CODE.put("999", "Multiple Disputes");

        SCORE_SEGMENT_RESPONSE_SCORE_CARD_NAME.put("01", "CIBILTUSCR");
        SCORE_SEGMENT_RESPONSE_SCORE_CARD_NAME.put("02", "PLSCORE");
        SCORE_SEGMENT_RESPONSE_SCORE_CARD_NAME.put("04", "CIBILTUSC2");
        SCORE_SEGMENT_RESPONSE_SCORE_CARD_NAME.put("08", "CIBILTUSC3");
        SCORE_SEGMENT_RESPONSE_SCORE_CARD_NAME.put("12", "CIBILTUIE2");
        SCORE_SEGMENT_RESPONSE_SCORE_CARD_NAME.put("16", "CIBILNTC1");

        SCORE_SEGMENT_ERROR_CODE.put("00001", "FID Not Found");
        SCORE_SEGMENT_ERROR_CODE.put("00002", "Duplicate FID");
        SCORE_SEGMENT_ERROR_CODE.put("00003", "Error related to Consumer Data Issue");
        SCORE_SEGMENT_ERROR_CODE.put("00004", "Error related to Database Issue");
        SCORE_SEGMENT_ERROR_CODE.put("00005", "Error during score calculation");


        CREDIT_REPORT_ADDRESS_RESIDENCE_CODE.put("01", "Owned");
        CREDIT_REPORT_ADDRESS_RESIDENCE_CODE.put("02", "Rented");

        STATE_CODE_MAP.put("01", "Jammu & Kashmir");
        STATE_CODE_MAP.put("02", "Himachal Pradesh");
        STATE_CODE_MAP.put("03", "Punjab");
        STATE_CODE_MAP.put("04", "Chandigarh");
        STATE_CODE_MAP.put("05", "Uttaranchal");
        STATE_CODE_MAP.put("06", "Haryana");
        STATE_CODE_MAP.put("07", "Delhi");
        STATE_CODE_MAP.put("08", "Rajasthan");
        STATE_CODE_MAP.put("09", "Uttar Pradesh");
        STATE_CODE_MAP.put("10", "Bihar");
        STATE_CODE_MAP.put("11", "Sikkim");
        STATE_CODE_MAP.put("12", "Arunachal Pradesh");
        STATE_CODE_MAP.put("13", "Nagaland");
        STATE_CODE_MAP.put("14", "Manipur");
        STATE_CODE_MAP.put("15", "Mizoram");
        STATE_CODE_MAP.put("16", "Tripura");
        STATE_CODE_MAP.put("17", "Meghalaya");
        STATE_CODE_MAP.put("18", "Assam");
        STATE_CODE_MAP.put("19", "West Bengal");
        STATE_CODE_MAP.put("20", "Jharkhand");
        STATE_CODE_MAP.put("21", "Orissa");
        STATE_CODE_MAP.put("22", "Chhattisgarh");
        STATE_CODE_MAP.put("23", "Madhya Pradesh");
        STATE_CODE_MAP.put("24", "Gujarat");
        STATE_CODE_MAP.put("25", "Daman & Diu");
        STATE_CODE_MAP.put("26", "Dadra & Nagar Haveli and Daman & Diu");
        STATE_CODE_MAP.put("27", "Maharashtra");
        STATE_CODE_MAP.put("28", "Andhra Pradesh");
        STATE_CODE_MAP.put("29", "Karnataka");
        STATE_CODE_MAP.put("30", "Goa");
        STATE_CODE_MAP.put("31", "Lakshadweep");
        STATE_CODE_MAP.put("32", "Kerala");
        STATE_CODE_MAP.put("33", "Tamil Nadu");
        STATE_CODE_MAP.put("34", "Pondicherry");
        STATE_CODE_MAP.put("35", "Andaman & Nicobar Islands");
        STATE_CODE_MAP.put("36", "Telangana");
        STATE_CODE_MAP.put("38", "Ladakh");
        STATE_CODE_MAP.put("99", "APO Address");

        CREDIT_REPORT_ADDRESS_CATEGORY.put("01", "Permanent Address");
        CREDIT_REPORT_ADDRESS_CATEGORY.put("02", "Residence Address");
        CREDIT_REPORT_ADDRESS_CATEGORY.put("03", "Office Address");
        CREDIT_REPORT_ADDRESS_CATEGORY.put("04", "Not Categorized");
        CREDIT_REPORT_ADDRESS_CATEGORY.put("05", "Mortgage Property address");

        ACCOUNT_AND_LOAN_TYPE.put("01", "Auto Loan (Personal)");
        ACCOUNT_AND_LOAN_TYPE.put("02", "Housing Loan");
        ACCOUNT_AND_LOAN_TYPE.put("03", "Property Loan");
        ACCOUNT_AND_LOAN_TYPE.put("04", "Loan Against Shares/Securities");
        ACCOUNT_AND_LOAN_TYPE.put("05", "Personal Loan");
        ACCOUNT_AND_LOAN_TYPE.put("06", "Consumer Loan");
        ACCOUNT_AND_LOAN_TYPE.put("07", "Gold Loan");
        ACCOUNT_AND_LOAN_TYPE.put("08", "Education Loan");
        ACCOUNT_AND_LOAN_TYPE.put("09", "Loan to Professional");
        ACCOUNT_AND_LOAN_TYPE.put("10", "Credit Card");
        ACCOUNT_AND_LOAN_TYPE.put("11", "Leasing");
        ACCOUNT_AND_LOAN_TYPE.put("12", "Overdraft");
        ACCOUNT_AND_LOAN_TYPE.put("13", "Two-wheeler Loan");
        ACCOUNT_AND_LOAN_TYPE.put("14", "Non-Funded Credit Facility");
        ACCOUNT_AND_LOAN_TYPE.put("15", "Loan Against Bank Deposits");
        ACCOUNT_AND_LOAN_TYPE.put("16", "Fleet Card");
        ACCOUNT_AND_LOAN_TYPE.put("17", "Commercial Vehicle Loan");
        ACCOUNT_AND_LOAN_TYPE.put("18", "Telco – Wireless");
        ACCOUNT_AND_LOAN_TYPE.put("19", "Telco – Broadband");
        ACCOUNT_AND_LOAN_TYPE.put("20", "Telco – Landline");
        ACCOUNT_AND_LOAN_TYPE.put("21", "Seller Financing");
        ACCOUNT_AND_LOAN_TYPE.put("22", "Seller Financing Soft (Applicable to Enquiry Purpose only)");
        ACCOUNT_AND_LOAN_TYPE.put("23", "GECL Loan Secured");
        ACCOUNT_AND_LOAN_TYPE.put("24", "GECL Loan Unsecured");
        ACCOUNT_AND_LOAN_TYPE.put("31", "Secured Credit Card");
        ACCOUNT_AND_LOAN_TYPE.put("32", "Used Car Loan");
        ACCOUNT_AND_LOAN_TYPE.put("33", "Construction Equipment Loan");
        ACCOUNT_AND_LOAN_TYPE.put("34", "Tractor Loan");
        ACCOUNT_AND_LOAN_TYPE.put("35", "Corporate Credit Card");
        ACCOUNT_AND_LOAN_TYPE.put("36", "Kisan Credit Card");
        ACCOUNT_AND_LOAN_TYPE.put("37", "Loan on Credit Card");
        ACCOUNT_AND_LOAN_TYPE.put("38", "Prime Minister Jaan Dhan Yojana - Overdraft");
        ACCOUNT_AND_LOAN_TYPE.put("39", "Mudra Loans – Shishu / Kishor / Tarun");
        ACCOUNT_AND_LOAN_TYPE.put("40", "Microfinance – Business Loan");
        ACCOUNT_AND_LOAN_TYPE.put("41", "Microfinance – Personal Loan");
        ACCOUNT_AND_LOAN_TYPE.put("42", "Microfinance – Housing Loan");
        ACCOUNT_AND_LOAN_TYPE.put("43", "Microfinance – Other");
        ACCOUNT_AND_LOAN_TYPE.put("44", "Pradhan Mantri Awas Yojana - Credit Link Subsidy Scheme MAY CLSS");
        ACCOUNT_AND_LOAN_TYPE.put("45", "P2P Personal Loan");
        ACCOUNT_AND_LOAN_TYPE.put("46", "P2P Auto Loan");
        ACCOUNT_AND_LOAN_TYPE.put("47", "P2P Education Loan");
        ACCOUNT_AND_LOAN_TYPE.put("50", "Business Loan – Secured");
        ACCOUNT_AND_LOAN_TYPE.put("51", "Business Loan – General");
        ACCOUNT_AND_LOAN_TYPE.put("52", "Business Loan – Priority Sector – Small Business");
        ACCOUNT_AND_LOAN_TYPE.put("53", "Business Loan – Priority Sector – Agriculture");
        ACCOUNT_AND_LOAN_TYPE.put("54", "Business Loan – Priority Sector – Others");
        ACCOUNT_AND_LOAN_TYPE.put("55", "Business Non-Funded Credit Facility – General");
        ACCOUNT_AND_LOAN_TYPE.put("56", "Business Non-Funded Credit Facility – Priority Sector – Small Business");
        ACCOUNT_AND_LOAN_TYPE.put("57", "Business Non-Funded Credit Facility – Priority Sector – Agriculture");
        ACCOUNT_AND_LOAN_TYPE.put("58", "Business Non-Funded Credit Facility – Priority Sector-Others");
        ACCOUNT_AND_LOAN_TYPE.put("59", "Business Loan Against Bank Deposits");
        ACCOUNT_AND_LOAN_TYPE.put("61", "Business Loan - Unsecured");
        ACCOUNT_AND_LOAN_TYPE.put("69", "Short Term Personal Loan [Unsecured]");
        ACCOUNT_AND_LOAN_TYPE.put("70", "Priority Sector- Gold Loan [Secured]");
        ACCOUNT_AND_LOAN_TYPE.put("71", "Temporary Overdraft [Unsecured]");
        ACCOUNT_AND_LOAN_TYPE.put("80", "Microfinance Detailed Report (Applicable to Enquiry Purpose only)");
        ACCOUNT_AND_LOAN_TYPE.put("81", "Summary Report (Applicable to Enquiry Purpose only)");
        ACCOUNT_AND_LOAN_TYPE.put("88", "Locate Plus for Insurance (Applicable to Enquiry Purpose only)");
        ACCOUNT_AND_LOAN_TYPE.put("90", "Account Review (Applicable to Enquiry Purpose only)");
        ACCOUNT_AND_LOAN_TYPE.put("91", "Retro Enquiry (Applicable to Enquiry Purpose only)");
        ACCOUNT_AND_LOAN_TYPE.put("92", "Locate Plus (Applicable to Enquiry Purpose only)");
        ACCOUNT_AND_LOAN_TYPE.put("97", "Adviser Liability (Applicable to Enquiry Purpose only)");
        ACCOUNT_AND_LOAN_TYPE.put("00", "Other");
        ACCOUNT_AND_LOAN_TYPE.put("98", "Secured (Account Group for Portfolio Review response)");
        ACCOUNT_AND_LOAN_TYPE.put("99", "Unsecured (Account Group for Portfolio Review response)");

        ACCOUNT_OWNERSHIP_INDICATOR.put("1", "Individual");
        ACCOUNT_OWNERSHIP_INDICATOR.put("2", "Authorised User (refers to supplementary credit card holder)");
        ACCOUNT_OWNERSHIP_INDICATOR.put("3", "Guarantor");
        ACCOUNT_OWNERSHIP_INDICATOR.put("4", "Joint");
        ACCOUNT_OWNERSHIP_INDICATOR.put("5", "Deceased");

        ACCOUNT_SUIT_FILED_WILFUL_DEFAULT.put("00", "No Suit Filed");
        ACCOUNT_SUIT_FILED_WILFUL_DEFAULT.put("01", "Suit filed");
        ACCOUNT_SUIT_FILED_WILFUL_DEFAULT.put("02", "Wilful default");
        ACCOUNT_SUIT_FILED_WILFUL_DEFAULT.put("03", "Suit filed (Wilful default)");

        ACCOUNT_CREDIT_FACILITY_STATUS.put("00", "Restructured Loan");
        ACCOUNT_CREDIT_FACILITY_STATUS.put("01", "Restructured Loan (Govt. Mandated)");
        ACCOUNT_CREDIT_FACILITY_STATUS.put("02", "Written-off");
        ACCOUNT_CREDIT_FACILITY_STATUS.put("03", "Settled");
        ACCOUNT_CREDIT_FACILITY_STATUS.put("04", "Post (WO) Settled");
        ACCOUNT_CREDIT_FACILITY_STATUS.put("05", "Account Sold");
        ACCOUNT_CREDIT_FACILITY_STATUS.put("06", "Written Off and Account Sold");
        ACCOUNT_CREDIT_FACILITY_STATUS.put("07", "Account Purchased");
        ACCOUNT_CREDIT_FACILITY_STATUS.put("08", "Account Purchased and Written Off");
        ACCOUNT_CREDIT_FACILITY_STATUS.put("09", "Account Purchased and Settled");
        ACCOUNT_CREDIT_FACILITY_STATUS.put("10", "Account Purchased and Restructured");
        ACCOUNT_CREDIT_FACILITY_STATUS.put("11", "Restructured due to Natural Calamity");
        ACCOUNT_CREDIT_FACILITY_STATUS.put("12", "Restructured due to COVID-19");
        ACCOUNT_CREDIT_FACILITY_STATUS.put("13", "Post Write Off Closed");
        ACCOUNT_CREDIT_FACILITY_STATUS.put("14", "Restructured & Closed");
        ACCOUNT_CREDIT_FACILITY_STATUS.put("15", "Auctioned & Settled");
        ACCOUNT_CREDIT_FACILITY_STATUS.put("16", "Repossessed & Settled");
        ACCOUNT_CREDIT_FACILITY_STATUS.put("17", "Guarantee Invoked");

        ACCOUNT_TYPE_OF_COLLATERAL.put("00", "No Collateral");
        ACCOUNT_TYPE_OF_COLLATERAL.put("01", "Property");
        ACCOUNT_TYPE_OF_COLLATERAL.put("02", "Gold");
        ACCOUNT_TYPE_OF_COLLATERAL.put("03", "Shares");
        ACCOUNT_TYPE_OF_COLLATERAL.put("04", "Saving Account and Fixed Deposit");
        ACCOUNT_TYPE_OF_COLLATERAL.put("05", "Multiple Securities");
        ACCOUNT_TYPE_OF_COLLATERAL.put("06", "Others");

        ACCOUNT_PAYMENT_FREQUENCY.put("01", "Weekly");
        ACCOUNT_PAYMENT_FREQUENCY.put("02", "Fortnightly");
        ACCOUNT_PAYMENT_FREQUENCY.put("03", "Monthly");
        ACCOUNT_PAYMENT_FREQUENCY.put("04", "Quarterly");
        ACCOUNT_PAYMENT_FREQUENCY.put("05", "Bullet payment");
        ACCOUNT_PAYMENT_FREQUENCY.put("06", "Daily");
        ACCOUNT_PAYMENT_FREQUENCY.put("07", "Half yearly");
        ACCOUNT_PAYMENT_FREQUENCY.put("08", "Yearly");
        ACCOUNT_PAYMENT_FREQUENCY.put("09", "On-demand");

        ACCOUNT_SEGMENT_SUMMARY_ACCOUNT_GROUP.put("98", "Secured");
        ACCOUNT_SEGMENT_SUMMARY_ACCOUNT_GROUP.put("99", "Unsecured");

        ACCOUNT_SEGMENT_SUMMARY_LIVE_CLOSED_INDICATOR.put("0", "Live Accounts");
        ACCOUNT_SEGMENT_SUMMARY_LIVE_CLOSED_INDICATOR.put("1", "Closed Accounts");

        LOAN_SECURITY_STATUS_MAP.put("Auto Loan (Personal)", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Housing Loan", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Property Loan", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Loan Against Shares/Securities", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Personal Loan", "Unsecured");
        LOAN_SECURITY_STATUS_MAP.put("Consumer Loan", "Unsecured");
        LOAN_SECURITY_STATUS_MAP.put("Gold Loan", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Education Loan", "Unsecured");
        LOAN_SECURITY_STATUS_MAP.put("Loan to Professional", "Unsecured");
        LOAN_SECURITY_STATUS_MAP.put("Credit Card", "Unsecured");
        LOAN_SECURITY_STATUS_MAP.put("Leasing", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Overdraft", "Unsecured");
        LOAN_SECURITY_STATUS_MAP.put("Two-wheeler Loan", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Non-Funded Credit Facility", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Loan Against Bank Deposits", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Fleet Card", "Unsecured");
        LOAN_SECURITY_STATUS_MAP.put("Commercial Vehicle Loan", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Telco – Wireless", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Telco – Broadband", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Telco – Landline", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Used Car Loan", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Construction Equipment Loan", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Tractor Loan", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Corporate Credit Card", "Unsecured");
        LOAN_SECURITY_STATUS_MAP.put("Kisan Credit Card", "Unsecured");
        LOAN_SECURITY_STATUS_MAP.put("Loan on Credit Card", "Unsecured");
        LOAN_SECURITY_STATUS_MAP.put("Prime Minister Jaan Dhan Yojana – Overdraft", "Unsecured");
        LOAN_SECURITY_STATUS_MAP.put("Mudra Loans – Shishu / Kishor / Tarun", "Unsecured");
        LOAN_SECURITY_STATUS_MAP.put("Microfinance – Business Loan", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Microfinance – Personal Loan", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Microfinance – Housing Loan", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Microfinance – Others", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Pradhan Mantri Awas Yojana - Credit Link Subsidy Scheme MAY CLSS", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("P2P Personal Loan", "Unsecured");
        LOAN_SECURITY_STATUS_MAP.put("P2P Auto Loan", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("P2P Education Loan", "Unsecured");
        LOAN_SECURITY_STATUS_MAP.put("Business Loan – Secured", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Business Loan – General", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Business Loan – Priority Sector – Small Business", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Business Loan – Priority Sector – Agriculture", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Business Loan – Priority Sector – Others", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Business Non-Funded Credit Facility – General", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Business Non-Funded Credit Facility – Priority Sector – Small Business", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Business Non-Funded Credit Facility – Priority Sector – Agriculture", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Business Non-Funded Credit Facility – Priority Sector – Others", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Business Loan Against Bank Deposits", "Secured");
        LOAN_SECURITY_STATUS_MAP.put("Business Loan – Unsecured", "Unsecured");
        LOAN_SECURITY_STATUS_MAP.put("Other", "Unsecured");

        CIBIL_REMARKS_CODES_MAP.put("PN0001", "Certain information under Personal / Contract / Enquiry information section has been disputed by the consumer.");
        CIBIL_REMARKS_CODES_MAP.put("PN1001", "Consumer Name in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("PN1007", "Date of Birth in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("PN1008", "Gender in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("PN1999", "Multiple Disputes in Name (PN) Segment");
        CIBIL_REMARKS_CODES_MAP.put("ID1001", "ID Type in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("ID1002", "ID Number in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("ID1003", "Issue Date in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("ID1004", "Expiration Date in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("ID1999", "Multiple Disputes in Identification (ID) Segment");
        CIBIL_REMARKS_CODES_MAP.put("PT1001", "Telephone Number in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("PT1002", "Telephone Extension in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("PT1003", "Telephone Type in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("PT1999", "Multiple Disputes in Telephone (PT) Segment");
        CIBIL_REMARKS_CODES_MAP.put("EC1001", "E-Mail ID in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("EC1999", "Multiple Disputes in Email Contact (EC) Segment");
        CIBIL_REMARKS_CODES_MAP.put("EM0001", "Certain information under Employment information section has been disputed by the consumer.");
        CIBIL_REMARKS_CODES_MAP.put("EM1003", "Occupation Code in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("EM1004", "Income in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("EM1005", "Net/Gross Income Indicator in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("EM1006", "Monthly/Annual Income Indicator in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("EM1999", "Multiple Disputes in Employment (EM) Segment");
        CIBIL_REMARKS_CODES_MAP.put("PA1001", "Address Line (except State Code and PIN Code) in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("PA1006", "State in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("PA1007", "PIN Code in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("PA1008", "Address Category in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("PA1009", "Residence Code in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("PA1999", "Multiple Disputes in Address (PA) Segment");
        CIBIL_REMARKS_CODES_MAP.put("TL0001", "Certain information for this account has been disputed by the consumer.");
        CIBIL_REMARKS_CODES_MAP.put("TL1003", "Account Number in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1004", "Account Type in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1005", "Ownership Indicator in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1008", "Date Opened/Disbursed in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1009", "Date of Last Payment in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1010", "Date Closed in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1011", "Date Reported and Certified in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1012", "High Credit/Sanctioned Amount in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1013", "Current Balance in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1014", "Amount Overdue in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1030", "Payment History Start Date in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1031", "Payment History End Date in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1032", "Suit Filed / Wilful Default in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1033", "Credit Facility Status in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1034", "Value of Collateral in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1035", "Type of Collateral in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1036", "Credit Limit in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1037", "Cash Limit in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1038", "Rate Of Interest in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1039", "Repayment Tenure in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1040", "EMI Amount in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1041", "Written-off Amount (Total) in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1042", "Written-off Amount (Principal) in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1043", "Settlement Amount in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1044", "Payment Frequency in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1045", "Actual Payment Amount in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1101", "Payment History 1 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1102", "Payment History 2 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1103", "Payment History 3 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1104", "Payment History 4 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1105", "Payment History 5 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1106", "Payment History 6 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1107", "Payment History 7 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1108", "Payment History 8 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1109", "Payment History 9 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1110", "Payment History 10 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1111", "Payment History 11 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1112", "Payment History 12 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1113", "Payment History 13 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1114", "Payment History 14 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1115", "Payment History 15 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1116", "Payment History 16 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1117", "Payment History 17 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1118", "Payment History 18 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1119", "Payment History 19 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1120", "Payment History 20 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1121", "Payment History 21 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1122", "Payment History 22 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1123", "Payment History 23 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1124", "Payment History 24 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1125", "Payment History 25 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1126", "Payment History 26 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1127", "Payment History 27 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1128", "Payment History 28 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1129", "Payment History 29 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1130", "Payment History 30 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1131", "Payment History 31 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1132", "Payment History 32 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1133", "Payment History 33 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1134", "Payment History 34 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1135", "Payment History 35 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1136", "Payment History 36 in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("TL1886", "Duplicate Account");
        CIBIL_REMARKS_CODES_MAP.put("TL1888", "Account Ownership Error");
        CIBIL_REMARKS_CODES_MAP.put("TL1999", "Multiple Disputes in Account (TL) Segment");
        CIBIL_REMARKS_CODES_MAP.put("IQ1001", "Enquiry Purpose in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("IQ1006", "Enquiry Amount in Dispute");
        CIBIL_REMARKS_CODES_MAP.put("IQ1888", "Enquiry Ownership Error");
        CIBIL_REMARKS_CODES_MAP.put("IQ1999", "Multiple Disputes in Enquiry (IQ) SegmentMiscellaneous");
        CIBIL_REMARKS_CODES_MAP.put("000001", "One or more Members have not responded to your Dispute");
        CIBIL_REMARKS_CODES_MAP.put("000002", "Dispute accepted – pending correction by the Member");
        CIBIL_REMARKS_CODES_MAP.put("ZZ0999", "Multiple Disputes in multiple segments");
    }


    public static String getHeaderCriScoreType(String code) {
        return getFromMapOrDefault(HEADER_CRI_SCORE_TYPE, code);
    }

    public static String getHeaderCriOutputFormat(String code) {
        return getFromMapOrDefault(HEADER_CRI_OUTPUT_FORMAT, code);
    }

    public static String getHeaderCriMediaType(String code) {
        return getFromMapOrDefault(HEADER_CRI_MEDIA_TYPE, code);
    }

    public static String getHeaderCriAuthenticationMethod(String code) {
        return getFromMapOrDefault(HEADER_CRI_AUTHENTICATION_METHOD, code);
    }

    public static String getAddressCriAddressCategory(String code) {
        return getFromMapOrDefault(ADDRESS_CRI_ADDRESS_CATEGORY, code);
    }

    public static String getAddressCriResidenceCode(String code) {
        return getFromMapOrDefault(ADDRESS_CRI_RESIDENCE_CODE, code);
    }

    public static String getNameSegmentResponseGender(String code) {
        return getFromMapOrDefault(NAME_SEGMENT_RESPONSE_GENDER, code);
    }

    public static String getIdSegmentResponseIdType(String code) {
        return getFromMapOrDefault(ID_SEGMENT_RESPONSE_ID_TYPE, code);
    }

    public static String getTelephoneSegmentResponseTelephoneType(String code) {
        return getFromMapOrDefault(TELEPHONE_SEGMENT_RESPONSE_TELEPHONE_TYPE, code);
    }

    public static String getEmploymentSegmentResponseOccupationCode(String code) {
        return getFromMapOrDefault(EMPLOYMENT_SEGMENT_RESPONSE_OCCUPATION_CODE, code);
    }

    public static String getEmploymentSegmentResponseNetGrossIncIndicator(String code) {
        return getFromMapOrDefault(EMPLOYMENT_SEGMENT_RESPONSE_NET_GROSS_INC_INDICATOR, code);
    }

    public static String getEmploymentSegmentResponseMonthlyAnnualIncIndicator(String code) {
        return getFromMapOrDefault(EMPLOYMENT_SEGMENT_RESPONSE_MONTHLY_ANNUAL_INC_INDICATOR, code);
    }

    public static String getScoreSegmentResponseScoreCardName(String code) {
        return getFromMapOrDefault(SCORE_SEGMENT_RESPONSE_SCORE_CARD_NAME, code);
    }

    public static String getScoreSegmentErrorCode(String code) {
        return getFromMapOrDefault(SCORE_SEGMENT_ERROR_CODE, code);
    }

    public static String getCreditReportAddressCategory(String code) {
        return getFromMapOrDefault(CREDIT_REPORT_ADDRESS_CATEGORY, code);
    }

    public static String getCreditReportAddressResidenceCode(String code) {
        return getFromMapOrDefault(CREDIT_REPORT_ADDRESS_RESIDENCE_CODE, code);
    }

    public static String getStateFromStateCode(String stateCode) {
        if (stateCode == null) {
            return null;
        }
        String state = STATE_CODE_MAP.get(stateCode);
        return state != null ? state.toUpperCase(Locale.ROOT) : null;
    }

    public static String getAccountAndLoanType(String code) {
        return getFromMapOrDefault(ACCOUNT_AND_LOAN_TYPE, code);
    }

    public static String getAccountOwnershipIndicator(String code) {
        return getFromMapOrDefault(ACCOUNT_OWNERSHIP_INDICATOR, code);
    }

    public static String getAccountSuitFiledWilfulDefault(String code) {
        return getFromMapOrDefault(ACCOUNT_SUIT_FILED_WILFUL_DEFAULT, code);
    }

    public static String getAccountCreditFacilityStatus(String code) {
        return getFromMapOrDefault(ACCOUNT_CREDIT_FACILITY_STATUS, code);
    }

    public static String getAccountTypeOfCollateral(String code) {
        return getFromMapOrDefault(ACCOUNT_TYPE_OF_COLLATERAL, code);
    }

    public static String getAccountPaymentFrequency(String code) {
        return getFromMapOrDefault(ACCOUNT_PAYMENT_FREQUENCY, code);
    }

    // #TODO use this if account segment summary section is valid
    public static String getAccountSegmentSummaryAccountGroup(String code) {
        return getFromMapOrDefault(ACCOUNT_SEGMENT_SUMMARY_ACCOUNT_GROUP, code);
    }

    public static String getAccountSegmentSummaryLiveClosedIndicator(String code) {
        return getFromMapOrDefault(ACCOUNT_SEGMENT_SUMMARY_LIVE_CLOSED_INDICATOR, code);
    }

    public static String getLoanSecurityStatus(String loanType) {
        return getFromMapOrDefault(LOAN_SECURITY_STATUS_MAP, loanType);
    }

    public static String getAccountErrorCodes(String code) {
        return getFromMapOrDefault(ACCOUNT_ERROR_CODES, code);
    }

    public static String getEmploymentSegmentResponseErrorCode(String code) {
        return getFromMapOrDefault(EMPLOYMENT_SEGMENT_RESPONSE_ERROR_CODE, code);
    }

    public static String getCibilRemarksCodesMap(String code) {
        return getFromMapOrDefault(CIBIL_REMARKS_CODES_MAP, code);
    }

    private static String getFromMapOrDefault(Map<String, String> map, String key) {
        String result = map.get(key);
        return result != null ? result : key;
    }

}
