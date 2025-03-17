package com.narlock.kaizengraphqlapi.model.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RowInfo {
  private Integer profileId;
  private Integer rowIndex;
  private String widgets;
}
