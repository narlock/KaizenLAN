package com.narlock.kaizengraphqlapi.model.water;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WaterEntry {
  private Integer profileId;
  private String entryDate;
  private Integer entryAmount;
}
