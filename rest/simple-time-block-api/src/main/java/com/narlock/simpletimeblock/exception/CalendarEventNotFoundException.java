package com.narlock.simpletimeblock.exception;

import lombok.Data;

@Data
public class CalendarEventNotFoundException extends RuntimeException {
  private Integer calendarEventId;

  public CalendarEventNotFoundException(Integer calendarEventId, String message) {
    super(message);
    this.calendarEventId = calendarEventId;
  }
}
