package com.narlock.kaizengraphqlapi.model.checklist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChecklistItem {
  private Integer id;
  private String checklistName;
  private Integer profileId;
  private String name;
  private String description;
  private String lastCompletedDate;
  private Integer streak;
}
