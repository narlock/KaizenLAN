package com.narlock.kaizengraphqlapi.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputValidationUtil {
  public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

  public static boolean isISOLocalDate(String date) {
    try {
      // Parse the date using the formatter
      LocalDate.parse(date, DATE_FORMATTER);
      return true;
    } catch (DateTimeParseException e) {
      // Handle invalid date formats
      throw new IllegalArgumentException("Invalid date format. Expected YYYY-MM-DD.", e);
    }
  }

  public static boolean areISOLocalDate(String... dates) {
    for (String date : dates) {
      try {
        // Parse the date using the formatter
        LocalDate.parse(date, DATE_FORMATTER);
        return true;
      } catch (DateTimeParseException e) {
        // Handle invalid date formats
        throw new IllegalArgumentException("Invalid date format. Expected YYYY-MM-DD.", e);
      }
    }
    return true;
  }
}
