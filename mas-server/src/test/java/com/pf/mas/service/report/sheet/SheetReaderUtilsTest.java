package com.pf.mas.service.report.sheet;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.FieldDateMonthYear;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class SheetReaderUtilsTest {
    // constants
    // FY 2022-23 formats
    private static final LocalDate FROM_YEAR_2022 = LocalDate.parse("2022-04-01");
    private static final LocalDate TO_YEAR_2023 = LocalDate.parse("2023-03-31");
    private static final LocalDate FROM_YEAR_2122 = LocalDate.parse("2122-04-01");
    private static final LocalDate TO_YEAR_2123 = LocalDate.parse("2123-03-31");
    private static final LocalDate FROM_YEAR_1999 = LocalDate.parse("1999-04-01");
    private static final LocalDate TO_YEAR_2000 = LocalDate.parse("2000-03-31");
    // TTM (May-22 to Apr-23) formats
    private static final LocalDate FROM_MAY_2022 = LocalDate.parse("2022-05-01");
    private static final LocalDate TO_APR_2023 = LocalDate.parse("2023-04-30");
    private static final LocalDate FROM_DEC_2122 = LocalDate.parse("2022-12-01");
    private static final LocalDate TO_NOV_2123 = LocalDate.parse("2023-11-30");
    private static final LocalDate FROM_JUL_1999 = LocalDate.parse("1999-07-01");
    private static final LocalDate TO_JUN_2000 = LocalDate.parse("2000-06-30");
    // Period covered formats
    private static final LocalDate FROM_APR_2021 = LocalDate.parse("2021-04-01");
    // Date month formats
    private static final LocalDate FROM_APR_2023 = LocalDate.parse("2023-04-01");
    private static final LocalDate TO_JUL_1999 = LocalDate.parse("1999-07-31");
    // field date month year
    private static final FieldDateMonthYear FY_2022_23 = FieldDateMonthYear.builder().fromDate(FROM_YEAR_2022).toDate(TO_YEAR_2023).build();
    private static final FieldDateMonthYear FY_1999_2000 = FieldDateMonthYear.builder().fromDate(FROM_YEAR_1999).toDate(TO_YEAR_2000).build();
    private static final List<FieldDateMonthYear> FIELD_DATE_MONTH_YEAR_LIST = List.of(FY_2022_23, FY_1999_2000);
    private static final FieldDateMonthYear MAR_1999 = FieldDateMonthYear.builder().fromDate(LocalDate.parse("1999-03-01")).toDate(LocalDate.parse("1999-03-31")).build();
    private static final FieldDateMonthYear JAN_2000 = FieldDateMonthYear.builder().fromDate(LocalDate.parse("2000-01-01")).toDate(LocalDate.parse("2000-01-31")).build();
    private static final FieldDateMonthYear MAR_2022 = FieldDateMonthYear.builder().fromDate(LocalDate.parse("2022-03-01")).toDate(LocalDate.parse("2022-03-31")).build();
    private static final FieldDateMonthYear APR_2022 = FieldDateMonthYear.builder().fromDate(LocalDate.parse("2022-04-01")).toDate(LocalDate.parse("2022-04-30")).build();
    private static final FieldDateMonthYear OCT_2022 = FieldDateMonthYear.builder().fromDate(LocalDate.parse("2022-10-01")).toDate(LocalDate.parse("2022-10-31")).build();
    private static final FieldDateMonthYear MAR_2023 = FieldDateMonthYear.builder().fromDate(LocalDate.parse("2023-03-01")).toDate(LocalDate.parse("2023-03-31")).build();

    @Test
    void testFyDateFormatParse() throws MasReportSheetReaderException {
        validateDatePair(SheetReaderUtils.getFromDateToDatePair("abc FY 2022-23 "), FROM_YEAR_2022, TO_YEAR_2023);
        validateDatePair(SheetReaderUtils.getFromDateToDatePair(" FY   2022-23 "), FROM_YEAR_2022, TO_YEAR_2023);
        validateDatePair(SheetReaderUtils.getFromDateToDatePair(" FY 2022-23 TOP 20 B2B CUSTOMERS' ANALYSIS "), FROM_YEAR_2022, TO_YEAR_2023);
        validateDatePair(SheetReaderUtils.getFromDateToDatePair("FY 2122-23"), FROM_YEAR_2122, TO_YEAR_2123);
        validateDatePair(SheetReaderUtils.getFromDateToDatePair("FY2122-23"), FROM_YEAR_2122, TO_YEAR_2123);
        validateDatePair(SheetReaderUtils.getFromDateToDatePair("FY 1999-00"), FROM_YEAR_1999, TO_YEAR_2000);
        validateDatePair(SheetReaderUtils.getFromDateToDatePair("FY 1999-2000"), FROM_YEAR_1999, TO_YEAR_2000);
    }

    @Test
    void testTTMDateFormatParse() throws MasReportSheetReaderException {
        validateDatePair(SheetReaderUtils.getFromDateToDatePair(" TTM (May-22 to Apr-23)  "), FROM_MAY_2022, TO_APR_2023);
        validateDatePair(SheetReaderUtils.getFromDateToDatePair("TTM(May-22 to Apr-23)"), FROM_MAY_2022, TO_APR_2023);
        validateDatePair(SheetReaderUtils.getFromDateToDatePair(" TTM (May-22 to Apr-23) TOP 20 B2B CUSTOMERS' ANALYSIS "), FROM_MAY_2022, TO_APR_2023);
        validateDatePair(SheetReaderUtils.getFromDateToDatePair(" TTM ( Dec-22  to   Nov-23 )  "), FROM_DEC_2122, TO_NOV_2123);
        validateDatePair(SheetReaderUtils.getFromDateToDatePair(" TTM (Jul-1999 to Jun-2000)  "), FROM_JUL_1999, TO_JUN_2000);
    }

    @Test
    void testPeriodCoveredDateFormatParse() throws MasReportSheetReaderException {
        validateDatePair(SheetReaderUtils.getFromDateToDatePair("Period Covered:                Apr-21 to Apr-23"), FROM_APR_2021, TO_APR_2023);
        validateDatePair(SheetReaderUtils.getFromDateToDatePair(" Period  Covered  :                Apr-21   to  Apr-23 "), FROM_APR_2021, TO_APR_2023);
        validateDatePair(SheetReaderUtils.getFromDateToDatePair("Period Covered: Apr-21 to Apr-23"), FROM_APR_2021, TO_APR_2023);
        validateDatePair(SheetReaderUtils.getFromDateToDatePair(
                        """
                                PAN:                                        AAUFR9715R
                                GSTN:                                        23AAUFR9715R1Z4
                                Period Covered:                Apr-21 to Apr-23"""),
                FROM_APR_2021, TO_APR_2023);
    }

    @Test
    void testMonthYearFormatParse() throws MasReportSheetReaderException {
        validateDatePair(SheetReaderUtils.getFromDateToDatePair("Apr-23"), FROM_APR_2023, TO_APR_2023);
        validateDatePair(SheetReaderUtils.getFromDateToDatePair("  Apr-23  "), FROM_APR_2023, TO_APR_2023);
        validateDatePair(SheetReaderUtils.getFromDateToDatePair(" Jul-1999 "), FROM_JUL_1999, TO_JUL_1999);
    }

    @Test
    void testGetFYFieldDateMonthYearForMonth() {
        assertEquals(FY_2022_23, SheetReaderUtils.getFYFieldDateMonthYearForMonth(FIELD_DATE_MONTH_YEAR_LIST, OCT_2022));
        assertEquals(FY_2022_23, SheetReaderUtils.getFYFieldDateMonthYearForMonth(FIELD_DATE_MONTH_YEAR_LIST, APR_2022));
        assertEquals(FY_2022_23, SheetReaderUtils.getFYFieldDateMonthYearForMonth(FIELD_DATE_MONTH_YEAR_LIST, MAR_2023));
        assertEquals(FY_1999_2000, SheetReaderUtils.getFYFieldDateMonthYearForMonth(FIELD_DATE_MONTH_YEAR_LIST, JAN_2000));
        assertNull(SheetReaderUtils.getFYFieldDateMonthYearForMonth(FIELD_DATE_MONTH_YEAR_LIST, MAR_1999));
        assertNull(SheetReaderUtils.getFYFieldDateMonthYearForMonth(FIELD_DATE_MONTH_YEAR_LIST, MAR_2022));
    }

    @Test
    void testGetLastOneYearFromToDate() {
        LocalDate fromDate1 = SheetReaderUtils.getLastOneYearFromToDate(OCT_2022.getToDate());
        assertEquals(LocalDate.parse("2021-11-01"), fromDate1);

        LocalDate fromDate2 = SheetReaderUtils.getLastOneYearFromToDate(MAR_2023.getToDate());
        assertEquals(APR_2022.getFromDate(), fromDate2);
    }

    @Test
    void testParseNumericValue() {
        assertEquals(new BigDecimal("10.23"), SheetReaderUtils.parseNumericValue("10.23"));
        assertEquals(new BigDecimal("-10.23"), SheetReaderUtils.parseNumericValue("-10.23 "));
        assertEquals(new BigDecimal("50.73"), SheetReaderUtils.parseNumericValue(" 50.73%"));
        assertEquals(new BigDecimal("-50.73"), SheetReaderUtils.parseNumericValue("-50.73% "));
        assertEquals(new BigDecimal("6855549"), SheetReaderUtils.parseNumericValue("6,855,549 "));
        assertEquals(new BigDecimal("-6855549"), SheetReaderUtils.parseNumericValue(" -6,855,549 "));
        assertEquals(new BigDecimal("-6548.73"), SheetReaderUtils.parseNumericValue("-6,548.73% "));
        assertNull(SheetReaderUtils.parseNumericValue(""));
        assertNull(SheetReaderUtils.parseNumericValue("abc"));
        assertNull(SheetReaderUtils.parseNumericValue("  ,%- "));
        assertNull(SheetReaderUtils.parseNumericValue("1,234.56.78"));
    }

    @Test
    void testGetPANValueFromReportHeader() {
        assertEquals("AAUFR9715R", SheetReaderUtils.getPANValueFromReportHeader("""
                PAN:\t\t\t\t\tAAUFR9715R
                GSTN:\t\t\t\t\t23AAUFR9715R1Z4,24AAUFR9715R1Z2
                Period Covered:\t\tApr-21 to Apr-23"""));
        assertEquals("123", SheetReaderUtils.getPANValueFromReportHeader("PAN:123"));
        assertEquals("123", SheetReaderUtils.getPANValueFromReportHeader("  PAN : 123  "));
        assertEquals("123", SheetReaderUtils.getPANValueFromReportHeader("abddec\t\taddaPAN : 123  "));
        assertEquals("123", SheetReaderUtils.getPANValueFromReportHeader(" PAN:123\nGSTN:  123"));
        assertEquals("123", SheetReaderUtils.getPANValueFromReportHeader(" PAN:123\nPAN:  456"));
        assertEquals("123", SheetReaderUtils.getPANValueFromReportHeader(" PAN :\t\t123 "));
        assertNull(SheetReaderUtils.getPANValueFromReportHeader(""));
        assertNull(SheetReaderUtils.getPANValueFromReportHeader("   "));
    }

    @Test
    void testGetGSTNValueFromReportHeader() {
        assertEquals("23AAUFR9715R1Z4,24AAUFR9715R1Z2", SheetReaderUtils.getGSTNValueFromReportHeader("""
                PAN:\t\t\t\t\tAAUFR9715R
                GSTN:\t\t\t\t\t23AAUFR9715R1Z4,24AAUFR9715R1Z2
                Period Covered:\t\tApr-21 to Apr-23"""));
        assertEquals("123", SheetReaderUtils.getGSTNValueFromReportHeader("GSTN:123"));
        assertEquals("123", SheetReaderUtils.getGSTNValueFromReportHeader("  GSTN : 123  "));
        assertEquals("123,456", SheetReaderUtils.getGSTNValueFromReportHeader("abddec\t\taddaGSTN : 123,456  "));
        assertEquals("123", SheetReaderUtils.getGSTNValueFromReportHeader(" GSTN:123\nPAN:  123"));
        assertEquals("123", SheetReaderUtils.getGSTNValueFromReportHeader(" GSTN:123\nGSTN:  456"));
        assertEquals("123", SheetReaderUtils.getGSTNValueFromReportHeader(" GSTN :\t\t123 "));
        assertNull(SheetReaderUtils.getGSTNValueFromReportHeader(""));
        assertNull(SheetReaderUtils.getGSTNValueFromReportHeader("   "));
    }

    private void validateDatePair(Pair<LocalDate, LocalDate> localDatePair, LocalDate fromDate, LocalDate toDate) {
        assertNotNull(localDatePair);
        assertNotNull(localDatePair.getLeft());
        assertNotNull(localDatePair.getRight());
        assertEquals(fromDate, localDatePair.getLeft());
        assertEquals(toDate, localDatePair.getRight());
        assertTrue(fromDate.isBefore(toDate));
        assertTrue(localDatePair.getLeft().isBefore(localDatePair.getRight()));
    }
}
