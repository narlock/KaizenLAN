package com.narlock.kaizengraphqlapi.datasource.profile.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KaizenProfileModel {
  private Integer id;
  private String username;
  private String birthDate;
  private String imageUrl;
  private Integer xp;
  private Integer numRows;
  private String pin;
}
