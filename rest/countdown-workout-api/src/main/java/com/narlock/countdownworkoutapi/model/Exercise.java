package com.narlock.countdownworkoutapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "Exercise")
public class Exercise {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String imageUrl;

  public Exercise(String name, String imageUrl) {
    this.name = name;
    this.imageUrl = imageUrl;
  }
}
