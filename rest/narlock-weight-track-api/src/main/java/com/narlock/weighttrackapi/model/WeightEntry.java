package com.narlock.weighttrackapi.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "WeightEntry")
@IdClass(WeightEntryId.class)
public class WeightEntry {
  @Id
  @Column(name = "profile_id")
  private Integer profileId;

  @Id
  @Column(name = "entry_date")
  private LocalDate entryDate;

  @Column(name = "entry_amount")
  private Double entryAmount;
}
