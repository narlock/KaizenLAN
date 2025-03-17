package com.narlock.habitapi.model;

import lombok.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class HabitId {
  private String name;
  private Integer profileId;
}
