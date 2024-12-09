package com.pf.perfios.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
public final class PerfiosUtils {

    public static final String DATE_FORMAT = "dd/MM/yyyy";

    private PerfiosUtils() {
        //do nothing
    }

    public static LocalDate parseMonthToLocalDate(String monthName) {

        if (Strings.isEmpty(monthName)) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-yy-dd");

        return LocalDate.parse(monthName + "-01", formatter);
    }

    public static LocalDate parseLocalDate(String dateStr) {
        if (Strings.isEmpty(dateStr)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(dateStr, formatter);
        } catch (Exception e) {
            log.error("Failed to parse the date {}, error: {}", dateStr, e.getLocalizedMessage());
            return null;
        }
    }

    public static Long parseLong(String val){
        if(Strings.isEmpty(val)){
            return null;
        }
        try{
            return Long.parseLong(val);
        } catch (NumberFormatException nfe){
            log.error("Error while parsing {} to long", nfe.getMessage());
            return null;
        }
    }
}
