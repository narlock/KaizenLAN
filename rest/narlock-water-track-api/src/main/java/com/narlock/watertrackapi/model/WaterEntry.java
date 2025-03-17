package com.narlock.watertrackapi.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "WaterEntry")
@IdClass(WaterEntryId.class)
public class WaterEntry {
  @Id
  @Column(name = "profile_id")
  private Integer profileId;

  @Id
  @Column(name = "entry_date")
  private LocalDate entryDate;

  @Column(name = "entry_amount")
  private Integer entryAmount;
}
