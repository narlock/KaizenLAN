package com.narlock.simpletimeblock.util;

import com.narlock.simpletimeblock.model.request.CalendarEventRequest;
import com.narlock.simpletimeblock.model.request.CreateRecurringCalendarEventsRequest;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecurringEventParser {
  public static int parseRepeatDays(String repeat) {
    List<DayOfWeek> repeatDays = new ArrayList<>();
    if ("DAILY".equalsIgnoreCase(repeat)) {
      // Repeat every day, return an empty list
      return 1;
    } else if ("WEEKLY".equalsIgnoreCase(repeat)) {
      // Repeat every week, return an empty list
      return 7;
    } else if ("MONTHLY".equalsIgnoreCase(repeat)) {
      // Repeat every month, return an empty list
      return 31;
    }
    throw new IllegalArgumentException("Illegal repeat: " + repeat);
  }

  public static LocalDate calculateEndDate(LocalDate startDate, String until) {
    switch (until) {
      case "WEEK":
        return startDate.plusDays(7);
      case "MONTH":
        return startDate.plusDays(31);
      default:
        if (until.matches("\\d+MONTH")) {
          int monthsToAdd = Integer.parseInt(until.substring(0, until.indexOf("MONTH")));
          return startDate.plusDays(monthsToAdd * 31);
        } else if ("YEAR".equalsIgnoreCase(until)) {
          return startDate.plusDays(365);
        } else {
          throw new IllegalArgumentException("Invalid 'until' value: " + until);
        }
    }
  }

  public static CalendarEventRequest createEventFromRequest(
      CreateRecurringCalendarEventsRequest request, LocalDate date) {
    CalendarEventRequest eventRequest = new CalendarEventRequest();
    eventRequest.setName(request.getEvent().getName());
    eventRequest.setNote(request.getEvent().getNote());
    eventRequest.setStartTime(request.getEvent().getStartTime());
    eventRequest.setEndTime(request.getEvent().getEndTime());
    eventRequest.setDate(date);
    eventRequest.setMeta(request.getEvent().getMeta());
    return eventRequest;
  }
}
