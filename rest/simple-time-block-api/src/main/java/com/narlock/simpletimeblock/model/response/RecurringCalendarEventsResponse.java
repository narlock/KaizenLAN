package com.narlock.simpletimeblock.model.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class RecurringCalendarEventsResponse {
  private int size;
  private LocalDate startDate;
  private LocalDate endDate;
  private String repeat;
}
