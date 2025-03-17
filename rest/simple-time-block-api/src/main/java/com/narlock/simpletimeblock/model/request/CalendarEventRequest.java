package com.narlock.simpletimeblock.model.request;

import com.narlock.simpletimeblock.model.CalendarEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CalendarEventRequest {
  private String name;
  private String note;
  private LocalTime startTime;
  private LocalTime endTime;
  private LocalDate date;
  private String meta;

  public CalendarEvent toCalendarEvent() {
    return new CalendarEvent(name, note, startTime, endTime, date, meta);
  }
}
