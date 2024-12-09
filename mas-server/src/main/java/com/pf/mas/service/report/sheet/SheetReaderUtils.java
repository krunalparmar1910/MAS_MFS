package com.pf.mas.service.report.sheet;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.FieldDateMonthYear;
import com.pf.mas.model.entity.FieldGroup;
import com.pf.mas.model.entity.SheetType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.ss.usermodel.Cell;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public final class SheetReaderUtils {
    public static final String PERIOD_COVERED = "Period Covered:";
    private static final Pattern FY_DATE_PATTERN = Pattern.compile("FY\\s*(\\d{4})-(\\d{2,4})");
    private static final Pattern MONTH_YEAR_PATTERN = Pattern.compile("(\\w{3}-\\d{2,4})");
    private static final Pattern TTM_DATE_PATTERN = Pattern.compile("TTM\\s*\\(\\s*(\\w{3}-\\d{2,4})\\s*to\\s*(\\w{3}-\\d{2,4})\\s*\\)");
    private static final Pattern PERIOD_COVERED_PATTERN = Pattern.compile("Period\\s*Covered\\s*:\\s*(\\w{3}-\\d{2,4})\\s*to\\s*(\\w{3}-\\d{2,4})\\s*");
    private static final Pattern PAN_PATTERN = Pattern.compile("PAN\\s*:\\s*(\\w*)");
    private static final Pattern GSTN_PATTERN = Pattern.compile("GSTN\\s*:\\s*(\\S*)");
    private static final String DD_MMM_YY = "dd-MMM-yy";
    private static final String DD_MMM_YYYY = "dd-MMM-yyyy";
    private static final List<Pattern> PATTERN_LIST = List.of(FY_DATE_PATTERN, MONTH_YEAR_PATTERN, TTM_DATE_PATTERN, PERIOD_COVERED_PATTERN);

    private SheetReaderUtils() {
        // utils class
    }

    public static String sanitizeStringValue(String value) {
        return value.trim();
    }

    public static Integer parseIntegerValue(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            log.trace("Could not parse integer value {} due to {}", value, e.getLocalizedMessage());
            return null;
        }
    }

    public static boolean isValidDate(String value) {
        return PATTERN_LIST.stream().anyMatch(pattern -> pattern.matcher(value).find());
    }

    public static LocalDate parseDateValue(String value) {
        try {
            return LocalDate.parse(value);
        } catch (Exception e) {
            log.trace("Could not parse date value {} due to {}", value, e.getLocalizedMessage());
            return null;
        }
    }

    public static LocalDate parseDateValue(String value, DateTimeFormatter dateTimeFormatter) {
        try {
            return LocalDate.parse(value, dateTimeFormatter);
        } catch (Exception e) {
            log.trace("Could not parse date value {} due to {}", value, e.getLocalizedMessage());
            return null;
        }
    }

    public static BigDecimal parseNumericValue(String value) {
        BigDecimal bigDecimal = null;
        if (StringUtils.isNotBlank(value)) {
            // remove commas, hyphens, percentage sign and extra spaces
            String parsedValue = value.replaceAll("[,%\\s]+", "");
            try {
                bigDecimal = new BigDecimal(parsedValue);
            } catch (Exception e) {
                log.trace("Could not parse double value {} due to {}", value, e.getLocalizedMessage());
            }
        }
        return bigDecimal;
    }

    public static BigDecimal getNumericCellValue(Cell cell, String cellValue) {
        BigDecimal numericValue;
        try {
            numericValue = BigDecimal.valueOf(cell.getNumericCellValue());
        } catch (Exception e) {
            log.trace("Could not get numeric value from cell value {} due to {}", cell.getStringCellValue(), e.getLocalizedMessage());
            numericValue = parseNumericValue(cellValue);
        }
        return numericValue;
    }

    public static FieldGroup getNewFieldGroup(String groupName, SheetType sheetType, ClientOrder clientOrder) {
        FieldGroup fieldGroup = new FieldGroup();
        fieldGroup.setFieldGroupName(SheetReaderUtils.sanitizeStringValue(groupName));
        fieldGroup.setSheetType(sheetType);
        fieldGroup.setClientOrder(clientOrder);
        return fieldGroup;
    }

    public static String getPANValueFromReportHeader(String reportHeaderString) {
        if (StringUtils.isNotBlank(reportHeaderString)) {
            Matcher matcher = PAN_PATTERN.matcher(reportHeaderString);
            if (matcher.find() && matcher.groupCount() == 1) {
                log.trace("Found PAN in string {}, returning value {}", reportHeaderString, matcher.group(1));
                return matcher.group(1);
            }
        }
        log.trace("Did not find any PAN in string {}", reportHeaderString);
        return null;
    }

    public static String getGSTNValueFromReportHeader(String reportHeaderString) {
        if (StringUtils.isNotBlank(reportHeaderString)) {
            Matcher matcher = GSTN_PATTERN.matcher(reportHeaderString);
            if (matcher.find() && matcher.groupCount() == 1) {
                log.trace("Found GSTN in string {}, returning value {}", reportHeaderString, matcher.group(1));
                return matcher.group(1);
            }
        }
        log.trace("Did not find any GSTN in string {}", reportHeaderString);
        return null;
    }

    public static Pair<LocalDate, LocalDate> getFromDateToDatePair(String fieldDateValue) throws MasReportSheetReaderException {
        Pair<LocalDate, LocalDate> localDatePair = Pair.of(null, null);
        if (StringUtils.isBlank(fieldDateValue)) {
            return localDatePair;
        }

        // parse date value based on expected date patterns found across all sheets
        String parseDateValue = fieldDateValue.trim();
        if (FY_DATE_PATTERN.matcher(parseDateValue).find()) {
            Matcher matcher = FY_DATE_PATTERN.matcher(parseDateValue);
            if (matcher.find() && matcher.groupCount() == 2) {
                LocalDate fromDate = LocalDate.parse(matcher.group(1) + "-04-01");
                LocalDate toDate = fromDate.plusYears(1L).minusMonths(1L).with(TemporalAdjusters.lastDayOfMonth());
                localDatePair = Pair.of(fromDate, toDate);
            }
        } else if (TTM_DATE_PATTERN.matcher(parseDateValue).find()) {
            localDatePair = handleTTMAndPeriodCoveredPattern(parseDateValue, TTM_DATE_PATTERN);
        } else if (PERIOD_COVERED_PATTERN.matcher(parseDateValue).find()) {
            localDatePair = handleTTMAndPeriodCoveredPattern(parseDateValue, PERIOD_COVERED_PATTERN);
        } else if (MONTH_YEAR_PATTERN.matcher(parseDateValue).find()) {
            Matcher matcher = MONTH_YEAR_PATTERN.matcher(parseDateValue);
            if (matcher.find() && matcher.groupCount() == 1) {
                LocalDate fromDate = getStartDateFromShortMonthYear(matcher.group(1));
                LocalDate toDate = fromDate.with(TemporalAdjusters.lastDayOfMonth());
                localDatePair = Pair.of(fromDate, toDate);
            }
        }

        return localDatePair;
    }

    public static FieldDateMonthYear getFYFieldDateMonthYearForMonth(List<FieldDateMonthYear> fieldDateMonthYearList, FieldDateMonthYear month) {
        for (FieldDateMonthYear fieldDateMonthYear : fieldDateMonthYearList) {
            if ((month.getFromDate().isAfter(fieldDateMonthYear.getFromDate()) || month.getFromDate().isEqual(fieldDateMonthYear.getFromDate()))
                    && (month.getToDate().isBefore(fieldDateMonthYear.getToDate()) || month.getToDate().isEqual(fieldDateMonthYear.getToDate()))) {
                return fieldDateMonthYear;
            }
        }
        return null;
    }

    public static LocalDate getLastOneYearFromToDate(LocalDate toDate) {
        return toDate.minusYears(1L).with(TemporalAdjusters.firstDayOfNextMonth());
    }

    private static Pair<LocalDate, LocalDate> handleTTMAndPeriodCoveredPattern(String parseDateValue, Pattern pattern) throws MasReportSheetReaderException {
        Matcher matcher = pattern.matcher(parseDateValue);
        LocalDate fromDate = null;
        LocalDate toDate = null;
        if (matcher.find() && matcher.groupCount() == 2) {
            fromDate = getStartDateFromShortMonthYear(matcher.group(1));
            toDate = getStartDateFromShortMonthYear(matcher.group(2)).with(TemporalAdjusters.lastDayOfMonth());
        }
        return Pair.of(fromDate, toDate);
    }

    private static LocalDate getStartDateFromShortMonthYear(String monthYear) throws MasReportSheetReaderException {
        String[] monthYearSplit = monthYear.split("-");
        if (monthYearSplit.length != 2) {
            throw new MasReportSheetReaderException("Invalid date format for value: " + monthYear + ", expected Month-Year (eg: Apr-23)");
        }
        String startDateString = "01-" + monthYearSplit[0] + "-" + monthYearSplit[1];

        try {
            return LocalDate.parse(startDateString, DateTimeFormatter.ofPattern(DD_MMM_YY));
        } catch (DateTimeParseException e) {
            log.trace("Invalid format {} for string {}", DD_MMM_YY, startDateString, e);
        }

        try {
            return LocalDate.parse(startDateString, DateTimeFormatter.ofPattern(DD_MMM_YYYY));
        } catch (DateTimeParseException e) {
            log.trace("Invalid format {} for string {}", DD_MMM_YYYY, startDateString, e);
        }

        throw new MasReportSheetReaderException("Could not get start date from " + monthYear);
    }
}
