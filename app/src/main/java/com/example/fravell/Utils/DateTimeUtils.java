package com.example.fravell.Utils;

import android.util.Pair;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {
    public static String millisecondsToDate(long milliseconds) {
        DateFormat dateFormat = new SimpleDateFormat("MMM d", Locale.getDefault());
        return dateFormat.format(new Date(milliseconds));
    }

    public static String millisecondsToDate(long milliseconds, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return dateFormat.format(new Date(milliseconds));
    }

    public static boolean isInBetweenDates(Pair<Long, Long> firstDate, Pair<Long, Long> secondDate) {
        boolean isInBetweenDates = false;
        LocalDate fravellerStartDate = Instant.ofEpochMilli(firstDate.first)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate fravellerEndDate = Instant.ofEpochMilli(firstDate.second)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate specifiedStartDate = Instant.ofEpochMilli(secondDate.first)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate specifiedEndDate = Instant.ofEpochMilli(secondDate.second)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        isInBetweenDates = (fravellerStartDate.isEqual(specifiedStartDate) || fravellerStartDate.isAfter(specifiedStartDate))
                && (fravellerEndDate.isEqual(specifiedEndDate) || fravellerEndDate.isBefore(specifiedEndDate));
        return isInBetweenDates;
    }
}
