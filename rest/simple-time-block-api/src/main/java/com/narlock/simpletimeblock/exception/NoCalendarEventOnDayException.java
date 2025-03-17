package com.narlock.simpletimeblock.exception;

import java.time.LocalDate;
import lombok.Data;

@Data
public class NoCalendarEventOnDayException extends RuntimeException {
  private LocalDate date;

  public NoCalendarEventOnDayException(LocalDate date, String message) {
    super(message);
    this.date = date;
  }
}
