package com.narlock.kaizengraphqlapi.datasource.profile.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileModel {
  private KaizenProfileModel profile;
  private HealthModel health;
  private List<RowInfoModel> rowInfoList;
}
