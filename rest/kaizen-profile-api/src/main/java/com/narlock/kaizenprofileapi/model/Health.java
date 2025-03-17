package com.narlock.kaizenprofileapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "Health")
public class Health {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "profile_id")
  private Integer profileId;

  private Double height;
  private Double weight;

  @Column(name = "goal_weight")
  private Double goalWeight;

  @Column(name = "goal_water")
  private Double goalWater;
}
