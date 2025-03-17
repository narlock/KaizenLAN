package com.narlock.habitapi.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class HabitEntryId {
  private String name;
  private Integer profileId;
  private LocalDate date;
}
