package com.narlock.checklistapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "Checklist")
@IdClass(ChecklistId.class)
public class Checklist {

  @Id private String name;

  @Id
  @Column(name = "profile_id")
  private Integer profileId;

  @Column(name = "repeat_every")
  private String repeatEvery;

  public Checklist(Integer profileId, String repeatEvery) {
    this.profileId = profileId;
    this.repeatEvery = repeatEvery;
  }
}
