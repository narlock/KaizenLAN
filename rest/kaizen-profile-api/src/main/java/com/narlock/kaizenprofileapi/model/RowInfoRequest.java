package com.narlock.kaizenprofileapi.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RowInfoRequest {

  @NotNull(message = "rowIndex must not be null")
  private Integer rowIndex;

  @NotNull(message = "widgets must not be null")
  private String widgets;
}
