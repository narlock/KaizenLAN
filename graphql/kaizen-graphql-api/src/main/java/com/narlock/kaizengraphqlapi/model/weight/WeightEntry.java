package com.narlock.kaizengraphqlapi.model.weight;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeightEntry {
  private Integer profileId;
  private String entryDate;
  private Double entryAmount;
}
