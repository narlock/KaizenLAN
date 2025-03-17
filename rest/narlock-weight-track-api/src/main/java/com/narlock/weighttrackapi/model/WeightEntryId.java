package com.narlock.weighttrackapi.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeightEntryId {
  private Integer profileId;
  private LocalDate entryDate;
}
