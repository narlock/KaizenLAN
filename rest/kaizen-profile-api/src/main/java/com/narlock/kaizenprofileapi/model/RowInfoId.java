package com.narlock.kaizenprofileapi.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RowInfoId implements Serializable {
  private Integer profileId;
  private Integer rowIndex;
}
