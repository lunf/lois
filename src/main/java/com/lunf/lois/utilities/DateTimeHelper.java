package com.lunf.lois.utilities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeHelper {

    private static DateTimeFormatter defaultFormatter = DateTimeFormatter.RFC_1123_DATE_TIME;

    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Convert ZonedDateTime to String with default format RFC_1123_DATE_TIME (Tue, 3 Jun 2008 11:05:30 GMT)
     *
     * @param zonedDateTime
     * @param dateTimeFormatter
     * @return
     */
    public static String convertToString(ZonedDateTime zonedDateTime, DateTimeFormatter dateTimeFormatter) {

        String dateTime = "";

        if (zonedDateTime == null) {
            return dateTime;
        }

        if (dateTimeFormatter == null) {
            dateTime = zonedDateTime.format(defaultFormatter);
        } else {
            dateTime = zonedDateTime.format(dateTimeFormatter);
        }

        return dateTime;
    }

    /**
     * Convert ZonedDaeTime to date string with format as dd-MMM-yyyy (aka: 18-Oct-2018)
     *
     * @param zonedDateTime
     * @return
     */
    public static String convertToDateString(ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) {
            return new String();
        }

        String dateString = zonedDateTime.format(dateFormatter);

        return dateString;
    }

    public static String convertToTimeString(LocalTime localTime) {
        if (localTime == null) {
            return new String();
        }

        String timeString = localTime.format(timeFormatter);

        return timeString;
    }

    public static ZonedDateTime convertToDateTime(String dateTime, DateTimeFormatter dateTimeFormatter) {

        if (dateTimeFormatter == null) {
            dateTimeFormatter = DateTimeHelper.defaultFormatter;
        }

        LocalDate date = LocalDate.parse(dateTime, dateTimeFormatter);

        return date.atStartOfDay(ZoneId.systemDefault());
    }
}
