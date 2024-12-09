package com.pf.perfios.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

public class PerfiosUtilsTest {

    @Test
    public void testParseMonthYearToLocalDate() {
        testMonthYear(2023,1,1,"Jan-23");
        testMonthYear(2023,2,1,"Feb-23");
        testMonthYear(2023,3,1,"Mar-23");
        testMonthYear(2024,4,1,"Apr-24");
        testMonthYear(2026,5,1,"May-26");
        testMonthYear(2023,6,1,"Jun-23");
        testMonthYear(2023,7,1,"Jul-23");
        testMonthYear(2023,8,1,"Aug-23");
        testMonthYear(2099,9,1,"Sep-99");
        testMonthYear(2023,10,1,"Oct-23");
        testMonthYear(2022,11,1,"Nov-22");
        testMonthYear(2023,12,1,"Dec-23");
    }

    private void testMonthYear(int year, int month, int day, String input) {
        LocalDate expectedDate = LocalDate.of(year, month, day);
        LocalDate actualDate = PerfiosUtils.parseMonthToLocalDate(input);
        assertEquals(expectedDate, actualDate);
    }


    @Test
    public void testParseMonthToLocalDateWithNull() {
        // Test with null input
        LocalDate result = PerfiosUtils.parseMonthToLocalDate(null);
        assertNull(result);
    }

    @Test
    public void testParseMonthToLocalDateWithEmptyStr() {
        // Test with empty input
        LocalDate result = PerfiosUtils.parseMonthToLocalDate("");
        assertNull(result);
    }

    @Test
    public void testParseMonthToLocalDateWith() {
        assertThrows(DateTimeParseException.class, () -> {
            PerfiosUtils.parseMonthToLocalDate("Alpha-24");
        });
    }



}