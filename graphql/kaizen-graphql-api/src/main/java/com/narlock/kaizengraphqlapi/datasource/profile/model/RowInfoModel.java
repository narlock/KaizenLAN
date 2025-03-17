package com.narlock.kaizengraphqlapi.datasource.profile.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RowInfoModel {
  private Integer profileId;
  private Integer rowIndex;
  private String widgets;
}
