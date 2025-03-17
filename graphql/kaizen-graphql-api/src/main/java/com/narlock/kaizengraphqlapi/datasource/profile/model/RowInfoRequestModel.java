package com.narlock.kaizengraphqlapi.datasource.profile.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RowInfoRequestModel {
  private Integer rowIndex;
  private String widgets;
}
