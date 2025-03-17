package com.narlock.kaizengraphqlapi.model.profile;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {
  private ProfileDetails profile;
  private Health health;
  private List<RowInfo> rowInfoList;
}
