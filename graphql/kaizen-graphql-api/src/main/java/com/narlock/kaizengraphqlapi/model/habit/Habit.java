package com.narlock.kaizengraphqlapi.model.habit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Habit {
  private String name;
  private Integer profileId;
}
