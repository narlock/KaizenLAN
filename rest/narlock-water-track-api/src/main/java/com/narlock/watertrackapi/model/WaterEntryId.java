package com.narlock.watertrackapi.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WaterEntryId {
  private Integer profileId;
  private LocalDate entryDate;
}
