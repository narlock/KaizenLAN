package com.narlock.habitapi.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Builder
@Table(name = "HabitEntry")
@IdClass(HabitEntryId.class)
public class HabitEntry {
  @Id
  @Column(name = "habit_name")
  private String name;

  @Id
  @Column(name = "profile_id")
  private Integer profileId;

  @Id
  @Column(name = "completed_date")
  private LocalDate date;
}
