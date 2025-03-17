package com.narlock.checklistapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChecklistItemId {
  private Integer id;
  private String checklistName;
  private Integer profileId;
}
