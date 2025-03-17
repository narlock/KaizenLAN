package com.narlock.kaizengraphqlapi.model.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Health {
  private Double height;
  private Double weight;
  private Double goalWeight;
  private Double goalWater;
}
