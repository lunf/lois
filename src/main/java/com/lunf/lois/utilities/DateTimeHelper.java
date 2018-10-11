package com.lunf.lois.utilities;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeHelper {

    private static DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;

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
            dateTime = zonedDateTime.format(formatter);
        } else {
            dateTime = zonedDateTime.format(dateTimeFormatter);
        }

        return dateTime;
    }

    public static ZonedDateTime convertToDateTime(String dateTime, DateTimeFormatter dateTimeFormatter) {

        if (dateTimeFormatter == null) {
            dateTimeFormatter = DateTimeHelper.formatter;
        }

        LocalDate date = LocalDate.parse(dateTime, dateTimeFormatter);

        return date.atStartOfDay(ZoneId.systemDefault());
    }
}
