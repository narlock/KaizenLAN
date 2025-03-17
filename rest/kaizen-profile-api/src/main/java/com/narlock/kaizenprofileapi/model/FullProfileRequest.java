package com.narlock.kaizenprofileapi.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FullProfileRequest {
  private Profile profile;
  private Health health;
  private List<RowInfo> rowInfoList;
}
