package com.narlock.checklistapi.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "ChecklistItem")
@IdClass(ChecklistItemId.class)
public class ChecklistItem {
  @Id private Integer id;

  @Id
  @Column(name = "checklist_name")
  private String checklistName;

  @Id
  @Column(name = "profile_id")
  private Integer profileId;

  private String name;
  private String description;

  @Column(name = "last_completed_date")
  private LocalDate lastCompletedDate;

  @Column(name = "exclude_days")
  private String excludeDays;

  private Integer streak;

  public ChecklistItem(
      String checklistName,
      Integer profileId,
      String name,
      String description,
      LocalDate lastCompletedDate,
      String excludeDays) {
    this.checklistName = checklistName;
    this.profileId = profileId;
    this.name = name;
    this.description = description;
    this.lastCompletedDate = lastCompletedDate;
    this.excludeDays = excludeDays;
  }
}
