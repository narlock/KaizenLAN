package com.narlock.checklistapi.model;

import java.time.LocalDate;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChecklistItemRequest {
  private String checklistName;
  private String name;

  private Integer profileId;
  private String description;
  private LocalDate lastCompletedDate;
  private String excludeDays;

  public ChecklistItem toChecklistItem() {
    return new ChecklistItem(
        checklistName, profileId, name, description, lastCompletedDate, excludeDays);
  }
}
