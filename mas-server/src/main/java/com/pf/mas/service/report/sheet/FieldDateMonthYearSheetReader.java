package com.pf.mas.service.report.sheet;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.FieldDateMonthYear;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;

public interface FieldDateMonthYearSheetReader {
    FieldDateMonthYear getFieldDateMonthYear(String fieldDateValue) throws MasReportSheetReaderException;

    FieldDateMonthYear getOrCreateFieldDateMonthYear(Pair<LocalDate, LocalDate> fromDateToDatePair, String fieldDateValue);

    FieldDateMonthYear getLastOneYearFromToDate(FieldDateMonthYear fieldDateMonthYear, String fieldDateValue);
}
