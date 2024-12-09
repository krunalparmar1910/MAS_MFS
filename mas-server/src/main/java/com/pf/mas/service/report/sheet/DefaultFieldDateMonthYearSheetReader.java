package com.pf.mas.service.report.sheet;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.FieldDateMonthYear;
import com.pf.mas.repository.FieldDateMonthYearRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class DefaultFieldDateMonthYearSheetReader implements FieldDateMonthYearSheetReader {
    private static final long CACHE_CLEANUP_INTERVAL_MS = 60L * 60L * 1000L; // hourly cleanup
    private final FieldDateMonthYearRepository fieldDateMonthYearRepository;
    private final Map<String, FieldDateMonthYear> fieldDateMonthYearMap;
    private final AtomicLong lastAccessTime;

    public DefaultFieldDateMonthYearSheetReader(FieldDateMonthYearRepository fieldDateMonthYearRepository) {
        this.fieldDateMonthYearRepository = fieldDateMonthYearRepository;
        fieldDateMonthYearMap = new ConcurrentHashMap<>();
        lastAccessTime = new AtomicLong(System.currentTimeMillis());
    }

    @Override
    public FieldDateMonthYear getFieldDateMonthYear(String fieldDateValue) throws MasReportSheetReaderException {
        Pair<LocalDate, LocalDate> fromDateToDatePair = SheetReaderUtils.getFromDateToDatePair(fieldDateValue);
        if (fromDateToDatePair.getLeft() == null || fromDateToDatePair.getRight() == null) {
            throw new MasReportSheetReaderException("Could not get from and to date from " + fieldDateValue);
        }
        return getOrCreateFieldDateMonthYear(fromDateToDatePair, fieldDateValue);
    }

    @Override
    public FieldDateMonthYear getOrCreateFieldDateMonthYear(Pair<LocalDate, LocalDate> fromDateToDatePair, String fieldDateValue) {
        lastAccessTime.set(System.currentTimeMillis());
        return fieldDateMonthYearMap.computeIfAbsent(fieldDateValue, i -> {
            FieldDateMonthYear fieldDateMonthYear = fieldDateMonthYearRepository.findByFieldDateValue(fieldDateValue);
            if (fieldDateMonthYear == null) {
                log.trace("Key {} does not exist in DB, creating", fieldDateValue);
                fieldDateMonthYear = new FieldDateMonthYear();
                fieldDateMonthYear.setFromDate(fromDateToDatePair.getLeft());
                fieldDateMonthYear.setToDate(fromDateToDatePair.getRight());
                fieldDateMonthYear.setFieldDateValue(fieldDateValue);
                return fieldDateMonthYearRepository.save(fieldDateMonthYear);
            } else {
                log.trace("Key {} already exists in DB", fieldDateValue);
                return fieldDateMonthYear;
            }
        });
    }

    @Override
    public FieldDateMonthYear getLastOneYearFromToDate(FieldDateMonthYear fieldDateMonthYear, String fieldDateValue) {
        FieldDateMonthYear lastYear = new FieldDateMonthYear();
        lastYear.setFieldDateValue(fieldDateValue);
        lastYear.setToDate(fieldDateMonthYear.getToDate());
        lastYear.setFromDate(SheetReaderUtils.getLastOneYearFromToDate(fieldDateMonthYear.getToDate()));
        return getOrCreateFieldDateMonthYear(Pair.of(lastYear.getFromDate(), lastYear.getToDate()), lastYear.getFieldDateValue());
    }

    @Scheduled(initialDelay = CACHE_CLEANUP_INTERVAL_MS, fixedDelay = CACHE_CLEANUP_INTERVAL_MS)
    private void cleanupTask() {
        long currentTime = System.currentTimeMillis();
        long difference = currentTime - lastAccessTime.get();
        Duration duration = Duration.ofMillis(difference);

        if (difference >= CACHE_CLEANUP_INTERVAL_MS) {
            fieldDateMonthYearMap.clear();
            log.debug("Cleared fieldDateMonthYearMap, time difference from last access is {} minutes {} seconds", duration.toMinutes(), duration.toSecondsPart());
        } else {
            log.debug("Did not clear cache since it was last accessed {} minutes {} seconds ago, minimum last access time difference should be {} minutes",
                    duration.toMinutes(), duration.toSecondsPart(), Duration.ofMillis(CACHE_CLEANUP_INTERVAL_MS).toMinutes());
        }
    }
}
